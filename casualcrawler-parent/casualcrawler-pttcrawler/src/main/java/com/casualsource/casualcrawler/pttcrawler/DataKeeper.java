package com.casualsource.casualcrawler.pttcrawler;

import com.casualsource.casualcrawler.datawriter.DataWriter;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.DataWriters;
import com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc.DataBaseInfo;
import com.casualsource.casualcrawler.parser.HtmlParser;
import com.casualsource.casualcrawler.parser.HtmlParsers;
import com.casualsource.casualcrawler.parser.ParseResult;
import com.casualsource.casualcrawler.requests.HttpRequest;
import com.casualsource.casualcrawler.requests.HttpRequests;
import com.casualsource.casualcrawler.requests.HttpResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataKeeper {

    private static HttpRequest httpRequest;
    private static HtmlParser htmlParser;
    private static final String pttIndexUrl = "https://www.ptt.cc/ask/over18";
    private static Map<String, String> postData = new HashMap<>();
    private static DataBaseInfo dbInfo = new DataBaseInfo();
    private static DataWriter dataWriter;
    private static SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss yyyy", Locale.US);


    static{
        httpRequest = HttpRequests.createDefault();
        htmlParser = HtmlParsers.createDefault();
        postData.put("from", "/bbs/Gossiping/index.html");
        postData.put("yes", "yes");
        dbInfo.setJdbcDriver("org.postgresql.Driver");
        dbInfo.setUrl("jdbc:postgresql://localhost:5432/data");
        dbInfo.setUsername("trinity");
        dbInfo.setPasswd("trinity");
        dataWriter = DataWriters.genericJdbcDataWriter(dbInfo).bulid();
    }

    public static void main(String[] args) throws IOException, ParseException {
        DataKeeper dataKeeper = new DataKeeper();
        httpRequest.post(pttIndexUrl, postData);
        dataKeeper.start();
    }

    private void start() throws IOException, ParseException {
        dataWriter.createStorage(PttBean.class); // 建立資料表
        CrawlableUrlFilter urlFilter = new CrawlableUrlFilter();
        urlFilter.startCrawl();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        urlFilter.getUrlList().forEach(url ->{
            try {
                HttpResponse resp = httpRequest.get(buildPttUrl(url));
                List<PttBean> filledBeans = fillBeanListByPage(url ,resp.getBody());
                dataWriter.writeData(filledBeans);
            } catch (IOException | NoSuchAlgorithmException | ParseException e) {
                e.printStackTrace();
            }
        });



        System.out.println("update success !");
    }

    private List<PttBean> fillBeanListByPage(String url, String body) throws NoSuchAlgorithmException, ParseException {
        List<PttBean> result = new ArrayList<>();
        PttBean essay = extractEssayBean(url, body); // 萃取文章內容
        List<PttBean> respBeans = extractRespBeans(url, body); // 萃取針對文章的回應
        result.add(essay);
        result.addAll(respBeans);
        return result;
    }

    private List<PttBean> extractRespBeans(String url, String body) throws NoSuchAlgorithmException, ParseException {
        List<PttBean> result = new ArrayList<>();
        htmlParser.setHtml(body);

        htmlParser.setCssSelector(".article-meta-value");
        List<ParseResult> metaline = htmlParser.doParse();
        String title = metaline.get(2).getText();
        String parentPostTime = metaline.get(3).getText();

        htmlParser.setCssSelector(".push");
        List<ParseResult> respList = htmlParser.doParse();
        for(int i = 0; i < respList.size(); ++i){
            PttBean bean = new PttBean();
            bean.setId(getMD5Url(url + "_" + i+1));
            bean.setRid(getMD5Url(url+"_0"));
            bean.setPid(getMD5Url(url+"_0"));
            bean.setUrl(url);
            bean.setTitle(title);
            bean.setContent(respList.get(i).getChild(2).getText());
            bean.setAuthor(respList.get(i).getChild(1).getText());
            bean.setPostDate(new Timestamp(System.currentTimeMillis()));
            String respTime = respList.get(i).getChild(3).getText();
            bean.setPostDate(generateRespTime(parentPostTime, respTime));
            bean.setUpdateDate(null);
            bean.setReplycnt(null);
            bean.setUpdatecnt(null);
            switch (respList.get(i).getChild(0).getText()){
                case "→":
                    bean.setArrow(1);
                    break;
                case "推":
                    bean.setPush(1);
                    break;
                case "噓":
                    bean.setShush(1);
                    break;
            }
            bean.setLink(null);
            bean.setLang("zh_TW");
            result.add(bean);
        }
        return result;
    }

    private PttBean extractEssayBean(String url, String body) throws NoSuchAlgorithmException, ParseException {
        PttBean bean = new PttBean();
        bean.setId(getMD5Url(url+"_0"));
        bean.setSite(null);
        bean.setRid(bean.getId());
        bean.setPid(null);
        bean.setUrl(url);
        htmlParser.setHtml(body);
        htmlParser.setCssSelector(".article-meta-value");
        List<ParseResult> metaline = htmlParser.doParse();
        bean.setTitle(metaline.get(2).getText());
        bean.setAuthor(metaline.get(0).getText());
        bean.setPostDate(new Timestamp(sdf.parse(metaline.get(3).getText()).getTime()));
        bean.setUpdateDate(null);
        bean.setReplycnt(null);
        bean.setUpdatecnt(null);
        bean.setPush(0);
        bean.setShush(0);
        bean.setArrow(0);
        bean.setLink(null);
        bean.setLang("zh_TW");
        htmlParser.setCssSelector("#main-content");
        List<ParseResult> results = htmlParser.doParse();
        bean.setContent(results.get(0).getPureText());
        return bean;
    }

    private static String getMD5Url(String url) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(url.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    private Timestamp generateRespTime(String parentPostTime, String respTime) throws ParseException {
        String[] timeinfo = respTime.split(" ");
        String yearinfo = parentPostTime.substring(parentPostTime.lastIndexOf(" "));
        String time = yearinfo + " " + timeinfo[timeinfo.length - 2] + " " + timeinfo[timeinfo.length - 1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM/dd HH:mm");
        return new Timestamp(sdf.parse(time).getTime());
    }

    private String buildPttUrl(String suffix){
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.ptt.cc/");
        sb.append(suffix);
        return sb.toString();
    }
}
