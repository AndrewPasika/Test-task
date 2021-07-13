package interview.credorax.testtask.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorMessage {

  private final String message;

  public static ErrorMessage error(String message) {
    return new ErrorMessage(message);
  }

  public static ErrorMessage error(String format, Object... args) {
    return new ErrorMessage(String.format(format, args));
  }
}
