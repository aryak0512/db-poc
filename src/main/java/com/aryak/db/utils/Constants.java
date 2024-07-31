package com.aryak.db.utils;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Constants {

    public static final ThreadPoolExecutor dbInsertExecutor = new ThreadPoolExecutor(40, 40, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000));

}
