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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String SWAGGER_WHITELIST1 = "/v3/api-docs/";
    private static final String SWAGGER_WHITELIST = "/swagger-ui/**";

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();


    }
}