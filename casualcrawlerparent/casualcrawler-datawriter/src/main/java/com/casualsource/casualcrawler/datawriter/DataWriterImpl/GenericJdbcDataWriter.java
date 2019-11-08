package com.casualsource.casualcrawler.datawriter.DataWriterImpl;

import com.casualsource.casualcrawler.datawriter.DataWriter;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.JdbcTemplateConfig;
import com.casualsource.casualcrawler.datawriter.MetaData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;

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

    private class JdbcDataWriter implements DataWriter<MetaData>{
        private JdbcOperations jdbcOperations;

        private JdbcDataWriter(){
            ApplicationContext ctx = new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);
            this.jdbcOperations = ctx.getBean(JdbcOperations.class);
        }


        @Override
        public void createTable(List<MetaData> list) {
            StringBuilder createStat = new StringBuilder();
            StringBuilder dropIfExists = new StringBuilder();
            dropIfExists.append("drop table if exists ")
                    .append(list.get(0).getTableName())
                    .append(";");

            createStat.append("create table ")
              .append(list.get(0).getTableName())
              .append("(");
            for (int i = 0; i < list.size(); ++i) {
                MetaData m = list.get(i);
                createStat.append(m.getColumnName()).append(" ");
                createStat.append(m.getColumnProp());
                if (m.getColumnProp().equals("VARCHAR")) {
                    createStat.append("(").append(m.getLength()).append(")");
                }
                if(i < list.size()-1){
                    createStat.append(", ");
                }
            }
            createStat.append(");");

            jdbcOperations.execute(dropIfExists.toString());
            jdbcOperations.execute(createStat.toString());
        }
    }

}
