package interview.credorax.testtask.controllers;

import static interview.credorax.testtask.exception.ErrorMessage.error;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.StringJoiner;

import interview.credorax.testtask.exception.ApiResponseException;
import interview.credorax.testtask.exception.ErrorMessage;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApiResponseException.class)
  public ResponseEntity<ErrorMessage> handleApiException(ApiResponseException cause) {
    log.debug("Exception happened in controller", cause);
    return new ResponseEntity<>(cause.getErrorMessage(), cause.getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleException(Exception cause) {
    log.error("Exception happen in controller", cause);
    return new ResponseEntity<>(error(INTERNAL_SERVER_ERROR.getReasonPhrase()), INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return processBindingResult(ex.getBindingResult());
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return processBindingResult(ex.getBindingResult());
  }

  private ResponseEntity<Object> processBindingResult(BindingResult bindingResult) {
    String errorMessage = toErrorMessage(bindingResult);
    ErrorMessage serverErrorMessage = error(errorMessage);
    return new ResponseEntity<>(serverErrorMessage, BAD_REQUEST);
  }

  private String toErrorMessage(BindingResult bindingResult) {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    if (bindingResult.hasErrors()) {
      for (FieldError error : bindingResult.getFieldErrors()) {
        String field = error.getField();
        joiner.add(field + ": " + error.getDefaultMessage());
      }
    }
    if (bindingResult.hasGlobalErrors()) {
      for (ObjectError error : bindingResult.getGlobalErrors()) {
        joiner.add(error.getDefaultMessage());
      }
    }
    return joiner.toString();
  }
}
