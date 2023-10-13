package com.wfc.jwt.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;

/**
 * @author hui.guo
 * @since 2023/7/6 10:16 上午
 */
@Getter
@Setter
@Validated
public class JwtProperties {

    private static final Long DEFAULT_JWT_TOKEN_EXPIRES = 604800L;
    private static final String DEFAULT_BASE_PATH = "/api/**";
    private static final String DEFAULT_CREATE_AUTH_TOKEN_PATH = "/api/auth/login";
    private static final String DEFAULT_REFRESH_AUTH_TOKEN_PATH = "/api/auth/refresh";
    private static final String DEFAULT_AUTH_ME_PATH = "/api/auth/me";


    @NotEmpty(message = "issuer不能为空")
    private String issuer = "";

    @NotEmpty(message = "header不能为空")
    private String header = "Authorization";

    @NotNull(message = "expiresIn不能为空")
    @Positive(message = "expiresIn必须大于0")
    private Long expiresIn = DEFAULT_JWT_TOKEN_EXPIRES;

    @NotEmpty(message = "secret不能为空")
    private String secret = "";

    @NotEmpty(message = "basePath不能为空")
    private String basePath = DEFAULT_BASE_PATH;
    private List<String> permitAllPaths = Arrays.asList(
            DEFAULT_CREATE_AUTH_TOKEN_PATH,
            DEFAULT_REFRESH_AUTH_TOKEN_PATH
    );
    private boolean authApiEnabled = true;
    private String createAuthTokenPath = DEFAULT_CREATE_AUTH_TOKEN_PATH;
    private String refreshAuthTokenPath = DEFAULT_REFRESH_AUTH_TOKEN_PATH;
    private String authMePath = DEFAULT_AUTH_ME_PATH;
}
