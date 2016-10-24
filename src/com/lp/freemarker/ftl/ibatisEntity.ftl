package ${packagePath};

<#list importList?if_exists as importPackage>  
import ${importPackage.importPackage};
</#list>

public class ${className} {

<#list entityFieldList?if_exists as field>
	/**
	 * ${(field.fieldExplain)!}
	 */
	private ${field.fieldType} ${field.fieldName};
   
</#list>
<#if isCreateMoveSql>
<#--生成动态sql-->
	/**
	 * true   open
	 * false  close
	 */
	private boolean openGroupBy;
	
	/**
	 * true   open
	 * false  close
	 */
	private boolean openOrderBy;
	
	private Integer limitStart;
	
	private Integer limitEnd;
</#if>
<#list entityMethodList?if_exists as method>
	/**
	 * ${method.methodExplain?if_exists}
	 */
	public ${method.retutnType} get${method.methodName}() {
   		return ${method.fieldName};
   	}
   	/**
	 * ${method.methodExplain?if_exists}
	 */
	public ${className} set${method.methodName}(${method.paramType} ${method.paramName}) {
   		this.${method.fieldName} = ${method.paramName};
   		return this;
	}
	
</#list>
<#if isCreateMoveSql>
<#--生成动态sql-->
	/**
	 * true   open
	 * false  close
	 */
	public boolean getOpenGroupBy() {
		return openGroupBy;
	}
	/**
	 * true   open
	 * false  close
	 */
	public ${className} setOpenGroupBy(boolean openGroupBy) {
		this.openGroupBy = openGroupBy;
		return this;
	}
	
	/**
	 * true   open
	 * false  close
	 */
	public boolean getOpenOrderBy() {
		return openOrderBy;
	}
	/**
	 * true   open
	 * false  close
	 */
	public ${className} setOpenOrderBy(boolean openOrderBy) {
		this.openOrderBy = openOrderBy;
		return this;
	}
	
	public Integer getLimitStart() {
		return limitStart;
	}
	public ${className} setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
		return this;
	}

	public Integer getLimitEnd() {
		return limitEnd;
	}
	public ${className} setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
		return this;
	}
</#if>
}
