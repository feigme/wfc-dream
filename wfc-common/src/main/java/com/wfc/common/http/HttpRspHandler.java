package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/4/26 5:23 下午
 */
public interface HttpRspHandler {

    /**
     * 处理返回的字符串
     *
     * @param rspStr
     */
    void handle(String rspStr);

}
