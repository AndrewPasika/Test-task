package interview.credorax.testtask.repo;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import interview.credorax.testtask.repo.entity.Cardholder;

@Repository
public class CardholderRepoImpl implements CardholderRepo {

  private Map<String, Cardholder> cardholderByEmail = new ConcurrentHashMap<>();

  @Override
  public void saveCardholder(Cardholder cardholder) {
    cardholderByEmail.put(cardholder.getEmail(), cardholder);
  }

  @Override
  public Optional<Cardholder> findCardholder(String email) {
    return Optional.ofNullable(cardholderByEmail.get(email));
  }
}
