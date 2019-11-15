package com.casualsource.casualcrawler.pttcrawler;

import com.casualsource.casualcrawler.parser.HtmlParser;
import com.casualsource.casualcrawler.parser.HtmlParsers;
import com.casualsource.casualcrawler.parser.ParseResult;
import com.casualsource.casualcrawler.requests.HttpRequest;
import com.casualsource.casualcrawler.requests.HttpRequests;
import com.casualsource.casualcrawler.requests.HttpResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CrawlableUrlFilter {

    private static HttpRequest httpRequest;
    private static HtmlParser htmlParser;
    private static final String pttIndexUrl = "https://www.ptt.cc/ask/over18";
    private static Map<String, String> postData = new HashMap<>();
    private static Date endDate;
    private static SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss yyyy", Locale.US);
    private String nextPage = "";
    private List<String> urlList = new ArrayList<>();

    static{
        httpRequest = HttpRequests.createDefault();
        htmlParser = HtmlParsers.createDefault();
        postData.put("from", "/bbs/Gossiping/index.html");
        postData.put("yes", "yes");
        try {
            endDate = sdf.parse("Mon Nov 15 16:20:00 2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        CrawlableUrlFilter crawlableUrlFilter = new CrawlableUrlFilter();
        crawlableUrlFilter.startCrawl();
    }

    public void startCrawl() throws IOException, ParseException {
        HttpResponse resp = httpRequest.post(pttIndexUrl, postData); //進入首頁。取的 session 連線。
        fillUrlList(getUrlPages(resp.getBody()));
    }

    private void crawlNextPage(String page) throws IOException, ParseException {
        HttpResponse resp = httpRequest.get(page);
        fillUrlList(getUrlPages(resp.getBody()));
    }

    private void fillUrlList(List<String> list) throws IOException, ParseException {
        for(int i = 0; i < list.size(); ++i){
            if(varifyDate(list.get(i))){
                for(int j = i; j < list.size(); ++j){
                    System.out.println(list.get(j));
                    urlList.add(list.get(j));
                }
                break;
            }
        }
        if(varifyDate(list.get(0))){
            crawlNextPage(nextPage);
        }
    }

    private List<String> getUrlPages(String htmlBody) throws IOException {
        htmlParser.setHtml(htmlBody);
        htmlParser.setCssSelector("a.btn.wide");
        List<ParseResult> btnElems = htmlParser.doParse();
        nextPage = buildPttUrl(btnElems.get(1).getAttr("href")); //設定下一頁 index url
        System.out.println(nextPage);
        htmlParser.setCssSelector("div.title a");
        List<ParseResult> titleUrlElems = htmlParser.doParse();
        List<String> urlPage = new ArrayList<>();
        titleUrlElems.forEach(t ->{
            urlPage.add(t.getAttr("href"));
        });
        return urlPage;
    }

    private String buildPttUrl(String suffix){
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.ptt.cc/");
        sb.append(suffix);
        return sb.toString();
    }

    private boolean varifyDate(String firstTitleUrl){
        try {
            HttpResponse resp = httpRequest.get(buildPttUrl(firstTitleUrl));
            htmlParser.setHtml(resp.getBody());
            htmlParser.setCssSelector(".article-meta-value");
            List<ParseResult> metaList = htmlParser.doParse();
            String dateStr = metaList.get(3).getText();
            Date date = sdf.parse(dateStr);
            return date.after(endDate);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getUrlList(){
        return this.urlList;
    }

}
