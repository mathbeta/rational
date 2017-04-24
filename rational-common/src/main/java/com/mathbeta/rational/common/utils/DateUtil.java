package com.mathbeta.rational.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 147458 on 2017/4/12.
 */
public class DateUtil {
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }
}
