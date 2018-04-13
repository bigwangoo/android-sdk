package com.demo.sample.model;

/**
 * eventbus
 * <p>
 * Created by wyd on 2017/5/4.
 */
public class MessageEvent {

    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}