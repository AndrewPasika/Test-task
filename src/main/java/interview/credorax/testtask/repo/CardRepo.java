package interview.credorax.testtask.repo;

import java.util.Optional;

import interview.credorax.testtask.repo.entity.Card;

public interface CardRepo {

  void saveCard(Card card);

  Optional<Card> findCard(String pan);
}
