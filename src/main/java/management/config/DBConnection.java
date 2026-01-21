package management.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import management.exception.database.ApplicationPropertiesException;
import management.exception.database.ConnectionInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {

  private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
  private static final HikariDataSource mysqlDataSource;

  static {
    try {
      Properties prop = new Properties();
      try (InputStream input = DBConnection.class.getClassLoader()
          .getResourceAsStream("application.properties")) {

        if (input == null) {
          logger.error("Application properties file not found");
          throw new ApplicationPropertiesException();
        }

        prop.load(input);
      }

      HikariConfig mysqlConfig = new HikariConfig();
      mysqlConfig.setJdbcUrl(prop.getProperty("mysql.url"));
      mysqlConfig.setUsername(prop.getProperty("mysql.username"));
      mysqlConfig.setPassword(prop.getProperty("mysql.password"));

      mysqlConfig.setMaximumPoolSize(
          Integer.parseInt(prop.getProperty("hikari.maximum-pool-size")));
      mysqlConfig.setMinimumIdle(Integer.parseInt(prop.getProperty("hikari.minimum-idle")));
      mysqlConfig.setConnectionTimeout(
          Integer.parseInt(prop.getProperty("hikari.connection-timeout")));
      mysqlConfig.setIdleTimeout(Integer.parseInt(prop.getProperty("hikari.idle-timeout")));

      mysqlDataSource = new HikariDataSource(mysqlConfig);
      logger.info("MySQL connection established");

    } catch (IOException | NumberFormatException e) {
      logger.error("Failed to initialise mysql connection pool", e);
      throw new ConnectionInitException(e);
    }
  }

  public static Connection getMysqlConnection() throws SQLException {
    return mysqlDataSource.getConnection();
  }

  private DBConnection() {
  }
}
