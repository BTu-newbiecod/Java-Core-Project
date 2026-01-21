package management.exception.business;

import management.exception.base.BusinessException;

public class ResourceNotFoundException extends BusinessException {

  private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";

  public ResourceNotFoundException(String message) {
    super(ERROR_CODE,message);
  }
}
