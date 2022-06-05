package com.wfc.starter.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 飞影
 * @create by 2020-10-27 22:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface WfcEventHandler {

    /**
     * 默认异步
     *
     * @return
     */
    boolean async() default true;

}
