package com.accenture.chat.exception;

public class FileReadException extends RuntimeException {

    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}