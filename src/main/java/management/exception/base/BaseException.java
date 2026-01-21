package management.exception.base;

public class BaseException extends RuntimeException {

  private final String ERROR_CODE;

  public BaseException(String ERROR_CODE, String message) {
    super(message);
    this.ERROR_CODE = ERROR_CODE;
  }

  public BaseException(String ERROR_CODE, String message, Throwable cause) {
    super(message, cause);
    this.ERROR_CODE = ERROR_CODE;
  }

  public String getErrorCode() {
    return this.ERROR_CODE;
  }
}
