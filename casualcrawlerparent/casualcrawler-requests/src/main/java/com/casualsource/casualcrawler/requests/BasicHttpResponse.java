package com.casualsource.casualcrawler.requests;

import java.util.List;

public class BasicHttpResponse implements HttpResponse {
    private int statusCode;
    private List<Header> responseHeader;
    private String body;

    BasicHttpResponse(){}


    @Override
    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setResponseHeaders(List<Header> headers) {
        this.responseHeader = headers;
    }

    @Override
    public List<Header> getResponseHeaders() {
        return this.responseHeader;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public void setBody(String context) {
        this.body = context;
    }
}
