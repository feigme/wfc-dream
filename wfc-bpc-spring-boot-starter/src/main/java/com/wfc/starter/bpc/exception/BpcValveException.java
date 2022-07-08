package com.wfc.starter.bpc.exception;

/**
 * @author hui.guo
 * @since 2022/7/1 5:27 下午
 */
public class BpcValveException extends BpcException {
    public BpcValveException() {
        super();
    }

    public BpcValveException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpcValveException(String message) {
        super(message);
    }

    public BpcValveException(Throwable cause) {
        super(cause);
    }
}
