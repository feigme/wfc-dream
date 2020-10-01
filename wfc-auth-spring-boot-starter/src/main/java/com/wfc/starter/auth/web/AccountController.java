package com.wfc.starter.auth.web;

import com.wfc.starter.auth.RestResult;
import com.wfc.starter.auth.dal.entity.WfcAccountDO;
import com.wfc.starter.auth.service.AccountService;
import com.wfc.starter.auth.web.cmd.PwdRegisterCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 飞影
 * @create by 2020-09-29 21:05
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public RestResult login(String loginName, String password) {
        WfcAccountDO accountDO = accountService.loginByPwd(loginName, password);
        return RestResult.success(accountDO);
    }

    @PostMapping("/logout")
    public RestResult logout() {
        return RestResult.success();
    }

    @PostMapping("/register")
    public RestResult register(PwdRegisterCmd pwdRegisterCmd) {
        WfcAccountDO accountDO = accountService.registerByPwd(pwdRegisterCmd);
        return RestResult.success(accountDO);
    }

}
