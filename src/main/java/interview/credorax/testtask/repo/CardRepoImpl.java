package interview.credorax.testtask.repo;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import interview.credorax.testtask.repo.entity.Card;

@Repository
public class CardRepoImpl implements CardRepo {

  private final Map<String, Card> cardByPan = new ConcurrentHashMap<>();

  @Override
  public void saveCard(Card card) {
    cardByPan.put(card.getPan(), card);
  }

  @Override
  public Optional<Card> findCard(String pan) {
    return Optional.ofNullable(cardByPan.get(pan));
  }
}
