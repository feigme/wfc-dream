package cn.wmall.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hui.guo
 * @since 2023/10/18 7:37 下午
 */
@RestController
@RequestMapping("/system")
public class VersionController {

    @GetMapping("/version")
    public String version(){
        return "0.0.1";
    }
}
