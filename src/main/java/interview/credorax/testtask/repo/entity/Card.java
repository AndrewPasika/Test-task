package interview.credorax.testtask.repo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
  private String pan;
  private String expiry;
  private String cvv;
}
