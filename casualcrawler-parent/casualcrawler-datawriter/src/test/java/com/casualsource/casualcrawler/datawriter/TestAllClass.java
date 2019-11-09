package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.DataWriterImpl.DataWriters;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestAllClass {
    DataBaseInfo dataBaseInfo;
    List<PttBean> beans = new ArrayList<>();
    @Before
    public void prepare(){
        dataBaseInfo = new DataBaseInfo();
        dataBaseInfo.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        dataBaseInfo.setUrl("jdbc:mysql://localhost:3306/ptt_crawler?serverTimezone=CST");
        dataBaseInfo.setUsername("root");
        dataBaseInfo.setPasswd("Jarvan1110");

        PttBean bean1 = new PttBean();
        bean1.setPid(1);
        bean1.setContent("hello dog");
        bean1.setDate(new Date(System.currentTimeMillis()));

        PttBean bean2 = new PttBean();
        bean2.setPid(2);
        bean2.setContent("hello cat");
        bean2.setDate(new Date(System.currentTimeMillis()));

        beans.add(bean1);
        beans.add(bean2);
    }
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
        DataWriter<PttBean> writer = DataWriters.genericJdbcDataWriter(dataBaseInfo).bulid();
        writer.createStorage(PttBean.class);
    }

    @Test
    public void testCreateTableAndInsertData(){
        DataWriter<PttBean> writer = DataWriters.genericJdbcDataWriter(dataBaseInfo).bulid();
        writer.createStorage(PttBean.class);

        List<PttBean> beans = new ArrayList<>();
        PttBean bean = new PttBean();
        bean.setPid(1);
        bean.setContent("hello cat");
        bean.setDate(new Date(System.currentTimeMillis()));
        beans.add(bean);

        writer.writeData(beans);
    }

}
