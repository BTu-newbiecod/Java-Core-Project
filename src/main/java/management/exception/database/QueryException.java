package management.exception.database;

import management.exception.base.DatabaseException;

public class QueryException extends DatabaseException {

  private static final String ERROR_CODE = "QUERY_FAILED";

  public QueryException(Throwable cause) {
    super(ERROR_CODE, "Query failed: " + cause.getMessage(), cause);
  }
}
