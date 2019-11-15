package com.casualsource.casualcrawler.parser;

import java.util.List;

public interface HtmlParser {
    void setHtml(String html);

    void setCssSelector(String selctor);

    List<ParseResult> doParse();

}
