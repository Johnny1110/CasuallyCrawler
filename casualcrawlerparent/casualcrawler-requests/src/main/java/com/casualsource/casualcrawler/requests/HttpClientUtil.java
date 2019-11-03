package com.casualsource.casualcrawler.requests;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    static List<NameValuePair> MapToNameValuePairList(Map<String, String> param) {
        List<NameValuePair> result = new ArrayList<>();
        param.forEach((k, v) ->{
            result.add(new BasicNameValuePair(k, v));
        });
        return result;
    }

    public static List<org.apache.http.Header> generateDefaultApacheHeaders() {
        List<org.apache.http.Header>  headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36"));
        headers.add(new BasicHeader(HttpHeaders.REFERER,"https://www.google.com/"));
        headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL,"max-age=0"));
        return headers;
    }

    public static HttpResponse apacheResponseToCustomizedResponse(org.apache.http.HttpResponse apacheResponse) throws IOException {
        HttpResponse response = HttpResponses.createDefault();
        List<Header> headers = new ArrayList<>();
        Arrays.asList(apacheResponse.getAllHeaders()).forEach(apacheHeader -> {
            headers.add(new Header(apacheHeader.getName(), apacheHeader.getValue()));
        });
        response.setStatusCode(apacheResponse.getStatusLine().getStatusCode());
        response.setBody(EntityUtils.toString(apacheResponse.getEntity(), "UTF-8"));
        response.setResponseHeaders(headers);
        return response;
    }

    public static List<org.apache.http.Header> customizedHeaderToApacheHeader(List<Header> customizedHeaders) {
        List<org.apache.http.Header> headers = new ArrayList<>();
        customizedHeaders.forEach(customizedHeader -> {
            headers.add(new BasicHeader(customizedHeader.getName(), customizedHeader.getValue()));
        });
        return headers;
    }

    public static List<Header> apacheHeadersToCustomizedHeaders(List<org.apache.http.Header> apacheHeaders) {
        List<Header> headers = new ArrayList<>();
        apacheHeaders.forEach(apacheHeader ->{
            headers.add(new Header(apacheHeader.getName(), apacheHeader.getValue()));
        });
        return headers;
    }

    public static org.apache.http.cookie.Cookie customizedCookieToApacheCookie(Cookie customizedCookie) {
        BasicClientCookie cookie = new BasicClientCookie(customizedCookie.getName(), customizedCookie.getValue());
        if(!customizedCookie.getPath().equals("") && customizedCookie.getPath() != null){
            cookie.setPath(customizedCookie.getPath());
        }
        if(!customizedCookie.getDomain().equals("") && customizedCookie.getDomain() != null){
            cookie.setDomain(customizedCookie.getDomain());
        }
        return cookie;
    }

    public static List<Cookie> apacheCookiesToCustomizedCookies(BasicCookieStore cookieStore) {
        List<Cookie> cookies = new ArrayList<>();
        cookieStore.getCookies().forEach(apacheCookie -> {
            cookies.add(new Cookie(apacheCookie.getName(),apacheCookie.getValue(), apacheCookie.getDomain(), apacheCookie.getPath()));
        });
        return cookies;
    }
}
