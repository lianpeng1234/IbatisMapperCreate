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

public class CreateIbatisDaoFileUtil {

	/**
	 * 
	 * @param tableInfo
	 * @param ibatisDaoPackage ibatisDao文件的package路径
	 * @param ibatisEntityPackage ibatisEntity文件的package路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("deprecation")
	public static void createIbatisDaoFile(List<TableColumnInfo> tableInfo, String tableName) throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplatePath.class));
		Template template = configuration.getTemplate("ibatisDao.ftl", Locale.CHINA, "UTF-8");
		//
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("packagePath", DbFile.singleton.getFileConfig().getIbatisDaoPackage());//
		rootMap.put("importPackage", DbFile.singleton.getFileConfig().getIbatisEntityPackage());//
		String entityName = UtilLp.getIbatisEntityName(tableName);
		String interfaceName = UtilLp.getIbatitsDaoClassName(tableName);
		rootMap.put("interfaceName", interfaceName);//接口名称
		rootMap.put("entityName", entityName);//实体名称
		//
		List<Map<String,Object>> methodList = CreateIbatisFile.getMethodList(tableInfo);
		rootMap.put("methodList", methodList);//
		//import
		rootMap.put("importList", CreateIbatisFile.getImportList(tableInfo));
		//
		rootMap.put("isCreateMoveSql", DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		
		FileUtil.writeIbatisFile(template, rootMap, interfaceName+".java", "IbatisDao");//entityName
	}
	
}


































