package management.exception.database;

import management.exception.base.DatabaseException;

public class CloseConnectionException extends DatabaseException {

  private static final String ERROR_CODE = "CONNECTION_CLOSING_FAILED";

  public CloseConnectionException(Throwable cause) {
    super(ERROR_CODE,"Closing Connection Failed", cause);
  }

}
