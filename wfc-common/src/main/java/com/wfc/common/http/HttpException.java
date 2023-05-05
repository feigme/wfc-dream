package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/5/5 2:17 下午
 */
public class HttpException extends RuntimeException {

    public HttpException() {
        super();
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

}
