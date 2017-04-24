package com.mathbeta.rational.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 17-4-22.
 */
public class MapUtil {
    public static String serializeHashMap(Object... kvs) {
        return JSON.toJSONString(buildHashMap(kvs));
    }

    /**
     * <p>按key，value的顺序将kvs转化为一个map</p>
     * <p>如传入["result", true, "message", "start successfully"]将被转化为</p>
     * <p>{"result": true, "message": "start successfully"}</p>
     *
     * @param kvs，一系列key，value值，长度为偶数，否则最后一个值将不会被添加到map中
     * @return
     */
    public static Map<Object, Object> buildHashMap(Object... kvs) {
        Map<Object, Object> message = new HashMap<Object, Object>();
        if (kvs != null && kvs.length > 0) {
            for (int i = 0; i < kvs.length - 1; i += 2) {
                message.put(kvs[i], kvs[i + 1]);
            }
        }
        return message;
    }
}
