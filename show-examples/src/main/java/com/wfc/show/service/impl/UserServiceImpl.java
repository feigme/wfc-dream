package com.wfc.show.service.impl;


import com.wfc.show.service.UserService;
import com.wfc.show.service.vo.UserInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author 飞影
 * @create by 2020-11-28 23:08
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserInfoVO getUserInfo(Long id) {
        if (id == null) {
            return null;
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(1L);
        userInfoVO.setUserName("aaaaaa");
        return userInfoVO;
    }
}
