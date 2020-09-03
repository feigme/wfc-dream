package com.wfc.starter.dal.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 飞影
 * @create by 2019-08-30 10:50
 */
@Data
@TableName("user")
public class DemoUserDO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
