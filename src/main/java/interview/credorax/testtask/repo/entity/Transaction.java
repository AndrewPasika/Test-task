package interview.credorax.testtask.repo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import interview.credorax.testtask.dto.Currency;

@Data
@AllArgsConstructor
public class Transaction {
  private Long invoice;
  private BigDecimal amount;
  private Currency currency;
  private String cardholderEmail;
  private String cardPan;
}
