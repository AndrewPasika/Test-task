package interview.credorax.testtask.repo;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import interview.credorax.testtask.repo.entity.Transaction;

@Repository
public class TransactionRepoImpl implements TransactionRepo {

  private final Map<Long, Transaction> transactionInvoice = new ConcurrentHashMap<>();

  @Override
  public void saveTransaction(Transaction transaction) {
    transactionInvoice.put(transaction.getInvoice(), transaction);
  }

  @Override
  public Optional<Transaction> findTransaction(Long invoice) {
    return Optional.ofNullable(transactionInvoice.get(invoice));
  }
}
