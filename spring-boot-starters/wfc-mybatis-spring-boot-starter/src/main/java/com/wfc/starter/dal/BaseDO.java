package com.wfc.starter.dal;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 飞影
 */
@Getter
@Setter
public abstract class BaseDO {

    protected Long id;
    protected Date gmtCreate;
    protected Date gmtModified;
}
