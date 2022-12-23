package ru.planetnails.partnerslk.exception;

public class UserOrPasswordAreIncorrectException extends RuntimeException {

    public UserOrPasswordAreIncorrectException(String message) {
        super(message);
    }

}