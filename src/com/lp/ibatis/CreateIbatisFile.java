package com.lp.ibatis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import com.lp.db.DbFile;
import com.lp.db.JavaDataTypePackage;
import com.lp.db.JdbcUtil;
import com.lp.util.UtilLp;
import com.lp.window.CreateStatusWindow;

import freemarker.template.TemplateException;

public class CreateIbatisFile {
	
	private static String tableName = null;
	
	public static void createIbatisFile(List<String> tableNameList, CreateStatusWindow createStatusWindow) {
		boolean createStatus = true;
		for(int i=0; i<tableNameList.size(); i++) {
			tableName = tableNameList.get(i);
			try {
				List<TableColumnInfo> tableInfo = getTableInfo(tableName);
				//����ibatisʵ����
				CreateIbatisEntityFileUtil.createIbatisEntityFile(tableInfo, tableName);
				//����ibatis Dao
				CreateIbatisDaoFileUtil.createIbatisDaoFile(tableInfo, tableName);
				//���� ibatis xml
				CreateIbatisXmlFileUtil.createIbatisXmlFile(tableInfo, tableName);
				//����Ӧ�� service �ӿ�
				CreateAppServiceFileUtil.createAppServiceFile(tableInfo, tableName);
				//����Ӧ�� service �ӿ�ʵ��
				CreateAppServiceImplFileUtil.createAppServiceImplFile(tableInfo, tableName);
				//����Ӧ�� controller java�ļ�
				CreateAppControlerFileUtil.createAppControllerFile(tableInfo, tableName);
				//����ҳ��
				CreateViewFileUtil.createViewFile(tableInfo, tableName);
			} catch (SQLException e) {
				createStatus = false;
				e.printStackTrace();
			} catch (IOException e) {
				createStatus = false;
				e.printStackTrace();
			} catch (TemplateException e) {
				createStatus = false;
				e.printStackTrace();
			}
		}
		if(createStatus) {
			createStatusWindow.updateTextContent("�ļ����� �ɹ�");
			//
			JButton openButton = new JButton("��Ŀ¼");
			openButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		try {
	        			String openPath = DbFile.singleton.getFileConfig().getFileSavePath();// + UtilLp.getIbatisEntityName(tableName)
						Runtime.getRuntime().exec("explorer.exe " + openPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        });
	        createStatusWindow.addButton(openButton);
		}
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private static List<TableColumnInfo> getTableInfo(String tableName) throws SQLException {
		List<TableColumnInfo> tableInfo = new ArrayList<TableColumnInfo>();
		Connection conn = JdbcUtil.getOpenConnection();
		//��ȡ��ṹ��Ϣ
		String tableInfoSql = "SELECT * FROM information_schema.columns WHERE table_schema = '" + DbFile.singleton.getJdbc().getDbname() + "' AND table_name = '" + tableName + "'";
		PreparedStatement pst = conn.prepareStatement(tableInfoSql);
		ResultSet resultSet = pst.executeQuery();
		while(resultSet.next()) {
			TableColumnInfo columnInfo = new TableColumnInfo();
			columnInfo.setColumnName(resultSet.getString("COLUMN_NAME"));//���ֶ�����
			columnInfo.setColumnComment(resultSet.getString("COLUMN_COMMENT"));//���ֶ�ע��
			columnInfo.setDataType(resultSet.getString("DATA_TYPE"));//���ֶ� ��������
			columnInfo.setColumnKey(resultSet.getString("COLUMN_KEY"));//���ֶ� ��������
			columnInfo.setIsIndex(false);//�������ֶ�
			tableInfo.add(columnInfo);
		}
		//��ȡ������Ϣ
		String tableIndexSql = "SHOW INDEX FROM " + DbFile.singleton.getJdbc().getDbname() + "." + tableName;
		pst = conn.prepareStatement(tableIndexSql);
		resultSet = pst.executeQuery();
		while(resultSet.next()) {
			for(int i=0; i<tableInfo.size(); i++) {
				TableColumnInfo columnInfo = tableInfo.get(i);
				String Column_name =  resultSet.getString("Column_name");//���ֶ�����
				if(Column_name.equals(columnInfo.getColumnName())) {
					columnInfo.setNonUnique(resultSet.getString("Non_unique"));//�����Ƿ�����ظ� 0 �����ԣ�1 ����
					columnInfo.setIndexName(resultSet.getString("Key_name"));//��������
					columnInfo.setIndexSeq(resultSet.getString("Seq_in_index"));//����˳��
					columnInfo.setIsIndex(true);//�����ֶ�
				}
			}
		}
		return tableInfo;
	}
	

	/**
	 * 
	 * @param tableInfo
	 * @return
	 */
	protected static List<Map<String,Object>> getMethodList(List<TableColumnInfo> tableInfo) {
		List<Map<String,Object>> methodList = new ArrayList<Map<String,Object>>();//
		
		List<TableColumnInfo> tableInfoCopy1 = getTableInfoCopy(tableInfo);//
		List<TableColumnInfo> tableInfoCopy2 = getTableInfoCopy(tableInfo);//
		//
		for(int i=0; i<tableInfoCopy1.size(); i++) {
			TableColumnInfo columnInfo = tableInfoCopy1.get(i);
			//
			Map<String,Object> methodMap = new HashMap<String, Object>();
			int isIndexUnique = -1;//�Ƿ�Ψһ���� ��0 Ψһ������1 ��ͨ����
			StringBuilder methodNameSuffix = new StringBuilder();//�������ƺ�׺
			StringBuilder methodExplain = new StringBuilder("����");//����ע��
			int indexColumnCount = 0;//������������
			//��������list
			List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
			for(int j=0; j<tableInfoCopy2.size();) {
				if(columnInfo.getIndexName().equals( tableInfoCopy2.get(j).getIndexName() )) {//
					Map<String,Object> paramMap = new HashMap<String, Object>();
					paramMap.put("paramName", UtilLp.columnNameToHumpStr(tableInfoCopy2.get(j).getColumnName()));//��������
					paramMap.put("paramType", UtilLp.getFieldType(tableInfoCopy2.get(j).getDataType()).getJavaDataType());//������������
					paramMap.put("paramExplain", tableInfoCopy2.get(j).getColumnComment());//����˵��
					paramMap.put("COLUMN", tableInfoCopy2.get(j).getColumnName());//������Ӧ�����ݿ��е� �ֶ�����
					paramMap.put("JDBCTYPE", UtilLp.getFieldType(tableInfoCopy2.get(j).getDataType()).getIbatisJdbcType());//��Ӧ�� ibatis jdbcType
					paramList.add(paramMap);
					if(tableInfoCopy2.get(j).getNonUnique().equals("1")) {//��������Ψһ����
						isIndexUnique = 1;
					} else {//Ψһ����
						isIndexUnique = 0;
					}
					//
					indexColumnCount ++;
					//�������ƺ�׺
					methodNameSuffix.append(UtilLp.tableNameToHumpStr(tableInfoCopy2.get(j).getColumnName()));
					//ɾ��Ԫ��
					tableInfoCopy2.remove(j);
				} else {
					j++;
				}
			}
			if(paramList.size() > 0) {
				methodMap.put("paramList", paramList);//��������
				methodMap.put("methodNameSuffix", methodNameSuffix);//�������ƺ�׺
				methodMap.put("isIndexUnique", isIndexUnique);//�Ƿ�Ψһ���� ��0 Ψһ������1 ��ͨ����
				if(isIndexUnique == 0) {
					methodExplain.append("Ψһ");
				}
				if(indexColumnCount == 1) {
					methodExplain.append("���� ");
				} else if(indexColumnCount > 1) {
					methodExplain.append("�������� ");
				}
				for(int lp=0; lp<paramList.size(); lp++) {
					methodExplain.append(paramList.get(lp).get("paramName"));
					if(lp < paramList.size() - 1) {
						methodExplain.append(",");
					}
				}
				methodExplain.append(" ��������");
				methodMap.put("methodExplain", methodExplain.toString());//����ע��
				methodList.add(methodMap);
			}
		}
		return methodList;
	}
	
	/**
	 * ��¡
	 * @param tableInfo
	 * @return
	 */
	private static List<TableColumnInfo> getTableInfoCopy(List<TableColumnInfo> tableInfo) {
		List<TableColumnInfo> tableInfoCopy = new ArrayList<TableColumnInfo>();
		for(int i=0; i<tableInfo.size(); i++) {
			if(tableInfo.get(i).getIsIndex()) {
				tableInfoCopy.add( tableInfo.get(i).clone() );
			}
		}
		return tableInfoCopy;
	}
	
	protected static List<Map<String,String>> getImportList(List<TableColumnInfo> tableInfo) {
		//import
		List<Map<String,String>> importList = new ArrayList<Map<String,String>>();
		JavaDataTypePackage[] javaDataType = JavaDataTypePackage.values();
		for(int i=0; i<tableInfo.size(); i++) {
			String dataType = tableInfo.get(i).getDataType();
			for(int jdt=0; jdt<javaDataType.length; jdt++) {
				if(dataType.equalsIgnoreCase(javaDataType[jdt].getMySqlDataType())) {
					Map<String,String> importMap = new HashMap<String, String>();
					importMap.put("importPackage", javaDataType[jdt].getJavaPackage());
					if(!importList.contains(importMap)) {
						importList.add(importMap);
						break;
					}
				}
			}
		}
		return importList;
	}
	
	/**
	 * 
	 * @param tableInfo
	 * @return
	 */
	protected static List<Map<String,String>> getEntityField(List<TableColumnInfo> tableInfo) {
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		for(int i=0; i<tableInfo.size(); i++) {
			Map<String,String> xmlMap = new HashMap<String, String>();
			TableColumnInfo columnInfo = tableInfo.get(i);
			String columnName = columnInfo.getColumnName();
			String dataType = columnInfo.getDataType();
			String columnCom = columnInfo.getColumnComment();
			String columnKey = columnInfo.getColumnKey();//���ֶ���������
			String isPrimarykey = "";
			if("PRI".equals(columnKey)) {//����
				isPrimarykey = "yes";
			} else {
				isPrimarykey = "no";
			}
			//
			xmlMap.put("COLUMN", columnName);//���ݿ��ֶ���
			xmlMap.put("PROPERTY", UtilLp.columnNameToHumpStr(columnName));//������
			xmlMap.put("JDBCTYPE", UtilLp.getFieldType(dataType).getIbatisJdbcType());
			xmlMap.put("IS_PRIMARYKEY", isPrimarykey);//�Ƿ��� Ψһ����
			
			xmlMap.put("isOrderByColumn", UtilLp.getIsOrderByColumn(columnName));//
			xmlMap.put("isOrderByColumnDesc", UtilLp.getIsOrderByColumn(columnName) + "Desc");//true ����, false ����
			
			xmlMap.put("isGroupByColumn", UtilLp.getIsGroupByColumn(columnName));//
			
			xmlMap.put("fieldExplain", columnCom);//�ֶ�˵��
			listMap.add(xmlMap);
		}
		return listMap;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
