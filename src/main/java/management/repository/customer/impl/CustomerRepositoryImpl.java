package management.repository.customer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import management.config.DBConnection;
import management.constant.customer.CustomerEntityConstant;
import management.constant.customer.CustomerSqlConstant;
import management.entity.CustomerEntity;
import management.exception.base.BaseException;
import management.exception.business.DuplicateRecordException;
import management.exception.database.QueryException;
import management.model.Customer;
import management.repository.BaseJdbcRepository;
import management.repository.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerRepositoryImpl extends BaseJdbcRepository implements CustomerRepository {

  private static final Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

  private CustomerEntity toEntity(Customer customer) {
    return new CustomerEntity(
        customer.getCustomerId(),
        customer.getCustomerName(),
        customer.getPhoneNumber(),
        customer.getEmail(),
        customer.getAddress());
  }

  private Customer toCustomer(CustomerEntity customerEntity) {
    return new Customer(
        customerEntity.getCustomerId(),
        customerEntity.getCustomerName(),
        customerEntity.getPhoneNumber(),
        customerEntity.getEmail(),
        customerEntity.getAddress()
    );
  }

  private CustomerEntity mapToEntity(ResultSet resultSet) throws SQLException {
    return new CustomerEntity(
        resultSet.getString(CustomerEntityConstant.COLUMN_CUSTOMER_ID),
        resultSet.getString(CustomerEntityConstant.COLUMN_CUSTOMER_NAME),
        resultSet.getString(CustomerEntityConstant.COLUMN_PHONE_NUMBER),
        resultSet.getString(CustomerEntityConstant.COLUMN_EMAIL),
        resultSet.getString(CustomerEntityConstant.COLUMN_ADDRESS)
    );
  }

  private Map<String, Customer> toCustomerList(ResultSet resultSet) throws SQLException {
    Map<String, Customer> customers = new HashMap<>();

    while (resultSet.next()) {
      Customer customer = new Customer(
          resultSet.getString(CustomerEntityConstant.COLUMN_CUSTOMER_ID),
          resultSet.getString(CustomerEntityConstant.COLUMN_CUSTOMER_NAME),
          resultSet.getString(CustomerEntityConstant.COLUMN_PHONE_NUMBER),
          resultSet.getString(CustomerEntityConstant.COLUMN_EMAIL),
          resultSet.getString(CustomerEntityConstant.COLUMN_ADDRESS)
      );
      customers.put(customer.getCustomerId(), customer);
    }

    return Collections.unmodifiableMap(customers);
  }

  @Override
  public void save(Customer customer) {
    CustomerEntity customerEntity = this.toEntity(customer);

    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      int idx = 1;
      try (PreparedStatement preparedStatement = connection.prepareStatement(
          CustomerSqlConstant.SAVE_SQL)) {
        preparedStatement.setString(idx++, customerEntity.getCustomerId());
        preparedStatement.setString(idx++, customerEntity.getCustomerName());
        preparedStatement.setString(idx++, customerEntity.getPhoneNumber());
        preparedStatement.setString(idx++, customerEntity.getEmail());
        preparedStatement.setString(idx, customerEntity.getAddress());

        preparedStatement.executeUpdate();
      }

      connection.commit();

    } catch (SQLException e) {
      super.rollback(connection);

      if (e.getErrorCode() == 1062) {
        logger.error("(save) Duplicate entry for Customer ID: " + customerEntity.getCustomerId());
        throw new DuplicateRecordException("Email " + customerEntity.getEmail() + " is used!");
      }

      logger.error("(save) saving product error with id {}: {}", customerEntity.getCustomerId(),
          e.getMessage(), e);
      throw new QueryException(e);

    } finally {
      super.closeConnection(connection);
    }
  }

  @Override
  public Customer getById(String id) {
    logger.info("(getById) id: {}", id);

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            CustomerSqlConstant.GET_BY_ID_SQL)) {

      preparedStatement.setString(1, id);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {

          CustomerEntity customerEntity = this.mapToEntity(resultSet);

          return this.toCustomer(customerEntity);
        }
      }

    } catch (SQLException e) {
      logger.error("(getById) error getting customer by id {}: {}", id, e.getMessage(), e);
      throw new QueryException(e);
    }

    return null;
  }

  @Override
  public void update(Customer customer) {
    CustomerEntity customerEntity = this.toEntity(customer);
    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      int idx = 1;
      try (PreparedStatement preparedStatement = connection.prepareStatement(
          CustomerSqlConstant.UPDATE_SQL)) {
        preparedStatement.setString(idx++, customerEntity.getCustomerName());
        preparedStatement.setString(idx++, customerEntity.getPhoneNumber());
        preparedStatement.setString(idx++, customerEntity.getEmail());
        preparedStatement.setString(idx++, customerEntity.getAddress());
        preparedStatement.setString(idx, customerEntity.getCustomerId());

        int row = preparedStatement.executeUpdate();

        super.checkResult(row, customerEntity.getCustomerId());

        connection.commit();
      }
    } catch (SQLException e) {
      super.rollback(connection);

      if (e.getErrorCode() == 1062) {
        logger.error("(update) Duplicate entry");
        throw new DuplicateRecordException("Email " + customerEntity.getEmail() + " is used!");
      }

      logger.error("(update) update customer failed", e);
      throw new QueryException(e);

    } finally {
      super.closeConnection(connection);
    }
  }

  @Override
  public Customer findByEmail(String email) {
    logger.info("(findByEmail) email: {}", email);

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            CustomerSqlConstant.FIND_BY_EMAIL_SQL)) {

      preparedStatement.setString(1, email);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {

          CustomerEntity customerEntity = this.mapToEntity(resultSet);

          return this.toCustomer(customerEntity);
        }
      }

    } catch (SQLException e) {
      logger.error("(findByEmail) error getting customer by email {}: {}", email, e.getMessage(),
          e);
      throw new QueryException(e);
    }

    return null;
  }

  @Override
  public void delete(String id) {
    logger.info("(delete) id: {}", id);

    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      try (PreparedStatement preparedStatement = connection.prepareStatement(
          CustomerSqlConstant.DELETE_SQL)) {
        preparedStatement.setString(1, id);

        int row = preparedStatement.executeUpdate();

        super.checkResult(row, id);
      }

      connection.commit();

    } catch (SQLException e) {
      super.rollback(connection);
      logger.error("(delete) error deleting customer by id {}: {}", id, e.getMessage(), e);
      throw new QueryException(e);

    } catch (BaseException e) {
      super.rollback(connection);
      logger.error("(delete) error deleting customer by id {}: {}", id, e.getMessage(), e);
      throw e;

    } finally {
      super.closeConnection(connection);
    }

    logger.info("(delete) delete successfully for id: {}", id);
  }

  @Override
  public Map<String, Customer> getCustomers() {

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            CustomerSqlConstant.LIST_CUSTOMER_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      return this.toCustomerList(resultSet);

    } catch (SQLException e) {
      logger.error("(getCustomer) error getting customer list", e);
      throw new QueryException(e);
    }
  }
}
