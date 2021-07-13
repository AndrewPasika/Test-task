package interview.credorax.testtask.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import javax.validation.Valid;

import interview.credorax.testtask.dto.SuccessResponseDto;
import interview.credorax.testtask.dto.TransactionDto;
import interview.credorax.testtask.service.TransactionService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments", produces = APPLICATION_JSON_VALUE)
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponseDto> submitPayment(@RequestBody @Valid TransactionDto transactionDto) {
    log.debug("Processing submitted payment: {}", transactionDto);
    transactionService.submitPayment(transactionDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(transactionDto.getInvoice())
        .toUri();
    return ResponseEntity.created(location).body(new SuccessResponseDto(true));
  }

  @GetMapping(path = "/{invoice}")
  public ResponseEntity<TransactionDto> retrieveTransaction(@PathVariable Long invoice) {
    log.debug("Getting transaction by invoice: {}", invoice);
    return ResponseEntity.ok(transactionService.getTransaction(invoice));
  }
}
