package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.Annotation.Column;
import com.casualsource.casualcrawler.datawriter.Annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataWriterUtils {

    public static List<MetaData> reflectDataColumn(Class<? extends Object> data){
        String tableName = data.getAnnotation(Table.class).name();
        List<Field> fields = Arrays.asList(data.getDeclaredFields());
        List<MetaData> metaDataList = new ArrayList<>();
        fields.forEach(f ->{
            MetaData metaData = new MetaData();
            metaData.setTableName(tableName);
            metaData.setColumnName(f.getAnnotation(Column.class).name());
            metaData.setFieldName(f.getName());
            metaData.setColumnProp(f.getAnnotation(Column.class).properity().toString());
            metaData.setLength(f.getAnnotation(Column.class).length());
            metaDataList.add(metaData);
        });
        return metaDataList;
    }

    public static String genericInsertSql(Class<?> clazz){
        List<MetaData> metaDataList = reflectDataColumn(clazz);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(metaDataList.get(0).getTableName())
                .append("(");
        for(int i = 0; i < metaDataList.size(); ++i){
            sql.append(metaDataList.get(i).getColumnName());
            if(i < metaDataList.size() - 1){
                sql.append(", ");
            }
        }
        sql.append(")")
                .append("VALUES")
                .append("(");
        for(int i = 0; i < metaDataList.size(); ++i){
            sql.append("?");
            if(i < metaDataList.size() - 1){
                sql.append(", ");
            }
        }
        sql.append(");");
        return sql.toString();
    }

}
