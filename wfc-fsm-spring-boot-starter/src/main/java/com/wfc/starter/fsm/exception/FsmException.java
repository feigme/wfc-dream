package com.wfc.starter.fsm.exception;

/**
 * @author hui.guo
 * @since 2022/7/7 12:12 上午
 */
public class FsmException extends RuntimeException {
    public FsmException() {
        super();
    }

    public FsmException(String message, Throwable cause) {
        super(message, cause);
    }

    public FsmException(String message) {
        super(message);
    }

    public FsmException(Throwable cause) {
        super(cause);
    }
}
