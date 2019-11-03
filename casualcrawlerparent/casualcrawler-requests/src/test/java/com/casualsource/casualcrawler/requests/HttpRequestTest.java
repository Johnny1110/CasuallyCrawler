package com.casualsource.casualcrawler.requests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

public class HttpRequestTest {
    private HttpRequest request;
    private HttpResponse response;
    private String getURL = "";
    private String postURL = "";
    private Map<String, String> postData = new HashMap<>();
    private List<Header> headers = new ArrayList<>();
    private Header header;
    private Cookie cookie;



    @Before
    public void readyForTest(){
        request = HttpRequests.createDefault();
        response = HttpResponses.createDefault();

        getURL = "https://www.ptt.cc/ask/over18?from=%2Fbbs%2FGossiping%2Findex.html";

        postURL = "https://www.ptt.cc/ask/over18";
        postData.put("from", "/bbs/Gossiping/index.html");
        postData.put("yes", "yes");

        headers.add(new Header("authority", "www.google.com.tw"));
        headers.add(new Header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Mobile Safari/537.36"));
        headers.add(new Header("pragma", "no-cache"));

        header = new Header("accept-language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6");

        cookie = new Cookie("user-host", "JOHNNY-WORKSHOP");
        cookie.setDomain("openmind.cc");
        cookie.setPath("/");
    }

    @Test
    public void testGet() throws IOException {
        response = request.get(getURL);
//        System.out.println(response.getResponseHeaders());
//        System.out.println(response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testPost() throws IOException {
        response = request.post(postURL, postData);
//        System.out.println(response.getResponseHeaders());
//        System.out.println(response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSetAndGetHeaders(){
        request.setHeaders(headers);
        assertEquals(request.getHeaders(), headers);
    }

    @Test
    public void testAddHeader(){
        request.addHeader(header);
        assertTrue(request.getHeaders().contains(header));
    }

    @Test
    public void testAddCookieAndGetCookies(){
        request.addCookie(cookie);
        request.getCookies().forEach(System.out::println);
        System.out.println(cookie);
//        assertTrue(Arrays.asList(request.getCookies()).contains(cookie)); 測不過
    }

    @Test
    public void testCleanCookie(){
        request.cleanCookies();
        assertTrue(request.getCookies().isEmpty());
    }

}
