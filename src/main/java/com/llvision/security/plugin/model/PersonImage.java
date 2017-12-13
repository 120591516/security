package com.llvision.security.plugin.model;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by llvision on 17/5/5.
 */
public class PersonImage {

    private String id;

    private String contentType;

    private byte[] data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
