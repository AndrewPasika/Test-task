package interview.credorax.testtask.service.sanitizing;

import interview.credorax.testtask.dto.TransactionDto;

public interface TransactionDtoSanitizer {

  TransactionDto sanitize(TransactionDto transactionDto, SanitizeType sanitizeType);

  TransactionDto desanitize(TransactionDto transactionDto, SanitizeType sanitizeType);
}
