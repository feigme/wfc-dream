package com.wfc.jwt.filter;

import com.wfc.jwt.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hui.guo
 * @since 2023/7/6 10:41 上午
 */
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenHelper tokenHelper;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = tokenHelper.getToken(request);
        if (StringUtils.isNotBlank(authToken)) {
            String userName = tokenHelper.getUserNameFromToken(authToken);
            if (StringUtils.isNotBlank(userName)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (tokenHelper.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, authToken, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
