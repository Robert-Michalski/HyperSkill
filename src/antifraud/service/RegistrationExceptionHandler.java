package antifraud.service;

import antifraud.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            NullPointerException.class
    })
    public ResponseEntity<ErrorDTO> handleEmptyUserInput() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
