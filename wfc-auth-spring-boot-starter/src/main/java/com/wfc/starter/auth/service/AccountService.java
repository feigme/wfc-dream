package com.wfc.starter.auth.service;

import com.wfc.starter.auth.dal.entity.WfcAccountDO;
import com.wfc.starter.auth.web.cmd.PwdLoginCmd;
import com.wfc.starter.auth.web.cmd.PwdRegisterCmd;

public interface AccountService {

    /**
     * 根据账号密码登陆
     *
     * @param pwdLoginCmd
     * @return
     */
    String loginByPwd(PwdLoginCmd pwdLoginCmd);

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
    String registerByPwd(PwdRegisterCmd cmd);

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    WfcAccountDO getAccountById(Long id);

}
