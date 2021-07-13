package interview.credorax.testtask.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import interview.credorax.testtask.dto.TransactionDto;

@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

  private final String auditFilePath;
  private final CsvConverter csvConverter;

  @Autowired
  public AuditServiceImpl(@Value("${application.audit.file-path}") String auditFilePath, CsvConverter csvConverter) {
    this.csvConverter = csvConverter;
    this.auditFilePath = auditFilePath;
  }

  @Async // todo: configure thread pool
  @Override
  public void saveTransaction(TransactionDto transactionDto) {
    try {
      File auditFile = new File(auditFilePath);
      auditFile.createNewFile();

      String csv = System.lineSeparator() + csvConverter.toCsv(transactionDto);
      Files.write(Paths.get(auditFilePath), csv.getBytes(UTF_8), StandardOpenOption.APPEND);
    } catch (IOException ex) {
      log.error("Exception occurred during saving transaction", ex);
    }
  }
}
