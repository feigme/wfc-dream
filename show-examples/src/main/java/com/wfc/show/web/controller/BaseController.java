package com.wfc.show.web.controller;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author 飞影
 * @create by 2020-11-28 22:56
 */
public abstract class BaseController {

    protected Long getUserIdFromReq() {
        return (Long) RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
    }

    protected String getUserNameFromReq() {
        return (String) RequestContextHolder.currentRequestAttributes().getAttribute("userName", RequestAttributes.SCOPE_REQUEST);
    }
}
