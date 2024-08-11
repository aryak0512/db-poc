package com.aryak.db.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;

@Component
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        load();
    }

    private void load() {
        try {
            properties.load(new FileInputStream("/Users/aryak/Desktop/db-test/config/bulksms.properties"));
        } catch (Exception e) {
            log.error("Exception during loading of properties :", e);
        }
    }

    public void reloadProps(){
        load();
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

}
