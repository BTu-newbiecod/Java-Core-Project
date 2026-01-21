package management.repository;

import java.sql.Connection;
import java.sql.SQLException;
import management.exception.business.ResourceNotFoundException;
import management.exception.database.CloseConnectionException;
import management.exception.database.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseJdbcRepository {

  private static final Logger logger = LoggerFactory.getLogger(BaseJdbcRepository.class);

  protected void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.setAutoCommit(true);
        connection.close();
      } catch (SQLException e) {
        logger.error("(CloseConnection) close connection error", e);
        throw new CloseConnectionException(e);
      }
    }
  }

  protected void rollback(Connection connection) {
    if (connection != null) {
      try {
        connection.rollback();
      } catch (SQLException e) {
        logger.error("(rollback) rollback error", e);
        throw new RollbackException(e);
      }
    }
  }

  protected void checkResult(int result, String id) {
    if (result == 0) {
      logger.error("(checkResult) no rows affected for id: {}", id);
      throw new ResourceNotFoundException("No rows affected for id: " + id);
    }
  }
}
