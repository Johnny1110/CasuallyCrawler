package com.casualsource.casualcrawler.parser;

import com.casualsource.casualcrawler.requests.HttpRequest;
import com.casualsource.casualcrawler.requests.HttpRequests;
import com.casualsource.casualcrawler.requests.HttpResponse;
import com.casualsource.casualcrawler.requests.HttpResponses;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlParserTest {
    HtmlParser parser;
    String html;
    String pttGossipingURL = "https://www.ptt.cc/ask/over18";
    Map<String, String> postData = new HashMap<>();

    @Before
    public void prepareParaser() throws IOException {
        postData.put("from", "/bbs/Gossiping/index.html");
        postData.put("yes", "yes");
        HttpResponse resp = HttpResponses.createDefault();
        HttpRequest req = HttpRequests.createDefault();
        resp = req.post(pttGossipingURL, postData);
        html = resp.getBody();
        parser = HtmlParsers.createDefault();
    }

    @Test
    public void testHtmlParser(){
        parser.setHtml(html);
        parser.setCssSelector("div.title a[href]");
        List<ParseResult> results = parser.doParse();
        results.forEach(r -> System.out.println(r.getAttr("href")));

        parser.setCssSelector(".btn.wide");
        List<ParseResult> results1 = parser.doParse();
        results1.forEach(r -> System.out.println(r.getAttr("href")));
    }
}
