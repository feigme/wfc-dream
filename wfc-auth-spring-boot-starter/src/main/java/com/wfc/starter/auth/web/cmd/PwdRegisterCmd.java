package com.wfc.starter.auth.web.cmd;

import lombok.Data;

/**
 * @author 飞影
 * @create by 2020-10-01 20:57
 */
@Data
public class PwdRegisterCmd {

    private String loginName;
    private String password;
    private String confirmPassword;

}
