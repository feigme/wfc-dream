package com.wfc.starter.jwt.autoconfigure;

import com.wfc.jwt.config.JwtProperties;
import com.wfc.jwt.controller.AuthRestController;
import com.wfc.jwt.filter.TokenAuthFilter;
import com.wfc.jwt.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;

/**
 * @author hui.guo
 * @since 2023/7/6 11:09 上午
 */
@Slf4j
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
public class JwtSecurityAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "security.jwt")
    public JwtProperties jwtProperties() {
        log.info("-----JwtProperties------");
        return new JwtProperties();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        log.info("-----PasswordEncoder------");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenHelper tokenHelper(JwtProperties jwtProperties) {
        log.info("----TokenHelper-------");
        return new TokenHelper(jwtProperties);
    }

    @Configuration
    @ConditionalOnProperty(name = "security.jwt.auth-api-enabled", havingValue = "true", matchIfMissing = true)
    @ComponentScan(basePackageClasses = AuthRestController.class)
    public static class DefaultAuthApiConfigurer {
    }

    @Slf4j
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true, proxyTargetClass = true)
    @RequiredArgsConstructor
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;
        private final TokenHelper tokenHelper;
        private final JwtProperties jwtProperties;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            log.info("----AuthenticationManager-------");
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String[] permitAllPaths = jwtProperties.getPermitAllPaths().toArray(new String[0]);
            log.info("base-path: "+ jwtProperties.getBasePath());
            log.info("permitAllPaths: "+ Arrays.toString(permitAllPaths));
            http
                    .antMatcher(jwtProperties.getBasePath())
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(permitAllPaths).permitAll()
                    .and()
                    .addFilterBefore(tokenAuthFilter(), BasicAuthenticationFilter.class);
            http
                    .csrf().disable();
        }

        private TokenAuthFilter tokenAuthFilter() {
            return new TokenAuthFilter(tokenHelper, userDetailsService);
        }
    }

}
