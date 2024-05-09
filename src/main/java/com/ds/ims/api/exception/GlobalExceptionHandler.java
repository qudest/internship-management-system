package com.ds.ims.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import static org.springframework.http.HttpStatus.*;

/**
 * Глобальный обработчик исключений
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(404, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorDto(401, e.getMessage()), UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ErrorDto(400, e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(new ErrorDto(403, e.getMessage()), FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(401, e.getMessage()), UNAUTHORIZED);
    }

    @ExceptionHandler(GitlabException.class)
    public ResponseEntity<?> handleGitlabException(GitlabException e) {
        return new ResponseEntity<>(new ErrorDto(500, e.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
