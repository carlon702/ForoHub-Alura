package com.cjm.forum_hub.infra.errors;


import com.cjm.forum_hub.domain.error.DataError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404()
    {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){

        DataError dataError = new DataError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dataError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e)

    {
        var errorsBadRequest = e.getFieldErrors().stream().toList();

        return ResponseEntity.badRequest().body(errorsBadRequest);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity returnNullPointer(MethodArgumentNotValidException e)
    {
       DataError dtoError = new DataError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dtoError);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity returnIllegalStateException(MethodArgumentNotValidException e)
    {
        DataError dtoError = new DataError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dtoError);
    }

    private record DataValidationError(String variable, String message)
    {
        public  DataValidationError(FieldError error)
        {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
