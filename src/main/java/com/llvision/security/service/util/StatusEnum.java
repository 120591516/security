package com.llvision.security.service.util;

/**
 * Created by cxj on 2017/5/22.
 */
public enum StatusEnum {

    COLLECT_ONLY(0,"只采集"), //null
    SUCCESS(1,"识别成功"),
    NO_MATCH(-1,"无对比结果"); //result.isEmpty()

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
