package management.repository.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import management.config.DBConnection;
import management.constant.admin.AdminEntityConstant;
import management.constant.admin.AdminSqlConstant;
import management.exception.database.QueryException;
import management.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminRepositoryImpl implements AdminRepository {

  private static final Logger logger = LoggerFactory.getLogger(AdminRepositoryImpl.class);

  private Admin mapToModel(ResultSet rs) throws SQLException {
    return new Admin(
        rs.getString(AdminEntityConstant.COLUMN_ID),
        rs.getString(AdminEntityConstant.COLUMN_USERNAME),
        rs.getString(AdminEntityConstant.COLUMN_PASSWORD)
    );
  }

  @Override
  public Admin findByUsername(String username) {
    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement ps = connection.prepareStatement(AdminSqlConstant.FIND_BY_USERNAME)) {

      ps.setString(1, username);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapToModel(rs);
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding admin by username: {}", username, e);
      throw new QueryException(e);
    }
    return null;
  }
}