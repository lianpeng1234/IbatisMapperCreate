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

public class CreateAppServiceImplFileUtil {

	/**
	 * @param tableName 数据库表名称
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("deprecation")
	public static void createAppServiceImplFile(List<TableColumnInfo> tableInfo, String tableName) throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new ClassTemplateLoader(TemplatePath.class));
		Template template = configuration.getTemplate("ibatisServiceImpl.ftl", Locale.CHINA, "UTF-8");
		//
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("packagePath", DbFile.singleton.getFileConfig().getServiceImplPackage());//
		rootMap.put("ibatisEntityPackage", DbFile.singleton.getFileConfig().getIbatisEntityPackage());//
		String entityName = UtilLp.getIbatisEntityName(tableName);
		String className = UtilLp.getAppServiceImplClassName(tableName);
		rootMap.put("className", className);//接口名称
		rootMap.put("entityName", entityName);//实体名称
		//
		rootMap.put("servicePackagePath", DbFile.singleton.getFileConfig().getServicePackage());
		String interfaceNameService = UtilLp.getAppServiceClassName(tableName);
		rootMap.put("interfaceNameService", interfaceNameService);
		//
		rootMap.put("ibatisDaoPackage", DbFile.singleton.getFileConfig().getIbatisDaoPackage());//
		String ibatisDaoName = UtilLp.getIbatitsDaoClassName(tableName);
		rootMap.put("ibatisDaoName", ibatisDaoName);
		//
		rootMap.put("ibatisDaoVar", ibatisDaoName.subSequence(0, 1).toString().toLowerCase() + ibatisDaoName.substring(1));
		//
		List<Map<String,Object>> methodList = CreateIbatisFile.getMethodList(tableInfo);
		rootMap.put("methodList", methodList);
		//import
		rootMap.put("importList", CreateIbatisFile.getImportList(tableInfo));
		//
		List<Map<String,String>> listMap = CreateIbatisFile.getEntityField(tableInfo);
		rootMap.put("listMap", listMap);
		//
		rootMap.put("isCreateMoveSql", DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		
		FileUtil.writeIbatisFile(template, rootMap, className+".java", "ServiceImpl");//entityName
	}
	
}
