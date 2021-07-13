package interview.credorax.testtask.config;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public LuhnCheckDigit luhnCheckDigit() {
    return new LuhnCheckDigit();
  }
}
