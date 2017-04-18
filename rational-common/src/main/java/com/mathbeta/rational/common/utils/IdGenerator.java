package com.mathbeta.rational.common.utils;

import java.util.UUID;

/**
 * Created by Administrator on 17-4-15.
 */
public class IdGenerator {
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("\\-", "");
    }
}
