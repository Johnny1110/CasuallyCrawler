package com.casualsource.casualcrawler.datawriter;

import com.casualsource.casualcrawler.datawriter.Annotation.Column;
import com.casualsource.casualcrawler.datawriter.Annotation.Table;

@Table(name="ptt_message")
public class PttBean {
    @Column(name="p_id", properity= Column.ColumnProperity.INTEGER)
    public String pid;

    @Column(name="p_content", properity= Column.ColumnProperity.VARCHAR)
    public String content;
}
