package com.spring.app.Security;

/** 
 * @author Saloua LAHJOUJI
 * @date 30/12/2020 
 */

public class Constants {

    public final static String SUCCESS = "Success";
    public final static String UNIQUE_ID_ALREADY_EXIST = "Unique id already exist";
    public final static String USER_NOT_FOUND = "User not found";
    public final static String DEVICE_NOT_FOUND = "User not found";
    public final static String INTERNAL_SERVER_ERROR = "Internal server error";
    public final static String EXCEPTION_FAILED = "Exception failed";
    public final static String NOT_FOUND = "Not Found";
    public final static String FORBIDDEN = "Forbidden";
    public final static String USER_PROFILE_NOT_UPDATED = "User profile not updated";

    public static final String SECRET = "atypikhouse";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
}

