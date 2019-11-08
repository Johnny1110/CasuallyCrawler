package com.casualsource.casualcrawler.datawriter.DataWriterImpl;

import com.casualsource.casualcrawler.datawriter.DataWriter;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.JdbcUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class GenericJdbcDataWriter implements DataWriter {
    private DataBaseInfo dbInfo;

    GenericJdbcDataWriter(DataBaseInfo dbInfo){
        this.dbInfo = dbInfo;
    }

    public JdbcDataWriter bulid(){
        return new JdbcDataWriter();
    }

    private class JdbcDataWriter{
        private DataSource dataSource;

        JdbcDataWriter(){
            dataSource = JdbcUtils.getDataSource(dbInfo);
        }

        public void test() throws SQLException {
            Connection connection = dataSource.getConnection();
        }

    }

}
