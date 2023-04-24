package com.wfc.starter.dal;

import lombok.Data;

import java.util.Date;

/**
 * @author 飞影
 * @create by 2020-09-29 20:19
 */
@Data
public class BaseDO {

    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
}
