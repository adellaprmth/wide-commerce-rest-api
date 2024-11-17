package wide_commerce.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.dto.WebResponse;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse> handleResponseStatusException(ResponseStatusException e){
        return new ResponseEntity<>(
                WebResponse.builder().message(e.getMessage()).build(),
                e.getStatusCode()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                WebResponse.builder().message(e.getMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
