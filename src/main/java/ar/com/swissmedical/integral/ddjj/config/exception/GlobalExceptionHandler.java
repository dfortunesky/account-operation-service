package ar.com.swissmedical.integral.ddjj.config.exception;

import ar.com.swissmedical.integral.ddjj.application.dto.response.ErrorResponseDto;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponseDto> handleNumberFormat(NumberFormatException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                "Parámetro con formato inválido: se esperaba un número",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParam(MissingServletRequestParameterException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                "Parámetro requerido ausente: " + ex.getParameterName(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({DataAccessException.class, PersistenceException.class})
    public ResponseEntity<ErrorResponseDto> handleDatabaseException(Exception ex) {
        Throwable rootCause = ex;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        String message = rootCause.getMessage() != null ? rootCause.getMessage() : rootCause.getClass().getSimpleName();
        ErrorResponseDto error = new ErrorResponseDto(
                "Error al acceder a la base de datos: " + message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponseDto error = new ErrorResponseDto("Recurso no encontrado: " + ex.getResourcePath(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
