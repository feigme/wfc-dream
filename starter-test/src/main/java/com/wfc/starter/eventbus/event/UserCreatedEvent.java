package com.wfc.starter.eventbus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 飞影
 * @create by 2020-10-27 22:13
 */
@Getter
@AllArgsConstructor
public class UserCreatedEvent implements WfcEvent{

    private Long id;
    private String name;

}
