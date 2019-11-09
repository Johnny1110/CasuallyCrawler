package com.casualsource.casualcrawler.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class BasicHtmlParser implements HtmlParser {

    BasicHtmlParser(){}

    private String html;
    private String cssSelector;

    @Override
    public List<ParseResult> doParse() {
        verify();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(cssSelector);
        List<ParseResult> results = new ArrayList<>();
        elements.forEach(e -> results.add(new ParseResult(e)));
        return results;
    }

    private void verify() {
        if(this.html == null || this.cssSelector == null) {
            throw new ParserException("html or css-celector not initialze, please set html and css-selector values in BasicHtmlParser.");
        }
    }

    @Override
    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public void setCssSelector(String cssSelector) {
        this.cssSelector = cssSelector;
    }
}
