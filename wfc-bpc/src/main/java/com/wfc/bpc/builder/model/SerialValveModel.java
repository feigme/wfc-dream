package com.wfc.bpc.builder.model;

import com.wfc.bpc.core.BpcValve;
import lombok.Data;

/**
 * 串行 valve模型
 *
 * @author hui.guo
 * @since 2023/5/29 11:54 下午
 */
@Data
public class SerialValveModel extends BpcModel {

    private BpcModel last;

    private BpcValve valve;

    public SerialValveModel(String name) {
        super(name);
    }

    public SerialValveModel(String name, BpcValve valve) {
        super(name);
        this.valve = valve;
    }
}
