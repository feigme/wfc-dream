package com.wfc.show.service;

import com.wfc.show.service.vo.UserInfoVO;

/**
 * 用户信息
 *
 * @author guohui
 */
public interface UserService {

    /**
     * 返回用户信息数据
     *
     * @param id
     * @return
     */
    UserInfoVO getUserInfo(Long id);

}
