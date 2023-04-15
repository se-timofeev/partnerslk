package ru.planetnails.partnerslk.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.UserStatus;
import ru.planetnails.partnerslk.security.config.CustomAuthenticationManager;
import ru.planetnails.partnerslk.security.jwt.JwtTokenProvider;
import ru.planetnails.partnerslk.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@Slf4j
@Tag(name = "Authentication", description = "")
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final CustomAuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(CustomAuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider,
                                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Operation(summary = "Getting the token with 'username' and 'password'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You will get the token if the user and password are " +
                    "correct",
                    content = {@Content(mediaType = "application/json"
                    )}),
            @ApiResponse(responseCode = "401", description = "User or password are incorrect",
                    content = {@Content(mediaType = "application/json"
                    )})
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) throws HttpMessageNotReadableException {
        log.info("login {}", requestDto);
     //   try {
            String username = requestDto.getUsername();
            User user = userService.findByName(username);
            if (user == null) {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_GATEWAY);
            }
            if (user == null || user.getStatus() != UserStatus.ACTIVE) {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.CONFLICT);
            }
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
//                    requestDto.getPassword()));
//            String token = jwtTokenProvider.createToken(username, user.getRoles());
//            log.info("token has been granted to user {}", username);
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", "");
            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
//
//        }
    }
}
