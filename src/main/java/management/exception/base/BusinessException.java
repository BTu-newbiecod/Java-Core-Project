package management.exception.base;

public class BusinessException extends BaseException {

  private static final String ERROR_CODE = "BUSINESS_ERROR";

  public BusinessException(String message) {
    super(ERROR_CODE, message);
  }

  protected BusinessException(String ERROR_CODE, String message) {
    super(ERROR_CODE, message);
  }
}
