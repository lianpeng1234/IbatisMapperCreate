package com.lp.ibatis;

import java.io.IOException;
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

public class CreateIbatisXmlFileUtil {

	/**
	 * 
	 * @param tableInfo
	 * @param ibatisDaoPackage ibatisDao文件的package路径
	 * @param ibatisEntityPackage ibatisEntity文件的package路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("deprecation")
	public static void createIbatisXmlFile(List<TableColumnInfo> tableInfo, String tableName) throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplatePath.class));
		Template template = configuration.getTemplate("ibatisXml.ftl", Locale.CHINA, "UTF-8");
		//
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("jingHao", "#");
		//
		rootMap.put("tableName", tableName);
		//
		rootMap.put("ibatisDaoPackagePath", DbFile.singleton.getFileConfig().getIbatisDaoPackage());
		rootMap.put("ibatisDaoName", UtilLp.getIbatitsDaoClassName(tableName));
		//
		rootMap.put("ibatisEntityPackagePath",DbFile.singleton.getFileConfig().getIbatisEntityPackage());
		String ibatisEntityName = UtilLp.getIbatisEntityName(tableName);
		rootMap.put("ibatisEntityName", ibatisEntityName);
		//实体类 属性
		for(int i=0; i<tableInfo.size(); i++) {
			TableColumnInfo columnInfo = tableInfo.get(i);
			String columnName = columnInfo.getColumnName();
			String dataType = columnInfo.getDataType();
			String columnKey = columnInfo.getColumnKey();//表字段索引类型
			if("PRI".equals(columnKey)) {//主键
				//
				String primaryKeyIbatisJdbcType = UtilLp.getFieldType(dataType).getIbatisJdbcType();
				rootMap.put("primaryKeyColumn", columnName);
				rootMap.put("primaryKeyProperty", UtilLp.columnNameToHumpStr(columnName));
				rootMap.put("primaryKeyIbatisJdbcType", primaryKeyIbatisJdbcType);
				break;
			}
		}
		//
		List<Map<String,String>> listMap = CreateIbatisFile.getEntityField(tableInfo);
		rootMap.put("listMap", listMap);
		//
		List<Map<String,Object>> methodList = CreateIbatisFile.getMethodList(tableInfo);
		rootMap.put("methodList", methodList);//
		//
		rootMap.put("isCreateMoveSql", DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		
		FileUtil.writeIbatisFile(template, rootMap, UtilLp.getIbatitsDaoClassName(tableName)+"Mapper.xml", "IbatisXml");//ibatisEntityName
	}
	
}
