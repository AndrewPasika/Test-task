package interview.credorax.testtask.service;

import interview.credorax.testtask.dto.TransactionDto;

public interface AuditService {

  void saveTransaction(TransactionDto transactionDto);
}
