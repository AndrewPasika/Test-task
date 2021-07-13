package interview.credorax.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

import interview.credorax.testtask.service.sanitizing.Sensitive;
import interview.credorax.testtask.validation.Expiry;
import interview.credorax.testtask.validation.Pan;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pan", "cvv"})
public class CardDto {
  @Pan
  @Sensitive
  @Pattern(regexp = "^[0-9]{16}$")
  private String pan;
  @Expiry
  @Sensitive
  @Pattern(regexp = "^[0-9]{4}$")
  private String expiry;
  @Sensitive
  @Pattern(regexp = "^[0-9]{3}$")
  private String cvv;
}
