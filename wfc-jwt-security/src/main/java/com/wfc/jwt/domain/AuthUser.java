package com.wfc.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hui.guo
 * @since 2023/7/6 9:58 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    private String userName;
    private Set<String> roleSet = new HashSet<>();

}
