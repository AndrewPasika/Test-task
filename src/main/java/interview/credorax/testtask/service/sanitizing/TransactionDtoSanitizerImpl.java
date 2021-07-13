package interview.credorax.testtask.service.sanitizing;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import interview.credorax.testtask.dto.CardDto;
import interview.credorax.testtask.dto.CardholderDto;
import interview.credorax.testtask.dto.TransactionDto;

@Service
@RequiredArgsConstructor
public class TransactionDtoSanitizerImpl implements TransactionDtoSanitizer {

  private final EncryptionService encryptionService;

  @Override
  public TransactionDto sanitize(TransactionDto transactionDto, SanitizeType sanitizeType) {
    CardDto cardDto = encryptionService.sanitize(transactionDto.getCard(), CardDto.class, sanitizeType);
    CardholderDto cardholderDto = encryptionService.sanitize(transactionDto.getCardholder(), CardholderDto.class, sanitizeType);
    return new TransactionDto(transactionDto.getInvoice(), transactionDto.getAmount(), transactionDto.getCurrency(), cardholderDto, cardDto);
  }

  @Override
  public TransactionDto desanitize(TransactionDto transactionDto, SanitizeType sanitizeType) {
    CardDto cardDto = encryptionService.desanitize(transactionDto.getCard(), CardDto.class, sanitizeType);
    CardholderDto cardholderDto = encryptionService.desanitize(transactionDto.getCardholder(), CardholderDto.class, sanitizeType);
    return new TransactionDto(transactionDto.getInvoice(), transactionDto.getAmount(), transactionDto.getCurrency(), cardholderDto, cardDto);
  }
}
