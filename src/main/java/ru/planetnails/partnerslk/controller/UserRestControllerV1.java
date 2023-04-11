package ru.planetnails.partnerslk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.model.user.UserStatus;
import ru.planetnails.partnerslk.model.user.dto.UserAddDto;
import ru.planetnails.partnerslk.model.user.dto.UserFullOutDto;
import ru.planetnails.partnerslk.model.user.dto.UserMapper;
import ru.planetnails.partnerslk.model.user.dto.UserOutDto;
import ru.planetnails.partnerslk.security.jwt.JwtTokenProvider;
import ru.planetnails.partnerslk.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@Validated
@CrossOrigin
@Tag(name = "Users", description = "You can create, modify and, get the users")
@RequestMapping(value = "/api/v1/users")
@Slf4j
public class UserRestControllerV1 {
    private final UserService userService;

    @Operation(summary = "Get the user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping(value = "{id}")
    public ResponseEntity<UserOutDto> getUserById(@PathVariable(name = "id") String userId) {
        User user = userService.findById(userId);
        if (user == null || user.getStatus().equals(UserStatus.DELETED)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserOutDto result = UserMapper.fromUserToUserOutDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Operation(summary = "Get the userID by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping(value = "/name/{username}")
    public ResponseEntity<UserFullOutDto> getUserByUsername(@PathVariable(name = "username") String username) {
        User user = userService.findByUsername(username);
        if (user == null || user.getStatus().equals(UserStatus.DELETED)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserFullOutDto result = UserMapper.fromUserToUserFullOutDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Create or update the user." +
            " Important! Field 'name' must be unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "409", description = "Users name is already taken",
                    content = @Content)
    })
    @PostMapping
    @PutMapping
    public UserOutDto add(@RequestBody UserAddDto userAddDDto) {
        return userService.add(userAddDDto);
    }

    @Operation(summary = "Set the users status as ACTIVE  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PatchMapping("/{userId}/active")
    public UserOutDto setUserActive(@PathVariable String userId) {
        return userService.setUserActive(userId);
    }

    @Operation(summary = "Set the users status as PENDING  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PatchMapping("/{userId}/pending")
    public UserOutDto setUserPending(@PathVariable String userId) {
        return userService.setUserPending(userId);
    }

    @Operation(summary = "Set the users status as BLOCKED  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PatchMapping("/{userId}/block")
    public UserOutDto setUserBlocked(@PathVariable String userId) {
        return userService.setUserBlocked(userId);
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId, HttpServletRequest request ) {
        log.info("Получен эндпоинт DELETE /api/v1/users; userId = {} " + userId);
        userService.deleteUser(userId,request);
    }
}