package interview.credorax.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import interview.credorax.testtask.service.sanitizing.Sensitive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"name", "email"})
public class CardholderDto {
  @Sensitive
  @NotNull
  @NotBlank
  @Size(min = 1, max = 160) // todo
  private String name;
  @Email
  @NotNull
  @NotBlank
  @Size(max = 160) // todo
  private String email;
}
