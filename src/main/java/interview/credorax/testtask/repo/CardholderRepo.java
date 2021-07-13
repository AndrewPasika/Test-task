package interview.credorax.testtask.repo;

import java.util.Optional;

import interview.credorax.testtask.repo.entity.Cardholder;

public interface CardholderRepo {

  void saveCardholder(Cardholder cardholder);

  Optional<Cardholder> findCardholder(String email);
}
