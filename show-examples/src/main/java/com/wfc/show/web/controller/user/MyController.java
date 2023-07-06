package com.wfc.show.web.controller.user;

import com.wfc.show.web.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hui.guo
 * @since 2023/7/6 11:59 上午
 */
@RestController
public class MyController extends BaseController {
    @GetMapping("/api/health")
    public String apiHealth() {
        return "UP";
    }

    @GetMapping("/public/status")
    public String publicStatus() {
        return "HelloWorld";
    }

    @GetMapping("/api/data")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getData() {
        return "Here is the data";
    }
}
