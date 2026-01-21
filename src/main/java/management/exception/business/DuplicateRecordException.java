package management.exception.business;

import management.exception.base.BusinessException;

public class DuplicateRecordException extends BusinessException {
  private static final String ERROR_CODE = "DUPLICATE_RECORD";

  public DuplicateRecordException(String message) {
    super(ERROR_CODE, message);
  }
}
