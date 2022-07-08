package com.wfc.starter.bpc.exception;

/**
 * @author hui.guo
 * @since 2022/7/1 5:27 下午
 */
public class BpcRollbackException extends BpcException {
    public BpcRollbackException() {
        super();
    }

    public BpcRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpcRollbackException(String message) {
        super(message);
    }

    public BpcRollbackException(Throwable cause) {
        super(cause);
    }
}
