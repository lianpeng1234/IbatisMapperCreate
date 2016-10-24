package com.lp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lp.db.DbFile;
import com.lp.db.MysqlDataType;

public class UtilLp {
	/**
	 * 字符串，首字母大写
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
       return  str;
    }
	
	/**
	 * 字符串，首字母小写
	 * @param str
	 * @return
	 */
	public static String firstToLowerCase(String str) {
		str = str.substring(0, 1).toLowerCase() + str.substring(1);
       return  str;
    }
	
	/**
	 * 判断字符串是否包含数字
	 * @param str
	 * @return true 包含 ， false 不包含
	 */
	public static boolean ishaveDigit(String str) {
		// 判断一个字符串是否含有数字
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 将mysql数据类型，翻译为 java 数据类型
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
	 * 获取ibatisDao类名称
	 * @param tableName
	 * @return
	 */
	public static String getIbatitsDaoClassName(String tableName) {
		String entityName = processString(tableName);
		String interfaceName = entityName+"Dao";
		return interfaceName;
	}
	
	/**
	 * 获取service层接口类名称
	 * @param tableName
	 * @return
	 */
	public static String getAppServiceClassName(String tableName) {
		String entityName = processString(tableName);
		String interfaceName = entityName+"Service";
		return interfaceName;
	}
	
	/**
	 * 获取service层实现类名称
	 * @param tableName
	 * @return
	 */
	public static String getAppServiceImplClassName(String tableName) {
		String entityName = processString(tableName);
		String interfaceName = entityName+"ServiceImpl";
		return interfaceName;
	}
	
	/**
	 * 获取 ibatisEntity类名称
	 * @param className
	 * @return
	 */
	public static String getIbatisEntityName(String tableName) {
		String entityName = processString(tableName);
//		String interfaceName = entityName+"Entity";
		return entityName;
	}
	
	/**
	 * 类名 格式化为 驼峰标识，首字母大写,如果有数字，放弃包含数字的字符串
	 * @param className
	 * @return
	 */
	public static String processString(String str) {
		StringBuilder s = new StringBuilder();
		if(str.indexOf("_") > -1) {//包含下划线
			String[] sArray = str.toLowerCase().split("_");
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
			return firstToUpperCase(str.toLowerCase());
		}
	}
	
	/**
	 * 格式化为 驼峰标识，首字母大写
	 * @param str
	 * @return
	 */
	public static String processStringFull(String str) {
		StringBuilder s = new StringBuilder();
		if(str.indexOf("_") > -1) {//包含下划线
			String[] sArray = str.toLowerCase().split("_");
			for(int i=0; i<sArray.length; i++) {
				s.append(firstToUpperCase(sArray[i]));
			}
			return s.toString();
		} else {
			return firstToUpperCase(str.toLowerCase());
		}
	}
	
	
	public static String getIsOrderByColumn(String str) {
		return "isOrderBy" + processStringFull(str);
	}
	
	public static String getIsOrderByColumnDesc(String str) {
		return "isOrderBy" + processStringFull(str) + "Desc";
	}
	
	public static String getIsGroupByColumn(String str) {
		return "isGroupBy" + processStringFull(str);
	}
	
	/**
	 * 格式化为 驼峰标识，首字母小写
	 * @param columnName
	 * @return
	 */
	public static String processColumnName(String str) {
		StringBuilder s = new StringBuilder();
		if(str.indexOf("_") > -1) {//包含下划线
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
