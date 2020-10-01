package com.wfc.starter.auth.service;

import com.wfc.starter.auth.dal.entity.WfcAccountDO;
import com.wfc.starter.auth.web.cmd.PwdRegisterCmd;

public interface AccountService {

    /**
     * 根据账号密码登陆
     *
     * @param loginName
     * @param password
     * @return
     */
    WfcAccountDO loginByPwd(String loginName, String password);

    /**
     * 根据手机号码登陆
     *
     * @param phoneNum
     * @param verifyCode
     * @return
     */
    WfcAccountDO loginByPhone(String phoneNum, String verifyCode);

    /**
     * 注册账号密码
     *
     * @param cmd
     * @return
     */
    WfcAccountDO registerByPwd(PwdRegisterCmd cmd);

}
