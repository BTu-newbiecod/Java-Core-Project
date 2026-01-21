package management.exception.database;

import management.exception.base.DatabaseException;

public class ApplicationPropertiesException extends DatabaseException {

  private static final String ERROR_CODE = "PROPERTIES_LOAD_ERROR";

  public ApplicationPropertiesException() {
    super(ERROR_CODE, "application properties loaded error");
  }
}
