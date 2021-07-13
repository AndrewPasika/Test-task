package interview.credorax.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
  @Positive
  private Long invoice;
  @Positive
  private BigDecimal amount;
  @NotNull
  private Currency currency;
  @Valid
  private CardholderDto cardholder;
  @Valid
  private CardDto card;
}
