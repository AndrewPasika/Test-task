package interview.credorax.testtask.service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import interview.credorax.testtask.dto.CardDto;
import interview.credorax.testtask.dto.CardholderDto;
import interview.credorax.testtask.dto.TransactionDto;
import interview.credorax.testtask.exception.ApiResponseException;
import interview.credorax.testtask.repo.CardRepo;
import interview.credorax.testtask.repo.CardholderRepo;
import interview.credorax.testtask.repo.TransactionRepo;
import interview.credorax.testtask.repo.entity.Card;
import interview.credorax.testtask.repo.entity.Cardholder;
import interview.credorax.testtask.repo.entity.Transaction;
import interview.credorax.testtask.service.sanitizing.SanitizeType;
import interview.credorax.testtask.service.sanitizing.TransactionDtoSanitizer;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepo transactionRepo;
  private final CardholderRepo cardholderRepo;
  private final CardRepo cardRepo;
  private final AuditService auditService;
  private final TransactionDtoSanitizer transactionSanitizer;

  @Override
  public void submitPayment(TransactionDto transactionDto) {
    TransactionDto masked = transactionSanitizer.sanitize(transactionDto, SanitizeType.MASK);
    auditService.saveTransaction(masked);

    TransactionDto encrypted = transactionSanitizer.sanitize(transactionDto, SanitizeType.ENCRYPT);
    saveCard(encrypted.getCard());
    saveCardholder(encrypted.getCardholder());
    saveTransaction(encrypted);
  }

  @Override // todo: should be transactional
  public TransactionDto getTransaction(Long invoice) {
    Transaction transaction = transactionRepo.findTransaction(invoice)
        .orElseThrow(() -> new ApiResponseException("No transaction was found for invoice: %d", NOT_FOUND, invoice));
    Cardholder cardholder = cardholderRepo.findCardholder(transaction.getCardholderEmail())
        .orElseThrow(() -> new ApiResponseException(INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR));
    Card card = cardRepo.findCard(transaction.getCardPan())
        .orElseThrow(() -> new ApiResponseException(INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR));

    CardholderDto cardholderDto = new CardholderDto(cardholder.getName(), cardholder.getEmail());
    CardDto cardDto = new CardDto(card.getPan(), card.getExpiry(), card.getCvv());
    TransactionDto transactionDto = new TransactionDto(invoice, transaction.getAmount(), transaction.getCurrency(), cardholderDto, cardDto);
    return transactionSanitizer.desanitize(transactionDto, SanitizeType.ENCRYPT);
  }

  private void saveCard(CardDto cardDto) {
    Card card = new Card(cardDto.getPan(), cardDto.getExpiry(), cardDto.getCvv());
    cardRepo.saveCard(card);
  }

  private void saveCardholder(CardholderDto cardholderDto) {
    Cardholder cardholder = new Cardholder(cardholderDto.getName(), cardholderDto.getEmail());
    cardholderRepo.saveCardholder(cardholder);
  }

  private void saveTransaction(TransactionDto transactionDto) {
    Transaction transaction = new Transaction(transactionDto.getInvoice(),
        transactionDto.getAmount(),
        transactionDto.getCurrency(),
        transactionDto.getCardholder().getEmail(),
        transactionDto.getCard().getPan());
    transactionRepo.saveTransaction(transaction);
  }
}
