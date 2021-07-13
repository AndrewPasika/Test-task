package interview.credorax.testtask.service;

import interview.credorax.testtask.dto.TransactionDto;

public interface TransactionService {

  void submitPayment(TransactionDto transactionDto);

  TransactionDto getTransaction(Long invoice);
}
