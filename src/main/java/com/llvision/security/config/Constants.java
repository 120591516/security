package com.llvision.security.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final int RECOGNITION_TYPE_CAR_PLATE = 1;
    public static final int RECOGNITION_TYPE_FACE = 2;

    private Constants() {
    }
}
