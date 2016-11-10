package com.lp.ibatis;

public class TableColumnInfo implements Cloneable{
	/**
	 * �ֶ����� ,���� DICT_TYPE
	 */
	private String columnName;
	/**
	 * �ֶ�ע��
	 */
	private String columnComment;
	/**
	 * �������� ���� varchar
	 */
	private String dataType;
	/**
	 * �ֶ���������������� �������� ��PRI��
	 */
	private String columnKey;
	/**
	 * true �����ֶΣ�false �������ֶ�
	 * �Ƿ��������ֶΣ�ֻ�������ֶΣ�����   nonUnique��indexName��indexSeq   ����ֵ
	 */
	private boolean isIndex;
	/**
	 * �����Ƿ�����ظ� 0 �����ԣ�1 ����
	 */
	private String nonUnique;
	/**
	 * ��������
	 */
	private String indexName;
	/**
	 * ����˳��
	 */
	private String indexSeq;
	
	public TableColumnInfo clone() {
		try {
			return (TableColumnInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public boolean getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(boolean isIndex) {
		this.isIndex = isIndex;
	}
	public String getNonUnique() {
		return nonUnique;
	}
	public void setNonUnique(String nonUnique) {
		this.nonUnique = nonUnique;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexSeq() {
		return indexSeq;
	}
	public void setIndexSeq(String indexSeq) {
		this.indexSeq = indexSeq;
	}
	

}
