package com.casualsource.casualcrawler.datawriter.DataWriterImpl;

import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;

public class DataWriters {
    private DataWriters(){}

    public static GenericJdbcDataWriter genericJdbcDataWriter(DataBaseInfo dataBaseInfo){
        return new GenericJdbcDataWriter(dataBaseInfo);
    }
}
