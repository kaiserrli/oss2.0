package com.ebupt.oss.domain;

/**
 * Created by 凯 on 2016/11/25.
 */
public class UploadBean {
    private String url;
    private String uuid;

    public UploadBean() {
        super();
    }

    public UploadBean(String url, String uuid) {
        super();
        this.url = url;
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
