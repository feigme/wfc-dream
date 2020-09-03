package com.wfc.starter.web.controller.demo;

import com.wfc.starter.dal.demo.entity.DemoUserDO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 飞影
 * @create by 2020-04-18 18:03
 */
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoUserController {

    @ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息")
    @RequestMapping("/user")
    @ResponseBody
    public DemoUserDO user() {
        DemoUserDO demoUserDO = new DemoUserDO();
        demoUserDO.setName("aaa");
        return demoUserDO;
    }

}
