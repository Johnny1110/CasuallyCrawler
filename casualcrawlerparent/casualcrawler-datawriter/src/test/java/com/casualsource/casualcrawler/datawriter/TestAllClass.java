package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.DataWriterImpl.DataWriters;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestAllClass {
    @Test
    public void testAnnotation(){
        DataWriterUtils utils = new DataWriterUtils();
        List<MetaData> list = utils.reflectDataColumn(PttBean.class);
        list.forEach(metaData -> {
            System.out.println(metaData.getTableName());
            System.out.println(metaData.getColumnName());
            System.out.println(metaData.getColumnProp());
            System.out.println(metaData.getFieldName());
            System.out.println(metaData.getLength());
            System.out.println("---------------------------------");
        });
    }

    @Test
    public void testCreateTable(){
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        dataBaseInfo.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        dataBaseInfo.setUrl("jdbc:mysql://localhost:3306/ptt_crawler?serverTimezone=CST");
        dataBaseInfo.setUsername("root");
        dataBaseInfo.setPasswd("Jarvan1110");
        DataWriter<MetaData> writer = DataWriters.genericJdbcDataWriter(dataBaseInfo).bulid();
        writer.createTable(DataWriterUtils.reflectDataColumn(PttBean.class));
    }
}
