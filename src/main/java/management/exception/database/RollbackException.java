package management.exception.database;

import management.exception.base.DatabaseException;

public class RollbackException extends DatabaseException {

  private static final String ERROR_CODE = "ROLLBACK_FAILED";

  public RollbackException(Throwable cause) {
    super(ERROR_CODE,"rollback failed", cause);
  }
}
