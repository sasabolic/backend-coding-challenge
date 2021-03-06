package com.engagetech.solution.adapter.primary.rest.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Rest Exception Handler.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleBadRequest(Exception e) {
    return buildResponseEntity(RestError.of(HttpStatus.BAD_REQUEST, e));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();

    RestError restError = RestError.of(status, ex, "Validation failed");
    restError.addValidationErrors(bindingResult.getAllErrors());

    return buildResponseEntity(restError);
  }

  private ResponseEntity<Object> buildResponseEntity(RestError restError) {
    return ResponseEntity.status(restError.getStatus()).body(restError);
  }

  /**
   * REST error object.
   */
  @Getter
  static class RestError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private List<String> errors;

    private RestError(LocalDateTime timestamp,
      HttpStatus status,
      String error,
      String exception,
      String message) {
      this.timestamp = timestamp;
      this.status = status.value();
      this.error = error;
      this.exception = exception;
      this.message = message;
    }

    static RestError of(HttpStatus status, Throwable ex) {
      return of(status, ex, ex.getLocalizedMessage());
    }

    static RestError of(HttpStatus status, Throwable ex, String message) {
      return new RestError(
        LocalDateTime.now(),
        status,
        status.getReasonPhrase(),
        ex.getClass().getName(),
        message);
    }

    void addValidationErrors(List<? extends ObjectError> bindErrors) {
      this.errors = bindErrors.stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
  }
}
