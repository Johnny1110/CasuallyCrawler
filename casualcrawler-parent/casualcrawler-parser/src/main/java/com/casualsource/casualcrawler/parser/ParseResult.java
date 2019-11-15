package com.casualsource.casualcrawler.parser;

import org.jsoup.nodes.Element;

public class ParseResult {
    private Element element;

    ParseResult(Element element){
        this.element = element;
    }

    public String getAttr(String attrName){
        return this.element.attr(attrName);
    }

    public String getText(){
        return this.element.text();
    }

    public String getPureText(){
        return this.element.ownText();
    }

    public ParseResult getChild(int child){
        return new ParseResult(this.element.child(child));
    }
}
