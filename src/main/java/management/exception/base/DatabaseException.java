package management.exception.base;

public class DatabaseException extends BaseException {

  private static final String ERROR_CODE = "DATABASE_ERROR";

  public DatabaseException(String message,Throwable cause) {
    super(ERROR_CODE, message, cause);
  }

  protected DatabaseException(String ERROR_CODE, String message, Throwable cause) {
    super(ERROR_CODE, message, cause);
  }

  protected DatabaseException(String ERROR_CODE, String message) {
    super(ERROR_CODE, message);
  }
}
