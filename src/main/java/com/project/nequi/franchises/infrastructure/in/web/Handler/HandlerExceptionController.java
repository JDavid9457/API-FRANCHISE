package com.project.nequi.franchises.infrastructure.in.web.Handler;



import com.project.nequi.franchises.infrastructure.dto.response.ErrorModelDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler()
    public ResponseEntity<ErrorModelDTO> notFoundException(Exception e) {
        ErrorModelDTO errorModelDTO = ErrorModelDTO.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorModelDTO);
    }

}
