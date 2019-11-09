package com.casualsource.casualcrawler.pttcrawler;

import com.casualsource.casualcrawler.requests.HttpRequest;
import com.casualsource.casualcrawler.requests.HttpRequests;
import com.casualsource.casualcrawler.requests.HttpResponse;
import com.casualsource.casualcrawler.requests.HttpResponses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String postURL = "https://www.ptt.cc/ask/over18";
        Map<String, String> postData = new HashMap<>();
        postData.put("from", "/bbs/Gossiping/index.html");
        postData.put("yes", "yes");

        HttpRequest request = HttpRequests.createDefault();
        HttpResponse response = HttpResponses.createDefault();
        response = request.post(postURL, postData);
        System.out.printf("HTTP Status-Code : %d \n", response.getStatusCode());
        System.out.println("<----------------------------------->");
        System.out.printf("response header info : %s \n",response.getResponseHeaders());
        System.out.println("<----------------------------------->");
        System.out.println(response.getBody());
    }
}
