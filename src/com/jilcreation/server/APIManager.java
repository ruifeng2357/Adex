package com.jilcreation.server;

public class APIManager {
	// Service Address
    public static String API_PREFIX = "http://jilcreation.com/phpdb132";

    // Connection Info
    public static int connectTimeout = 4 * 1000; // 5 Seconds

    // Error Code
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_FAIL = 500;

    // Command List
    public static String CMD_GETDEALS = API_PREFIX + "/selectservice.php";
}
