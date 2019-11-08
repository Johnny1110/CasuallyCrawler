package com.casualsource.casualcrawler.parser;

public class HtmlParsers {
    private HtmlParsers(){}

    public static BasicHtmlParser createDefault(){
        return new BasicHtmlParser();
    }
}
