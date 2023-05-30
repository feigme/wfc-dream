package com.wfc.bpc.builder.model;

import lombok.Data;

/**
 * @author hui.guo
 * @since 2023/5/29 11:55 下午
 */
@Data
public class BpcModel {

    protected String name;

    protected BpcModel next;

    protected BpcModel prev;

    public BpcModel() {

    }

    public BpcModel(String name) {
        this.name = name;
    }

}
