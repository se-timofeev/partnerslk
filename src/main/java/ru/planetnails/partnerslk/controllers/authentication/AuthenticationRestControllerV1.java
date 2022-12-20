package ru.planetnails.partnerslk.controllers.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.config.CustomAuthenticationManager;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.security.jwt.JwtTokenProvider;
import ru.planetnails.partnerslk.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final CustomAuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(CustomAuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) throws  HttpMessageNotReadableException {
        log.info("login {}", requestDto);
        try {
            String username = requestDto.getUsername();
            User user = userService.findByName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));


            String token = jwtTokenProvider.createToken(username, user.getRoles());
            log.info("token has been granted to user {}", username);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
