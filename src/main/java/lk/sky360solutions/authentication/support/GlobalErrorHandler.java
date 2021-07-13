package lk.sky360solutions.authentication.support;

import lk.sky360solutions.authentication.exception.InvalidRefreshTokenException;
import lk.sky360solutions.authentication.exception.ProductNotFoundException;
import lk.sky360solutions.authentication.exception.RefreshTokenExpiredException;
import lk.sky360solutions.authentication.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
      ProductNotFoundException.class,
      UserNotFoundException.class
    })
    public Map<String, String> handleNotFoundError(Exception e) {
        return Collections.singletonMap("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
      InvalidRefreshTokenException.class,
      RefreshTokenExpiredException.class
    })
    public Map<String, String> handleBadRequestError(Exception e) {
      return Collections.singletonMap("error", e.getMessage());
    }
}
