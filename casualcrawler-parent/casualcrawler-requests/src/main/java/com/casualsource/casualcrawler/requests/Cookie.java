package com.casualsource.casualcrawler.requests;

import java.util.Objects;

public class Cookie {

    private String name;
    private String value;
    private String domain = "";
    private String path = "";

    public Cookie(String name, String value){
        this.name = name;
        this.value = value;
    }

    public Cookie(String name, String value, String domain, String path){
        this(name, value);
        this.domain = domain;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString(){
        return String.format("[cookie-name: %s, cookie-value: %s, cookie-path: %s, cookie-domain: %s]"
                ,name, value, path, domain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return getName().equals(cookie.getName()) &&
                getValue().equals(cookie.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}
