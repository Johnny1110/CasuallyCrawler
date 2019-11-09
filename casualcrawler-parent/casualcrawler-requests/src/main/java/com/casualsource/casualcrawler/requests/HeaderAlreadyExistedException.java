package com.casualsource.casualcrawler.requests;

public class HeaderAlreadyExistedException extends RuntimeException {
    public HeaderAlreadyExistedException(Header header){
        super(String.format("header: %s already existed. if you want to add this header, please reset the header.", header));
        this.printStackTrace();
    }
}
