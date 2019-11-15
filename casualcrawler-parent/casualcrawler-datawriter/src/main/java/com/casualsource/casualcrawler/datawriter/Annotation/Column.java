package com.casualsource.casualcrawler.datawriter.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public enum ColumnProperity{
        INTEGER,
        VARCHAR,
        DATE,
        TEXT,
        TIMESTAMP;
        ;
    }
    String name();
    ColumnProperity properity();
    int length() default 50;

}
