package management.repository.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import management.config.DBConnection;
import management.constant.customer.CustomerEntityConstant;
import management.constant.invoice.InvoiceEntityConstant;
import management.constant.invoice.InvoiceSqlConstant;
import management.dto.invoice.InvoiceResponse;
import management.dto.invoice.RevenueResponse;
import management.entity.InvoiceDetailEntity;
import management.entity.InvoiceEntity;
import management.exception.database.QueryException;
import management.model.Invoice;
import management.model.InvoiceDetail;
import management.repository.invoice.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceRepositoryImpl implements InvoiceRepository {

  private static final Logger logger = LoggerFactory.getLogger(InvoiceRepositoryImpl.class);

  private InvoiceEntity toEntity(Invoice invoice) {
    return new InvoiceEntity(
        invoice.getInvoiceId(),
        invoice.getCustomerId(),
        invoice.getCreateAt(),
        invoice.getTotalAmount()
    );
  }

  private InvoiceDetailEntity toEntity(InvoiceDetail invoiceDetail) {
    return new InvoiceDetailEntity(
        invoiceDetail.getInvoiceDetailId(),
        invoiceDetail.getInvoiceId(),
        invoiceDetail.getProductId(),
        invoiceDetail.getQuantity(),
        invoiceDetail.getUnitPrice()
    );
  }

  private List<InvoiceResponse> mapResultSetToInvoiceResponse(ResultSet resultSet)
      throws SQLException {
    List<InvoiceResponse> invoiceResponseList = new ArrayList<>();

    while (resultSet.next()) {
      InvoiceResponse invoiceResponse = new InvoiceResponse(
          resultSet.getString(InvoiceEntityConstant.COLUMN_INVOICE_ID),
          resultSet.getString(CustomerEntityConstant.COLUMN_CUSTOMER_NAME),
          resultSet.getTimestamp(InvoiceEntityConstant.COLUMN_CREATE_AT).toLocalDateTime(),
          resultSet.getBigDecimal(InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT)
      );
      invoiceResponseList.add(invoiceResponse);
    }

    return Collections.unmodifiableList(invoiceResponseList);
  }

  @Override
  public void save(Connection connection, Invoice invoice) {
    InvoiceEntity invoiceEntity = this.toEntity(invoice);

    int idx = 1;
    try (PreparedStatement preparedStatement = connection.prepareStatement(
        InvoiceSqlConstant.SAVE_INVOICE_SQL)) {
      preparedStatement.setString(idx++, invoiceEntity.getInvoiceId());
      preparedStatement.setString(idx++, invoiceEntity.getCustomerId());
      preparedStatement.setTimestamp(idx++, Timestamp.valueOf(invoiceEntity.getCreateAt()));
      preparedStatement.setBigDecimal(idx, invoiceEntity.getTotalAmount());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      logger.error("(save) save invoice error", e);
      throw new QueryException(e);
    }
  }

  @Override
  public void save(Connection connection, InvoiceDetail invoiceDetail) {
    InvoiceDetailEntity invoiceDetailEntity = this.toEntity(invoiceDetail);

    int idx = 1;
    try (PreparedStatement preparedStatement = connection.prepareStatement(
        InvoiceSqlConstant.SAVE_INVOICE_DETAIL_SQL)) {
      preparedStatement.setString(idx++, invoiceDetailEntity.getInvoiceDetailId());
      preparedStatement.setString(idx++, invoiceDetailEntity.getInvoiceId());
      preparedStatement.setString(idx++, invoiceDetailEntity.getProductId());
      preparedStatement.setInt(idx++, invoiceDetailEntity.getQuantity());
      preparedStatement.setBigDecimal(idx, invoiceDetailEntity.getUnitPrice());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      logger.error("(save) save invoice detail error", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<InvoiceResponse> findAll() {

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            InvoiceSqlConstant.FIND_ALL_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      return this.mapResultSetToInvoiceResponse(resultSet);

    } catch (Exception e) {
      logger.error("(findAll) get all invoice error", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<InvoiceResponse> searchByCustomerName(String name) {
    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            InvoiceSqlConstant.SEARCH_BY_CUSTOMER_NAME_SQL)) {

      preparedStatement.setString(1, "%" + name.toLowerCase() + "%");

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return this.mapResultSetToInvoiceResponse(resultSet);
      }
    } catch (SQLException e) {
      logger.error("(searchByCustomerName) search by customer name error", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<InvoiceResponse> searchByDate(String dateStr) {
    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            InvoiceSqlConstant.SEARCH_BY_DATE_SQL)) {

      preparedStatement.setString(1, dateStr);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return this.mapResultSetToInvoiceResponse(resultSet);
      }
    } catch (SQLException e) {
      logger.error("(searchByDate) search by Date error", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<RevenueResponse> getRevenueByDay() {
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement ps = conn.prepareStatement(InvoiceSqlConstant.STATISTIC_BY_DAY_SQL);
        ResultSet rs = ps.executeQuery()) {
      return mapToRevenueList(rs);
    } catch (SQLException e) {
      logger.error("(getRevenueByDay) error get revenue by day", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<RevenueResponse> getRevenueByMonth() {
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement ps = conn.prepareStatement(InvoiceSqlConstant.STATISTIC_BY_MONTH_SQL);
        ResultSet rs = ps.executeQuery()) {
      return mapToRevenueList(rs);
    } catch (SQLException e) {
      logger.error("(getRevenueByMonth) error get revenue by month", e);
      throw new QueryException(e);
    }
  }

  @Override
  public List<RevenueResponse> getRevenueByYear() {
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement ps = conn.prepareStatement(InvoiceSqlConstant.STATISTIC_BY_YEAR_SQL);
        ResultSet rs = ps.executeQuery()) {
      return mapToRevenueList(rs);
    } catch (SQLException e) {
      logger.error("(getRevenueByYear) error get revenue by year", e);
      throw new QueryException(e);
    }
  }

  private List<RevenueResponse> mapToRevenueList(ResultSet rs) throws SQLException {
    List<RevenueResponse> list = new ArrayList<>();
    while (rs.next()) {
      list.add(new RevenueResponse(
          rs.getString(InvoiceEntityConstant.REVENUE_TIME_PERIOD),
          rs.getBigDecimal(InvoiceEntityConstant.REVENUE_TOTAL_REVENUE)
      ));
    }
    return list;
  }
}
