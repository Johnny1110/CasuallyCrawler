package com.casualsource.casualcrawler.requests;

import java.util.List;

public interface HttpResponse {
    public void setStatusCode(int code);

    public int getStatusCode();

    public void setResponseHeaders(List<Header> headers);

    public List<Header> getResponseHeaders();

    public String getBody();

    public void setBody(String context);
}
