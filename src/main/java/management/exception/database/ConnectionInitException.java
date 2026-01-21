package management.exception.database;

import java.sql.Connection;
import management.exception.base.DatabaseException;

public class ConnectionInitException extends DatabaseException {

  private static final String ERROR_CODE = "CONNECTION_INIT_ERROR";

  public ConnectionInitException(Throwable cause) {
    super(ERROR_CODE,"Connection Init Failed",cause);
  }
}
