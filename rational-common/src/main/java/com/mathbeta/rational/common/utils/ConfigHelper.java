package com.mathbeta.rational.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by 147458 on 2017/4/12.
 */
public class ConfigHelper {
    private static final Logger log = LoggerFactory.getLogger(ConfigHelper.class);
    private static Properties properties;
    private static Properties serverProperties;
    private static Properties kafkaProperties;
    private static String filePath;
    private static String jettyServerfilePath;
    private static String kafkafilePath;

    public ConfigHelper() {
    }

    public static Properties loadProperties(String fileName) {
        Properties prop = new Properties();

        try {
            File e = getFile(fileName, "configs/");
            if(e != null) {
                FileInputStream input = new FileInputStream(e);
                prop.load(input);
            }
        } catch (Exception var4) {
            log.error("Loading config.properties fails", var4);
        }

        return prop;
    }

    public static String getJettyParameter(String key) {
        return serverProperties.getProperty(key);
    }

    public static String getKafkaParameter(String key) {
        try {
            kafkafilePath = getFilePath("kafka.properties", "configs/");
            if(kafkafilePath != null && kafkaProperties == null) {
                kafkaProperties = loadProperties("kafka.properties");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return kafkaProperties.getProperty(key);
    }

    public static String getValue(String key) {
        String value = null;

        try {
            value = properties.getProperty(key);
        } catch (Exception var3) {
            log.error("key:" + key + " 资源参数加载失败！", var3);
        }

        return value;
    }

    public static String getFilePath(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if(propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        if(filePath != null && !"".equals(filePath)) {
            filePath = filePath.endsWith("/")?filePath.concat(fileName):filePath.concat("/").concat(fileName);
        } else {
            URL url = ConfigHelper.class.getClassLoader().getResource(propertyName + fileName);
            if(url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }

            filePath = url.getPath();
        }

        return filePath;
    }

    public static File getFile(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if(propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        File file = null;
        if(filePath != null && !"".equals(filePath)) {
            filePath = filePath.endsWith("/")?filePath.concat(fileName):filePath.concat("/").concat(fileName);
            file = new File(filePath);
        } else {
            URL url = ConfigHelper.class.getClassLoader().getResource(propertyName + fileName);
            if(url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }

            file = new File(url.getPath());
        }

        return file;
    }

    static {
        System.out.println("[" + DateUtil.getDateTime() + "] Loading config.properties");

        try {
            filePath = getFilePath("config.properties", "configs/");
            if(filePath != null) {
                properties = loadProperties("config.properties");
            }

            jettyServerfilePath = getFilePath("jettyServer.properties", "configs/");
            if(jettyServerfilePath != null) {
                serverProperties = loadProperties("jettyServer.properties");
            }
        } catch (Exception var1) {
            ;
        }

    }
}