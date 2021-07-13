package interview.credorax.testtask.validation;

import lombok.RequiredArgsConstructor;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PanValidator implements ConstraintValidator<Pan, String> {

  private final LuhnCheckDigit luhnCheckDigit;

  @Override
  public boolean isValid(String pan, ConstraintValidatorContext context) {
    return luhnCheckDigit.isValid(pan);
  }
}
