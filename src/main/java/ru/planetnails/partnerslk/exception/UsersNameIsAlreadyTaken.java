package ru.planetnails.partnerslk.exception;

public class UsersNameIsAlreadyTaken extends RuntimeException {
    public UsersNameIsAlreadyTaken(String message) {
        super(message);
    }
}
