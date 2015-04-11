package com.jilcreation.server;

public class APIManager {
	// Service Address
    public static String API_PREFIX = "http://128.199.67.219/phpwebservices";

    // Connection Info
    public static int connectTimeout = 4 * 1000; // 5 Seconds

    // Error Code
    public static final int ERR_SUCCESS = 0;
    public static final int ERR_FAIL = 500;

    // Command List
    public static String CMD_GETDEALS = API_PREFIX + "/selectservice.php";
    public static String CMD_SELECTDEAL = API_PREFIX + "/selectdealsservice.php";
    public static String CMD_GETEXHIBITORS = API_PREFIX + "/selectexhibitorservice.php";
    public static String CMD_GETPROGRAMMES = API_PREFIX + "/selectscheduleservice.php";
    public static String CMD_ADDUSER = API_PREFIX + "/adduserservice.php?x=0";
    public static String CMD_UPDATEPREF = API_PREFIX + "/updateprefservice.php";
    public static String CMD_UPDATEDURATION = API_PREFIX + "/updatedurationservice.php";
    public static String CMD_SEARCHBY = API_PREFIX + "/searchbykeywordservice.php";
    public static String CMD_NOTIFICATION = API_PREFIX + "/selectnotificationservice.php";
}
