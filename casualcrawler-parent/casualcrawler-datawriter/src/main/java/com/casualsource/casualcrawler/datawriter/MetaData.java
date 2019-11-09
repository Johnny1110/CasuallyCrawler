package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.Annotation.Column;

public class MetaData {
    private String tableName;
    private String fieldName;
    private String columnName;
    private String ColumnProp;
    private int length;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnProp() {
        return ColumnProp;
    }

    public void setColumnProp(String columnProp) {
        ColumnProp = columnProp;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
