package com.wfc.starter.auth.web.cmd;

import lombok.Data;

/**
 * @author 飞影
 * @create by 2020-11-28 16:18
 */
@Data
public class PwdLoginCmd {

    private String loginName;
    private String password;

}
