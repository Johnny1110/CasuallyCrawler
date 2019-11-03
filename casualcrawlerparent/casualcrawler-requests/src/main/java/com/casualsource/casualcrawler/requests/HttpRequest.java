package com.casualsource.casualcrawler.requests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HttpRequest {
    public HttpResponse post(String url, Map<String, String> param) throws IOException;

    public HttpResponse get(String url) throws IOException;

    public void setHeaders(List<Header> headers);

    public void addHeader(Header header);

    public List<Header> getHeaders();

    public void addCookie(Cookie cookie);

    public List<Cookie> getCookies();

    public void cleanCookies();
}
