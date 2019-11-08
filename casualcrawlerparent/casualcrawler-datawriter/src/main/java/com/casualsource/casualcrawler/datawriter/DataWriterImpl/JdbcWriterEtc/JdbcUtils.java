package com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class JdbcUtils {
    private JdbcUtils(){}

    public static DataSource getDataSource(DataBaseInfo dbInfo){
        try {
            ComboPooledDataSource c3p0 = new ComboPooledDataSource();
            c3p0.setDriverClass(dbInfo.getJdbcDriver());
            c3p0.setJdbcUrl(dbInfo.getUrl());
            c3p0.setUser(dbInfo.getUsername());
            c3p0.setPassword(dbInfo.getPasswd());
            return c3p0;
        } catch (PropertyVetoException e) {
            throw new RuntimeException("jdbc connection pool initialize fail");
        }
    }
}
