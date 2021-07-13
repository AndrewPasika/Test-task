package interview.credorax.testtask.service;

import interview.credorax.testtask.dto.TransactionDto;

public interface CsvConverter {

  String toCsv(TransactionDto transactionDto);
}
