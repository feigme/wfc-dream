package com.wfc.bpc.builder.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hui.guo
 * @since 2023/5/29 11:55 下午
 */
@Data
public class ParallelValveModel extends BpcModel {

    List<BpcDoublyLinkedModel> valveList = new ArrayList<>();

    public ParallelValveModel() {

    }

    public ParallelValveModel(String name) {
        super(name);
    }
}
