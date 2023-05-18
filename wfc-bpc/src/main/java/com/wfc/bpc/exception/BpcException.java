package com.wfc.bpc.exception;

/**
 * @author hui.guo
 * @since 2022/7/7 12:12 上午
 */
public class BpcException extends RuntimeException {
    public BpcException() {
        super();
    }

    public BpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpcException(String message) {
        super(message);
    }

    public BpcException(Throwable cause) {
        super(cause);
    }
}
