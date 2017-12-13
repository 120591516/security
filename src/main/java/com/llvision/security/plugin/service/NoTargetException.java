package com.llvision.security.plugin.service;

/**
 * Created by cxj on 2017/5/22.
 */
public class NoTargetException extends Exception {

    public NoTargetException() {
        super();
    }

    public NoTargetException(String message) {
        super(message);
    }

    public NoTargetException(String message, Exception e) {
        super(message, e);
    }
}
