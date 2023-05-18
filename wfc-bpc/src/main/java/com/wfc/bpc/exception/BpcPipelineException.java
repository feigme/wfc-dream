package com.wfc.bpc.exception;

/**
 * @author hui.guo
 * @since 2022/7/1 5:27 下午
 */
public class BpcPipelineException extends BpcException {
    public BpcPipelineException() {
        super();
    }

    public BpcPipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpcPipelineException(String message) {
        super(message);
    }

    public BpcPipelineException(Throwable cause) {
        super(cause);
    }
}
