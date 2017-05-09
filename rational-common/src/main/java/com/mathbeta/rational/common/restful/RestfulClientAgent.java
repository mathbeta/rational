package com.mathbeta.rational.common.restful;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/25.
 */
public class RestfulClientAgent {
    private Client client;
    private static RestfulClientAgent agent;

    private RestfulClientAgent() {
        client = ClientBuilder.newClient();
    }

    public static RestfulClientAgent getAgent() {
        if (agent == null) {
            synchronized (RestfulClientAgent.class) {
                if (agent == null) {
                    agent = new RestfulClientAgent();
                }
            }
        }
        return agent;
    }

    /**
     * 发送get或delete请求
     *
     * @param method  get或delete
     * @param url     请求地址，string数组，可以只传入一个完整的url，也可以传入[ip, port, path]组合或其他形式，该数组所有元素会被连接到一起构成最终的url
     * @param headers 请求头
     * @param params  请求参数，以键值对的方式排列，值可以是一个数组，如果长度为奇数，则最后一个key将被丢弃
     * @return
     */
    public Object getOrDelete(String method, String[] url, Map<Object, Object> headers, Object... params) {
        WebTarget target = client.target(buildUrl(url));
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length - 1; i += 2) {
                target.queryParam((String) params[i], params[i + 1]);
            }
        }
        Invocation.Builder builder = target.request();
        wrapHeaders(builder, headers);

        Response response = null;
        if ("get".equalsIgnoreCase(method)) {
            response = builder.get();
        } else {
            response = builder.delete();
        }
        return response.getEntity();
    }

    private String buildUrl(String[] url) {
        if (url != null && url.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String u : url) {
                sb.append(u);
            }
            return sb.toString();
        }
        return "";
    }

    private void wrapHeaders(Invocation.Builder builder, Map<Object, Object> headers) {
        if (headers != null && !headers.isEmpty()) {
            Iterator<Object> it = headers.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                builder.header(key.toString(), headers.get(key));
            }
        }
    }

    /**
     * 发送post或put请求
     *
     * @param method
     * @param url     string数组，可以只传入一个完整的url，也可以传入[ip, port, path]组合或其他形式，该数组所有元素会被连接到一起构成最终的url
     * @param headers
     * @param params
     * @return
     */
    public Object postOrPut(String method, String[] url, Map<Object, Object> headers, String... params) {
        WebTarget target = client.target(buildUrl(url));
        Form form = new Form();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length - 1; i += 2) {
                form.param(params[i], params[i + 1]);
            }
        }
        Invocation.Builder builder = target.request();
        wrapHeaders(builder, headers);
        Response response = null;
        if ("post".equalsIgnoreCase(method)) {
            response = builder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        } else {
            response = builder.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        }
        return response.getEntity();
    }

    public Object postOrPut(String method, String[] url, Map<Object, Object> headers, Object body) {
        WebTarget target = client.target(buildUrl(url));
        Invocation.Builder builder = target.request();
        wrapHeaders(builder, headers);
        Response response = null;
        if ("post".equalsIgnoreCase(method)) {
            response = builder.post(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED));
        } else {
            response = builder.put(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED));
        }
        return response.getEntity();
    }
}
