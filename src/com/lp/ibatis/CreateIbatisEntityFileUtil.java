package com.lp.ibatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.lp.db.DbFile;
import com.lp.freemarker.ftl.TemplatePath;
import com.lp.util.FileUtil;
import com.lp.util.UtilLp;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CreateIbatisEntityFileUtil {

	/**
	 * 
	 * @param tableInfo
	 * @param className
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("deprecation")
	public static void createIbatisEntityFile(List<TableColumnInfo> tableInfo, String tableName) throws IOException, TemplateException {
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplatePath.class));
		Template template = configuration.getTemplate("ibatisEntity.ftl", Locale.CHINA, "UTF-8");
		//
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("packagePath", DbFile.singleton.getFileConfig().getIbatisEntityPackage());//
		String className = UtilLp.getIbatisEntityName(tableName);
		rootMap.put("className", className);//������
		//ʵ���� ����
		List<Map<String,String>> entityFieldList = new ArrayList<Map<String,String>>();
		//ʵ���� ����
		List<Map<String,String>> entityMethodList = new ArrayList<Map<String,String>>();
		
		for(int i=0; i<tableInfo.size(); i++) {
			TableColumnInfo columnInfo = tableInfo.get(i);
			String columnCom = columnInfo.getColumnComment();
			String columnName = columnInfo.getColumnName();
			String dataType = columnInfo.getDataType();
			//����
			Map<String,String> entityFieldMap = new HashMap<String, String>();
			entityFieldMap.put("fieldExplain", columnCom);//ʵ�����ֶ�˵��
			String fieldName = UtilLp.columnNameToHumpStr(columnName);//ʵ�����ֶ� ����
			entityFieldMap.put("fieldName", fieldName);
			String fieldType = UtilLp.getFieldType(dataType).getJavaDataType();//�ֶ���������
			entityFieldMap.put("fieldType", fieldType);
			entityFieldList.add(entityFieldMap);//
			if(DbFile.singleton.getFileConfig().getIsCreateMoveSql()) {
				//like
				entityFieldMap = new HashMap<String, String>();
				entityFieldMap.put("fieldName", UtilLp.getIsLikeColumn(columnName));
				entityFieldMap.put("fieldType", "boolean");
				entityFieldMap.put("fieldExplain", "true �����ֶ� "+fieldName+ " ģ����ѯ" + ";false ��ȷ��ѯ (Ĭ��ֵ false)");//ʵ�����ֶ�˵��
				entityFieldList.add(entityFieldMap);//
				//order by
				entityFieldMap = new HashMap<String, String>();
				entityFieldMap.put("fieldName", UtilLp.getIsOrderByColumn(columnName));
				entityFieldMap.put("fieldType", "boolean");
				entityFieldMap.put("fieldExplain", "true �����ֶ� "+fieldName+ " ����" + ";false ������ (Ĭ��ֵ false)");//ʵ�����ֶ�˵��
				entityFieldList.add(entityFieldMap);//
				//order by ���� or ����
				entityFieldMap = new HashMap<String, String>();
				entityFieldMap.put("fieldName", UtilLp.getIsOrderByColumnDesc(columnName));
				entityFieldMap.put("fieldType", "boolean");
				entityFieldMap.put("fieldExplain", "�����ֶ� "+fieldName+ " ����, true ����, false ���� (Ĭ��ֵ false)");//ʵ�����ֶ�˵��
				entityFieldList.add(entityFieldMap);//
				//gorup by
				entityFieldMap = new HashMap<String, String>();
				entityFieldMap.put("fieldName", UtilLp.getIsGroupByColumn(columnName));
				entityFieldMap.put("fieldType", "boolean");
				entityFieldMap.put("fieldExplain", "true �����ֶ� "+fieldName+ " ����" + ";false ������ (Ĭ��ֵ false)");//ʵ�����ֶ�˵��
				entityFieldList.add(entityFieldMap);//
			}
			//����
			Map<String,String> entityMethodMap = new HashMap<String, String>();
			entityMethodMap.put("retutnType", fieldType);//get ���� ��������
			entityMethodMap.put("methodName", UtilLp.firstToUpperCase(fieldName));//get �� set ���� ������
			entityMethodMap.put("fieldName", fieldName);//
			entityMethodMap.put("paramType", fieldType);//set������������
			entityMethodMap.put("paramName", fieldName);//set���� ������
			entityMethodList.add(entityMethodMap);//
			if(DbFile.singleton.getFileConfig().getIsCreateMoveSql()) {
				//like
				entityMethodMap = new HashMap<String, String>();
				entityMethodMap.put("retutnType", "boolean");//get ���� ��������
				entityMethodMap.put("methodName", UtilLp.firstToUpperCase(UtilLp.getIsLikeColumn(columnName)));//get �� set ���� ������
				entityMethodMap.put("fieldName", UtilLp.getIsLikeColumn(columnName));//����������
				entityMethodMap.put("paramType", "boolean");//set������������
				entityMethodMap.put("paramName", UtilLp.getIsOrderByColumn(columnName));//set���� ������
				entityMethodMap.put("methodExplain", "true �����ֶ� "+fieldName+ " ģ����ѯ" + ";false ��ȷ��ѯ (Ĭ��ֵ false)");//����˵��
				entityMethodList.add(entityMethodMap);//
				//order by
				entityMethodMap = new HashMap<String, String>();
				entityMethodMap.put("retutnType", "boolean");//get ���� ��������
				entityMethodMap.put("methodName", UtilLp.firstToUpperCase(UtilLp.getIsOrderByColumn(columnName)));//get �� set ���� ������
				entityMethodMap.put("fieldName", UtilLp.getIsOrderByColumn(columnName));//����������
				entityMethodMap.put("paramType", "boolean");//set������������
				entityMethodMap.put("paramName", UtilLp.getIsOrderByColumn(columnName));//set���� ������
				entityMethodMap.put("methodExplain", "true �����ֶ� "+fieldName+ " ����" + ";false ������ (Ĭ��ֵ false)");//����˵��
				entityMethodList.add(entityMethodMap);//
				//order by ���� or ����
				entityMethodMap = new HashMap<String, String>();
				entityMethodMap.put("retutnType", "boolean");//get ���� ��������
				entityMethodMap.put("methodName", UtilLp.firstToUpperCase(UtilLp.getIsOrderByColumnDesc(columnName)));//get �� set ���� ������
				entityMethodMap.put("fieldName", UtilLp.getIsOrderByColumnDesc(columnName));//����������
				entityMethodMap.put("paramType", "boolean");//set������������
				entityMethodMap.put("paramName", UtilLp.getIsOrderByColumnDesc(columnName));//set���� ������
				entityMethodMap.put("methodExplain", "�����ֶ� "+fieldName+ " ����, true ����, false ���� (Ĭ��ֵ false)");//����˵��
				entityMethodList.add(entityMethodMap);//
				//group by
				entityMethodMap = new HashMap<String, String>();
				entityMethodMap.put("retutnType", "boolean");//get ���� ��������
				entityMethodMap.put("methodName", UtilLp.firstToUpperCase(UtilLp.getIsGroupByColumn(columnName)));//get �� set ���� ������
				entityMethodMap.put("fieldName", UtilLp.getIsGroupByColumn(columnName));//����������
				entityMethodMap.put("paramType", "boolean");//set������������
				entityMethodMap.put("paramName", UtilLp.getIsGroupByColumn(columnName));//set���� ������
				entityMethodList.add(entityMethodMap);//
			}
		}
		//
		rootMap.put("entityFieldList", entityFieldList);
		rootMap.put("entityMethodList", entityMethodList);
		//import
		rootMap.put("importList", CreateIbatisFile.getImportList(tableInfo));
		//
		rootMap.put("isCreateMoveSql", DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		
		FileUtil.writeIbatisFile(template, rootMap, className+".java", "IbatisEntity");//className
		
	}
	
}
