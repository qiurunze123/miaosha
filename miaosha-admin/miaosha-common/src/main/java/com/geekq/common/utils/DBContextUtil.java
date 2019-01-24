package com.geekq.common.utils;

public class DBContextUtil {

	private static ThreadLocal<String> dbPools = new ThreadLocal<>();

	public static final String DBMASTER = "dbmaster";
	public static final String DBREAD = "dbread";

	public static void setDB(String db) {
		dbPools.set(db);
	}

	public static String getDB() {
		return dbPools.get();
	}

}
