package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.Annotation.Column;
import com.casualsource.casualcrawler.datawriter.Annotation.Table;

import java.sql.Date;


@Table(name="ptt_message")
public class PttBean {
    @Column(name="p_id", properity= Column.ColumnProperity.INTEGER)
    private Integer pid;

    @Column(name="p_content", properity= Column.ColumnProperity.VARCHAR, length = 100)
    private String content;

    @Column(name="p_date", properity= Column.ColumnProperity.DATE)
    private Date date;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
