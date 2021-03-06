package com.qql.dagger.recommend.model.bean;

/**
 * Created by qql on 2017/3/30.
 */

public class CategoryBean {
    private long id;
    private String url;
    private String title;

    public CategoryBean(String title,String url) {
        this.url = url;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
