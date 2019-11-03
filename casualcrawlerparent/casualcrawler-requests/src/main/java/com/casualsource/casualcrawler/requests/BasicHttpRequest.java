package com.casualsource.casualcrawler.requests;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.*;

public class BasicHttpRequest implements HttpRequest {

    private List<org.apache.http.Header> headers = HttpClientUtil.generateDefaultApacheHeaders();
    private BasicCookieStore cookieStore = new BasicCookieStore();

    BasicHttpRequest(){}

    @Override
    public HttpResponse post(String url, Map<String, String> param) throws IOException {
        List<NameValuePair> postData = HttpClientUtil.MapToNameValuePairList(param);
        try(CloseableHttpClient client = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultCookieStore(cookieStore)
                .setDefaultCookieSpecRegistry(CookieSpecRegistries.createDefault())
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build()){

            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(postData));
            HttpResponse resp = client.execute(post, responseHandler);
            return resp;
        }
    }

    @Override
    public HttpResponse get(String url) throws IOException {
        try(CloseableHttpClient client = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultCookieStore(cookieStore)
                .setDefaultCookieSpecRegistry(CookieSpecRegistries.createDefault())
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build()){
            HttpGet get = new HttpGet(url);
            HttpResponse resp = client.execute(get, responseHandler);
            return resp;
        }
    }

    @Override
    public void setHeaders(List<Header> headers) {
        this.headers = HttpClientUtil.customizedHeaderToApacheHeader(headers);
    }

    @Override
    public void addHeader(Header header) {
        org.apache.http.Header apacheHeader = new BasicHeader(header.getName(), header.getValue());
        if (!this.headers.contains(apacheHeader)) {
            this.headers.add(apacheHeader);
        } else {
            throw new HeaderAlreadyExistedException(header);
        }
    }


    @Override
    public List<Header> getHeaders() {
        return HttpClientUtil.apacheHeadersToCustomizedHeaders(this.headers);
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.cookieStore.addCookie(HttpClientUtil.customizedCookieToApacheCookie(cookie));
    }

    @Override
    public List<Cookie> getCookies() {
        return HttpClientUtil.apacheCookiesToCustomizedCookies(this.cookieStore);
    }

    @Override
    public void cleanCookies() {
        this.cookieStore.clear();
    }

    private ResponseHandler<HttpResponse> responseHandler = apacheResponse -> {
        HttpResponse response = HttpClientUtil.apacheResponseToCustomizedResponse(apacheResponse);
        return response;
    };
}
