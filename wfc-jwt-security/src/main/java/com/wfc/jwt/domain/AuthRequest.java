package com.wfc.jwt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author hui.guo
 * @since 2023/7/6 10:00 上午
 */
@Getter
@Setter
public class AuthRequest {

    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;

}
