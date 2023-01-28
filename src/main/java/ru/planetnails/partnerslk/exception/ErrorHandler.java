package ru.planetnails.partnerslk.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> NotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PartnerNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> PartnerNotFoundException(PartnerNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsersNameIsAlreadyTaken.class})
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "This name is already taken by another user ")
    public ResponseEntity<String> UsersNameIsAlreadyTaken(UsersNameIsAlreadyTaken e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserOrPasswordAreIncorrectException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "username or password are incorrect")
    public ResponseEntity<String> UserOrPasswordAreIncorrectException(UserOrPasswordAreIncorrectException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({LoadingError.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ошибка загрузки данных")
    public ResponseEntity<String> LoadingError(LoadingError e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
