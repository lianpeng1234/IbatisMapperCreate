package com.lp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lp.db.DbFile;
import com.lp.db.MysqlDataType;

public class UtilLp {
	/**
	 * �ַ���������ĸ��д
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
       return  str;
    }
	
	/**
	 * �ַ���������ĸСд
	 * @param str
	 * @return
	 */
	public static String firstToLowerCase(String str) {
		str = str.substring(0, 1).toLowerCase() + str.substring(1);
       return  str;
    }
	
	/**
	 * �ж��ַ����Ƿ��������
	 * @param str
	 * @return true ���� �� false ������
	 */
	public static boolean ishaveDigit(String str) {
		// �ж�һ���ַ����Ƿ�������
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * ��mysql�������ͣ�����Ϊ java ��������
	 * @param dataType
	 * @return
	 */
	public static MysqlDataType getFieldType(String dataType) {
		MysqlDataType[] types = MysqlDataType.values();
		for(int i=0; i<types.length; i++) {
			if(types[i].getMySqlDataType().equalsIgnoreCase(dataType)) {
				return types[i];
			}
		}
		return null;
	}
	
	/**
	 * ��ȡibatisDao������
	 * @param tableName
	 * @return
	 */
	public static String getIbatitsDaoClassName(String tableName) {
		String entityName = tableNameToHumpStr(tableName);
		String interfaceName = entityName+"Dao";
		return interfaceName;
	}
	
	/**
	 * ��ȡservice��ӿ�������
	 * @param tableName
	 * @return
	 */
	public static String getAppServiceClassName(String tableName) {
		String entityName = tableNameToHumpStr(tableName);
		String interfaceName = entityName+"Service";
		return interfaceName;
	}
	
	/**
	 * ��ȡservice��ʵ��������
	 * @param tableName
	 * @return
	 */
	public static String getAppServiceImplClassName(String tableName) {
		String entityName = tableNameToHumpStr(tableName);
		String interfaceName = entityName+"ServiceImpl";
		return interfaceName;
	}
	
	/**
	 * ��ȡservice��ʵ��������
	 * @param tableName
	 * @return
	 */
	public static String getAppControllerClassName(String tableName) {
		String entityName = tableNameToHumpStr(tableName);
		String interfaceName = entityName+"Controller";
		return interfaceName;
	}
	
	/**
	 * ��ȡ ibatisEntity������
	 * @param className
	 * @return
	 */
	public static String getIbatisEntityName(String tableName) {
		String entityName = tableNameToHumpStr(tableName);
//		String interfaceName = entityName+"Entity";
		return entityName;
	}
	
	/**
	 * ���ݿ������ ��ʽ��Ϊ �շ��ʶ������ĸ��д����������֣������������ֵ��ַ���
	 * @param className
	 * @return
	 */
	public static String tableNameToHumpStr(String tableName) {
		StringBuilder s = new StringBuilder();
		if(tableName.indexOf("_") > -1) {//�����»���
			String[] sArray = tableName.toLowerCase().split("_");
			for(int i=0; i<sArray.length; i++) {
				if(sArray[i].equals(DbFile.singleton.getFileConfig().getDeleteStr())) {
					continue;
				}
				if(!ishaveDigit(sArray[i])) {
					s.append(firstToUpperCase(sArray[i]));
				}
			}
			return s.toString();
		} else {
			return firstToUpperCase(tableName.toLowerCase());
		}
	}
	
	
	public static String getIsOrderByColumn(String str) {
		return "isOrderBy" + columnNameToHumpStr(str);
	}
	
	public static String getIsOrderByColumnDesc(String str) {
		return "isOrderBy" + columnNameToHumpStr(str) + "Desc";
	}
	
	public static String getIsGroupByColumn(String str) {
		return "isGroupBy" + columnNameToHumpStr(str);
	}
	
	/**
	 * ���ݿ�� �ֶ�����  ��ʽ��Ϊ �շ��ʶ������ĸСд
	 * @param columnName
	 * @return
	 */
	public static String columnNameToHumpStr(String str) {
		StringBuilder s = new StringBuilder();
		if(str.indexOf("_") > -1) {//�����»���
			String[] sArray = str.toLowerCase().split("_");
			s.append(firstToLowerCase(sArray[0]));
			for(int i=1; i<sArray.length; i++) {
				s.append(firstToUpperCase(sArray[i]));
			}
			return s.toString();
		} else {
			return firstToLowerCase(str.toLowerCase());
		}
	}
	
	
}
