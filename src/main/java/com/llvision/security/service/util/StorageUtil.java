package com.llvision.security.service.util;


public class StorageUtil {
    public static String getContentType(String path){
        String[] splits = path.split("\\.");
        String suffix = splits[splits.length - 1];
        if ("jpg".equals(suffix)) {
            return "image/jpeg";
        }
        if ("jpeg".equals(suffix)) {
            return "image/jpeg";
        }
        if ("img".equals(suffix)) {
            return "application/x-img";
        }
        if ("ico".equals(suffix)) {
            return "application/x-ico";
        }
        if ("png".equals(suffix)) {
            return "image/png";
        }
        return "application/octet-stream";
    }
}
