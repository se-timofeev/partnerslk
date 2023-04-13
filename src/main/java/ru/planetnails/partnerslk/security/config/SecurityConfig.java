package ru.planetnails.partnerslk.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.planetnails.partnerslk.security.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String SWAGGER_WHITELIST1 = "/v3/api-docs/**";
    private static final String SWAGGER_WHITELIST2 = "/swagger-ui/**";
    private static final String SWAGGER_WHITELIST3 = "/swagger-ui.html";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(SWAGGER_WHITELIST1).permitAll()
                .antMatchers(SWAGGER_WHITELIST2).permitAll()
                .antMatchers(SWAGGER_WHITELIST3).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .build();
    }
}