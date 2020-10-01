package com.wfc.starter.auth.filter;

import com.alibaba.fastjson.JSON;
import com.wfc.starter.auth.RestResult;
import com.wfc.starter.auth.autoconfigure.CustomAuthProperties;
import com.wfc.starter.auth.exception.WfcAuthException;
import com.wfc.starter.auth.jwt.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 飞影
 * @create by 2020-04-10 12:26
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private CustomAuthProperties customAuthProperties;
    @Autowired
    private JwtHandler jwtHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isLogin = this.checkUrl(request, customAuthProperties.getLoginAndRegisterUrl());

        // 过滤链接
        if (isLogin || ignoreUrl(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt检查
        String jwt = jwtHandler.getJwtFromRequest(request);
        if (StringUtils.isEmpty(jwt)) {
            this.responseWrite(response, RestResult.failure("请先登录！"));
            return;
        }

        try {
            String username = jwtHandler.getUsernameFromJWT(jwt);
            RequestContextHolder.currentRequestAttributes().setAttribute("username", username, RequestAttributes.SCOPE_REQUEST);
            filterChain.doFilter(request, response);
        } catch (WfcAuthException e) {
            this.responseWrite(response, RestResult.failure(e.getMessage()));
            return;
        }

    }

    private void responseWrite(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(obj));
    }

    private boolean ignoreUrl(HttpServletRequest request) {
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        if (httpMethod == null) {
            httpMethod = HttpMethod.GET;
        }

        boolean flag = false;
        switch (httpMethod) {
            case GET:
                flag = this.checkUrl(request, customAuthProperties.getIgnore().getGet());
                break;
            case POST:
                flag = this.checkUrl(request, customAuthProperties.getIgnore().getPost());
                break;
            case DELETE:
                flag = this.checkUrl(request, customAuthProperties.getIgnore().getDelete());
                break;
            case PUT:
                flag = this.checkUrl(request, customAuthProperties.getIgnore().getPut());
                break;
            default:
                break;
        }

        return flag;
    }

    private boolean checkUrl(HttpServletRequest request, List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return false;
        }

        for (String url : urls) {
            if (pathMatcher.match(url, request.getServletPath())) {
                return true;
            }
        }

        return false;
    }

}
