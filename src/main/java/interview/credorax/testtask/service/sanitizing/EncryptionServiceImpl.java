package interview.credorax.testtask.service.sanitizing;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public <T> T sanitize(T value, Class<T> valueType, SanitizeType sanitizeType) {
    try {
      T copy = objectMapper.readValue(objectMapper.writeValueAsString(value), valueType);

      sanitizeFields(copy, copy.getClass().getDeclaredFields(), sanitizeType);
      return copy;
    } catch (Exception ex) {
      throw new IllegalStateException("Exception during encryption", ex);
    }
  }

  @Override
  public <T> T desanitize(T value, Class<T> valueType, SanitizeType desanitizeType) {
    try {
      T copy = objectMapper.readValue(objectMapper.writeValueAsString(value), valueType);

      desanitizeFields(copy, copy.getClass().getDeclaredFields(), desanitizeType);
      return copy;
    } catch (Exception ex) {
      throw new IllegalStateException("Exception during encryption", ex);
    }
  }

  @SneakyThrows
  private <T> void desanitizeFields(T value, Field[] fields, SanitizeType sanitizeType) {
    for (Field field : fields) {
      if (field.isAnnotationPresent(Sensitive.class)) {
        field.setAccessible(true);
        Object fieldValue = field.get(value);
        if (!(fieldValue instanceof String)) {
          throw new IllegalArgumentException(String.format("%s annotation is present on non-string field", Sensitive.class.getSimpleName()));
        }

        if (sanitizeType == SanitizeType.ENCRYPT) {
          decryptField(value, field, (String) fieldValue);
        } else {
          throw new IllegalArgumentException("Only encrypted fields are supported");
        }
      }
    }
  }

  @SneakyThrows
  private <T> void sanitizeFields(T value, Field[] fields, SanitizeType sanitizeType) {
    for (Field field : fields) {
      if (field.isAnnotationPresent(Sensitive.class)) {
        field.setAccessible(true);
        Object fieldValue = field.get(value);
        if (!(fieldValue instanceof String)) {
          throw new IllegalArgumentException(String.format("%s annotation is present on non-string field", Sensitive.class.getSimpleName()));
        }

        if (sanitizeType == SanitizeType.ENCRYPT) {
          encryptField(value, field, (String) fieldValue);
        } else if (sanitizeType == SanitizeType.MASK) {
          maskField(value, field, (String) fieldValue);
        } else {
          throw new IllegalArgumentException(String.format("Unknown sanitize type was provided: %s", sanitizeType));
        }
      }
    }
  }

  @SneakyThrows
  private <T> void maskField(T value, Field field, String fieldValue) {
    String masked = StringUtils.repeat("*", fieldValue.length());
    field.set(value, masked);
  }

  @SneakyThrows
  private <T> void encryptField(T value, Field field, String fieldValue) {
    String encoded = Base64.getEncoder().encodeToString(fieldValue.getBytes(UTF_8));
    field.set(value, encoded);
  }

  @SneakyThrows
  private <T> void decryptField(T value, Field field, String fieldValue) {
    String decoded = new String(Base64.getDecoder().decode(fieldValue.getBytes(UTF_8)), UTF_8);
    field.set(value, decoded);
  }
}
