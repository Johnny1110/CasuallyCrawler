package com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc;

public class DataBaseInfo {
    private String url;
    private String username;
    private String passwd;
    private String jdbcDriver;

    public DataBaseInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public DataBaseInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public DataBaseInfo setPasswd(String passwd) {
        this.passwd = passwd;
        return this;
    }

    public DataBaseInfo setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }
}
