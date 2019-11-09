package com.casualsource.casualcrawler.datawriter.DataWriterImpl;

import com.casualsource.casualcrawler.datawriter.DataWriter;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.JdbcTemplateConfig;
import com.casualsource.casualcrawler.datawriter.DataWriterUtils;
import com.casualsource.casualcrawler.datawriter.MetaData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GenericJdbcDataWriter{

    GenericJdbcDataWriter(DataBaseInfo dataBaseInfo){
        JdbcTemplateConfig.setDriver(dataBaseInfo.getJdbcDriver());
        JdbcTemplateConfig.setUsername(dataBaseInfo.getUsername());
        JdbcTemplateConfig.setPassword(dataBaseInfo.getPasswd());
        JdbcTemplateConfig.setUrl(dataBaseInfo.getUrl());
    }

    public JdbcDataWriter bulid(){
        return new JdbcDataWriter();
    }

    private class JdbcDataWriter<T> implements DataWriter<T>{
        private JdbcOperations jdbcOperations;

        private JdbcDataWriter(){
            ApplicationContext ctx = new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);
            this.jdbcOperations = ctx.getBean(JdbcOperations.class);
        }


        @Override
        public void createStorage(Class<? extends Object> clazz) {
            List<MetaData> mataDataList = DataWriterUtils.reflectDataColumn(clazz);
            StringBuilder createStat = new StringBuilder();
            StringBuilder dropIfExists = new StringBuilder();
            dropIfExists.append("drop table if exists ")
                    .append(mataDataList.get(0).getTableName())
                    .append(";");

            createStat.append("create table ")
              .append(mataDataList.get(0).getTableName())
              .append("(");
            for (int i = 0; i < mataDataList.size(); ++i) {
                MetaData m = mataDataList.get(i);
                createStat.append(m.getColumnName()).append(" ");
                createStat.append(m.getColumnProp());
                if (m.getColumnProp().equals("VARCHAR")) {
                    createStat.append("(").append(m.getLength()).append(")");
                }
                if(i < mataDataList.size()-1){
                    createStat.append(", ");
                }
            }
            createStat.append(");");

            jdbcOperations.execute(dropIfExists.toString());
            jdbcOperations.execute(createStat.toString());
        }

        @Override
        public int[] writeData(List<T> entityList) {
            final List<T> finalEntityList = entityList;
            String sql = DataWriterUtils.genericInsertSql(entityList.get(0).getClass());
            System.out.println(sql);
            BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object o = entityList.get(i);
                    Field[] fields = o.getClass().getDeclaredFields();

                    for(int j = 0; j < fields.length; ++j){
                        if(fields[j].getGenericType().getTypeName().equals("java.lang.String")){
                            try {
                                String funcName = generateGetFunc(fields[j].getName());
                                Method getter = o.getClass().getMethod(funcName, null);
                                ps.setString(j+1, (String) getter.invoke(o, null));
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                        if(fields[j].getGenericType().getTypeName().equals("java.lang.Integer")){
                            try {
                                String funcName = generateGetFunc(fields[j].getName());
                                System.out.println(funcName);
                                Method getter = o.getClass().getMethod(funcName, null);
                                ps.setInt(j+1, (Integer) getter.invoke(o, null));
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                        if(fields[j].getGenericType().getTypeName().equals("java.sql.Date")){
                            try {
                                String funcName = generateGetFunc(fields[j].getName());
                                Method getter = o.getClass().getMethod(funcName, null);
                                ps.setDate(j+1, (Date) getter.invoke(o, null));
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public int getBatchSize() {
                    return finalEntityList.size();
                }
            };
            return jdbcOperations.batchUpdate(sql, setter);
        }

        private String generateGetFunc(String name) {
            char[] cs=name.toCharArray();
            cs[0]-=32;
            return "get" + String.valueOf(cs);
        }

    }

}
