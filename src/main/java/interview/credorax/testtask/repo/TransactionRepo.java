package interview.credorax.testtask.repo;

import java.util.Optional;

import interview.credorax.testtask.repo.entity.Transaction;

public interface TransactionRepo {

  void saveTransaction(Transaction transaction);

  Optional<Transaction> findTransaction(Long invoice);
}
