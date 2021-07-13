package interview.credorax.testtask.integration;

import static interview.credorax.testtask.dto.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import interview.credorax.testtask.dto.CardDto;
import interview.credorax.testtask.dto.CardholderDto;
import interview.credorax.testtask.dto.TransactionDto;
import interview.credorax.testtask.service.AuditService;

@MockBean(classes = AuditService.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
class TransactionIntegrationTest {

  @LocalServerPort
  private int randomServerPort;
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void submitAndRetrievePayment_validFlow_ok() {
    // given
    CardholderDto cardholderDto = new CardholderDto(UUID.randomUUID().toString(), String.format("%s@example.com", UUID.randomUUID().toString()));
    CardDto cardDto = new CardDto("5555555555554444", "0624", "789");
    TransactionDto transactionDto = new TransactionDto(RandomUtils.nextLong(), new BigDecimal(RandomUtils.nextDouble()), EUR, cardholderDto, cardDto);

    // when
    URI location = testRestTemplate.postForLocation(String.format("http://localhost:%d/api/v1/payments", randomServerPort), transactionDto);

    // then
    assertThat(location).isNotNull();
    assertThat(location.toString()).isEqualTo(String.format("http://localhost:%d/api/v1/payments/%d", randomServerPort, transactionDto.getInvoice()));

    ResponseEntity<TransactionDto> actual = testRestTemplate.getForEntity(location, TransactionDto.class);
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(transactionDto);
  }

  @Test
  void submitPayment_invalidPan_404() {
    // given
    CardholderDto cardholderDto = new CardholderDto(UUID.randomUUID().toString(), String.format("%s@example.com", UUID.randomUUID().toString()));
    CardDto cardDto = new CardDto("5555555555554440", "0624", "789");
    TransactionDto transactionDto = new TransactionDto(RandomUtils.nextLong(), new BigDecimal(RandomUtils.nextDouble()), EUR, cardholderDto, cardDto);

    // when
    ResponseEntity<String> actual = testRestTemplate.postForEntity(String.format("http://localhost:%d/api/v1/payments", randomServerPort), transactionDto, String.class);

    // then
    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(actual.getBody()).isEqualTo("{\"message\":\"card.pan: PAN is not valid\"}");
  }
}
