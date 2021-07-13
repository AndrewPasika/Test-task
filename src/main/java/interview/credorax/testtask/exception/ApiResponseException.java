package interview.credorax.testtask.exception;

import static interview.credorax.testtask.exception.ErrorMessage.error;
import static java.lang.String.format;

import lombok.Getter;

import org.springframework.http.HttpStatus;

public class ApiResponseException extends RuntimeException {

  @Getter
  private final ErrorMessage errorMessage;

  @Getter
  private final HttpStatus httpStatus;

  public ApiResponseException(String messagePattern, HttpStatus httpStatus, Object... args) {
    super(format(messagePattern, args));
    this.errorMessage = error(getMessage());
    this.httpStatus = httpStatus;
  }
}
