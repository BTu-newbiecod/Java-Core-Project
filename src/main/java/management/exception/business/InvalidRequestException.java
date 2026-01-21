package management.exception.business;

import management.exception.base.BusinessException;

public class InvalidRequestException extends BusinessException {

  public static final String ERROR_CODE = "INVALID_REQUEST";

  public InvalidRequestException(String message) {
    super(ERROR_CODE,message);
  }
}
