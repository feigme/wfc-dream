package com.wfc.starter.auth.constant;

public enum AccountEnableEnum {

    ENABLE(0, "可用"),
    DISABLE(1, "不可用"),
    ;

    private int code;
    private String desc;

    private AccountEnableEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
