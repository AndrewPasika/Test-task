package interview.credorax.testtask.validation;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ExpiryValidator implements ConstraintValidator<Expiry, String> {

  private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
      .appendPattern("MM-yy")
      .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
      .toFormatter();

  @Override
  public boolean isValid(String expiry, ConstraintValidatorContext context) {
    try {
      String month = expiry.substring(0, 2);
      String year = expiry.substring(2, 4);
      LocalDate.parse(String.format("%s-%s", month, year), formatter);
      return true;
    } catch (Exception ex) {
      log.trace("Expiry {} is not valid", expiry, ex);
    }
    return false;
  }
}
