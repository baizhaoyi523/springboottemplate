package com.bzy.springtemplate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
@EnableWebSecurity
@EnableSpringHttpSession
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_PATTERN = "/admin/**";

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(ADMIN_PATTERN).authenticated()
                .anyRequest().permitAll()
        .and()
            .formLogin()
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
        .and()
            .exceptionHandling().authenticationEntryPoint(loginFailureHandler)
        ;
        // @formatter:on
    }

    @Bean
    protected SessionRepository sessionRepository() {
        MapSessionRepository mapSessionRepository = new MapSessionRepository();
        mapSessionRepository.setDefaultMaxInactiveInterval(10 * 60);
        return mapSessionRepository;
    }
}
