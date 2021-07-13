package interview.credorax.testtask.service;

import org.springframework.stereotype.Component;

import interview.credorax.testtask.dto.TransactionDto;

@Component
public class CsvConverterImpl implements CsvConverter {

  @Override
  public String toCsv(TransactionDto t) {
    return String.format("%d,%s,%s,%s,%s,%s,%s,%s", t.getInvoice(), t.getAmount(), t.getCurrency(),
        t.getCardholder().getName(), t.getCardholder().getEmail(),
        t.getCard().getPan(), t.getCard().getExpiry(), t.getCard().getCvv());
  }
}
