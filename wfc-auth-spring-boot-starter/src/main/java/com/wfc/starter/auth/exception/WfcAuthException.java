package com.wfc.starter.auth.exception;

/**
 * @author 飞影
 * @create by 2020-09-07 21:20
 */
public class WfcAuthException extends RuntimeException {

    public WfcAuthException(String message) {
        super(message);
    }

    public WfcAuthException(String messsage, Throwable t) {
        super(messsage, t);
    }

}
