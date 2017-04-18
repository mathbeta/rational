package com.mathbeta.rational.common.entity;

import java.io.Serializable;

/**
 * Created by 147458 on 2017/4/13.
 */
public class Message implements Serializable {
    private String message;
    private boolean result;
    private Object content;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static Message build(String message, boolean result, Object content) {
        Message msg = new Message();
        msg.setMessage(message);
        msg.setResult(result);
        msg.setContent(content);
        return msg;
    }
}
