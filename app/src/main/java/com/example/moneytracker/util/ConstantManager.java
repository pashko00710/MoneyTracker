package com.example.moneytracker.util;


final public class ConstantManager {

    public static final String STATUS_EMPTY = "";
    public static final String STATUS_UNAUTHORIZED = "unauthorized";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_LOGIN_ALREADY = "Login busy already";

    public static final String WRONG_PASSWORD = "Wrong password";

    public static final String WRONG_LOGIN = "Wrong login";

    public static final String TOKEN_KEY = "token_key";

    public static final String GOOGLE_TOKEN_KEY = "google_token_key";

    public final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    public final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    public final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;
}
