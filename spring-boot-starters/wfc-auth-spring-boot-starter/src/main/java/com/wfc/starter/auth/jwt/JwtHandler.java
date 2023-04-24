package com.wfc.starter.auth.jwt;

import com.wfc.starter.auth.autoconfigure.JwtProperties;
import com.wfc.starter.auth.exception.WfcAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 飞影
 * @create by 2020-04-12 00:18
 */
@Slf4j
@Component
public class JwtHandler {

    public static final String JWT_KEY = "jwt-%s";

    public static final String JWT_PREFIX = "Bearer ";

    @Autowired
    private JwtProperties jwtProperties;

    private Map<String, String> map = new HashMap<>();

    public String createJWT(Boolean rememberMe, Long id, String subject) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getKey());

        // 设置过期时间
        Long ttl = rememberMe ? jwtProperties.getRemember() : jwtProperties.getTtl();
        if (ttl > 0) {
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(ttl.intValue());
            builder.setExpiration(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存到缓存
        map.put(String.format(JWT_KEY, subject), jwt);
        return jwt;
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) throws WfcAuthException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String key = String.format(JWT_KEY, claims.getSubject());

            // 校验cache中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String cacheToken = map.get(key);
            if (!StringUtils.equals(jwt, cacheToken)) {
                throw new WfcAuthException("Token 过期");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new WfcAuthException("Token 已过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new WfcAuthException("不支持的 Token");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new WfcAuthException("Token 无效");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new WfcAuthException("无效的 Token 签名");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new WfcAuthException("Token 参数不存在");
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) throws WfcAuthException {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 清除JWT
        map.remove(String.format(JWT_KEY, username));
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) throws WfcAuthException {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 根据 jwt 信息
     *
     * @param jwt
     * @return
     * @throws WfcAuthException
     */
    public Claims getClaimsFromJWT(String jwt) throws WfcAuthException {
        return parseJWT(jwt);
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(JWT_PREFIX)) {
            return bearerToken.substring(JWT_PREFIX.length());
        }
        return null;
    }

}
