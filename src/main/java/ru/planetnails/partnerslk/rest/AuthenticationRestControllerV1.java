package ru.planetnails.partnerslk.rest;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.dto.AuthenticationRequestDto;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.security.jwt.JwtTokenProvider;
import ru.planetnails.partnerslk.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) throws MismatchedInputException, HttpMessageNotReadableException {
        log.info("login {}", requestDto);
        try {
            String username = requestDto.getUsername();
            User user = userService.findByName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
