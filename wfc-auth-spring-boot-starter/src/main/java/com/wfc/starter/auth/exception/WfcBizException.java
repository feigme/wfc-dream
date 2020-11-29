package com.wfc.starter.auth.exception;

/**
 * @author 飞影
 * @create by 2020-09-07 21:20
 */
public class WfcBizException extends RuntimeException {

    public WfcBizException(String message) {
        super(message);
    }

    public WfcBizException(String messsage, Throwable t) {
        super(messsage, t);
    }

}
