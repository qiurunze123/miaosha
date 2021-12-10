package com.geekq.common.utils;

public class DBContextUtil {

    public static final String DBMASTER = "dbmaster";
    public static final String DBREAD = "dbread";
    private static ThreadLocal<String> dbPools = new ThreadLocal<>();

    public static String getDB() {
        return dbPools.get();
    }

    public static void setDB(String db) {
        dbPools.set(db);
    }

}
