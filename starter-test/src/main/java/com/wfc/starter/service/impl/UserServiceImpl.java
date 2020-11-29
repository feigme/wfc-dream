package com.wfc.starter.service.impl;

import com.wfc.starter.auth.dal.entity.WfcAccountDO;
import com.wfc.starter.auth.exception.WfcBizException;
import com.wfc.starter.auth.service.AccountService;
import com.wfc.starter.service.UserService;
import com.wfc.starter.service.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 飞影
 * @create by 2020-11-28 23:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserInfoVO getUserInfo(Long id) {
        if (id == null) {
            return null;
        }

        WfcAccountDO accountDO = accountService.getAccountById(id);
        if (accountDO == null) {
            throw new WfcBizException(String.format("未找到当前用户, userId: %s", id));
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(accountDO.getId());
        userInfoVO.setUserName(accountDO.getLoginName());
        return userInfoVO;
    }
}
