package interview.credorax.testtask.service.sanitizing;

public interface EncryptionService {

  <T> T sanitize(T value, Class<T> valueType, SanitizeType sanitizeType);

  <T> T desanitize(T value, Class<T> valueType, SanitizeType sanitizeType);
}
