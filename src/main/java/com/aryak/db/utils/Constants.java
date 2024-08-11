package com.aryak.db.utils;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Constants {

    public static final ThreadPoolExecutor dbInsertExecutor = new ThreadPoolExecutor(40, 40, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000));
    public static final String BULKSMS_HOST = "bulksms.host";
    public static final String TENANT_LOAD_URL = "tms.url";
}
