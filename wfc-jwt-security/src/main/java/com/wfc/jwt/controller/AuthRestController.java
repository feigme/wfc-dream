package com.wfc.jwt.controller;

import com.wfc.jwt.config.JwtProperties;
import com.wfc.jwt.domain.AuthRequest;
import com.wfc.jwt.domain.AuthToken;
import com.wfc.jwt.domain.AuthUser;
import com.wfc.jwt.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hui.guo
 * @since 2023/7/6 10:48 上午
 */
@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final TokenHelper tokenHelper;
    private final JwtProperties jwtProperties;

    @PostMapping(value = "${security.jwt.create-auth-token-path:/api/auth/login}")
    public AuthToken createAuthenticationToken(@RequestBody AuthRequest credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jws = tokenHelper.generateToken(((User) authentication.getPrincipal()).getUsername());
        return new AuthToken(jws, jwtProperties.getExpiresIn());
    }

    @PostMapping(value = "${security.jwt.refresh-auth-token-path:/api/auth/refresh}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<AuthToken> refreshAuthenticationToken(HttpServletRequest request) {
        String authToken = tokenHelper.getToken(request);
        String refreshedToken = tokenHelper.refreshToken(authToken);
        return ResponseEntity.ok(new AuthToken(refreshedToken, jwtProperties.getExpiresIn()));
    }

    @PostMapping(value = "${security.jwt.auth-me-path:/api/auth/me}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<AuthUser> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roleSet = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        AuthUser authUser = new AuthUser(authentication.getPrincipal().toString(), roleSet);
        return ResponseEntity.ok(authUser);
    }

}
