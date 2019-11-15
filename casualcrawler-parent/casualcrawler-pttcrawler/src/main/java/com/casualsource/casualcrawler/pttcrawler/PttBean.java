package com.casualsource.casualcrawler.pttcrawler;

import com.casualsource.casualcrawler.datawriter.Annotation.Column;
import com.casualsource.casualcrawler.datawriter.Annotation.Table;

import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "public.ptt")
public class PttBean {
    @Column(name = "id", properity = Column.ColumnProperity.VARCHAR, length = 36)
    private String id;
    @Column(name = "site", properity = Column.ColumnProperity.VARCHAR, length = 36)
    private String site;
    @Column(name = "rid", properity = Column.ColumnProperity.VARCHAR, length = 36)
    private String rid;
    @Column(name = "pid", properity = Column.ColumnProperity.VARCHAR, length = 36)
    private String pid;
    @Column(name = "url", properity = Column.ColumnProperity.VARCHAR, length = 1024)
    private String url;
    @Column(name = "title", properity = Column.ColumnProperity.VARCHAR, length = 512)
    private String title;
    @Column(name = "content", properity = Column.ColumnProperity.TEXT)
    private String content;
    @Column(name = "author", properity = Column.ColumnProperity.VARCHAR, length = 128)
    private String author;
    @Column(name = "postDate", properity = Column.ColumnProperity.TIMESTAMP)
    private Timestamp postDate;
    @Column(name = "updateDate", properity = Column.ColumnProperity.DATE)
    private Date updateDate;
    @Column(name = "replycnt", properity = Column.ColumnProperity.INTEGER)
    private Integer replycnt;
    @Column(name = "updatecnt", properity = Column.ColumnProperity.INTEGER)
    private Integer updatecnt;
    @Column(name = "push", properity = Column.ColumnProperity.INTEGER)
    private Integer push;
    @Column(name = "shush", properity = Column.ColumnProperity.INTEGER)
    private Integer shush;
    @Column(name = "arrow", properity = Column.ColumnProperity.INTEGER)
    private Integer arrow;
    @Column(name = "link", properity = Column.ColumnProperity.VARCHAR, length = 1024)
    private String link;
    @Column(name = "lang", properity = Column.ColumnProperity.TEXT)
    private String lang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getReplycnt() {
        return replycnt;
    }

    public void setReplycnt(Integer replycnt) {
        this.replycnt = replycnt;
    }

    public Integer getUpdatecnt() {
        return updatecnt;
    }

    public void setUpdatecnt(Integer updatecnt) {
        this.updatecnt = updatecnt;
    }

    public Integer getPush() {
        return push;
    }

    public void setPush(Integer push) {
        this.push = push;
    }

    public Integer getShush() {
        return shush;
    }

    public void setShush(Integer shush) {
        this.shush = shush;
    }

    public Integer getArrow() {
        return arrow;
    }

    public void setArrow(Integer arrow) {
        this.arrow = arrow;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
