package com.wfc.starter.auth.constant;

/**
 * 账号可用
 *
 * @author guohui
 */
public enum AccountEnableEnum {

    /**
     * 可用
     */
    ENABLE(0, "可用"),
    /**
     * 不可用
     */
    DISABLE(1, "不可用"),
    ;

    private int code;
    private String desc;

    AccountEnableEnum(int code, String desc) {
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
