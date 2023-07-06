package com.wfc.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hui.guo
 * @since 2023/7/6 10:11 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    private String accessToken;
    private Long expiresIn;

}
