package com.wfc.starter.web.controller.user;

import com.wfc.starter.auth.RestResult;
import com.wfc.starter.service.UserService;
import com.wfc.starter.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 飞影
 * @create by 2020-11-28 22:41
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/info")
    public RestResult userInfo() {
        Long userId = this.getUserIdFromReq();
        return RestResult.success(userService.getUserInfo(userId));
    }

}
