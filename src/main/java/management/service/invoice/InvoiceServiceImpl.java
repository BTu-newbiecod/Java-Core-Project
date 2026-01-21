package management.service.invoice;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import management.config.DBConnection;
import management.dto.invoice.InvoiceDetailRequest;
import management.dto.invoice.InvoiceRequest;
import management.dto.invoice.InvoiceResponse;
import management.dto.invoice.RevenueResponse;
import management.exception.base.BusinessException;
import management.exception.business.InvalidRequestException;
import management.exception.business.ResourceNotFoundException;
import management.model.Invoice;
import management.model.InvoiceDetail;
import management.model.Product;
import management.repository.invoice.InvoiceRepository;
import management.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceServiceImpl implements InvoiceService {

  private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

  private final ProductRepository productRepository;
  private final InvoiceRepository invoiceRepository;

  public InvoiceServiceImpl(ProductRepository productRepository,
      InvoiceRepository invoiceRepository) {
    this.productRepository = productRepository;
    this.invoiceRepository = invoiceRepository;
  }

  @Override
  public void create(InvoiceRequest invoiceRequest) {
    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      String invoiceId = UUID.randomUUID().toString();
      BigDecimal totalAmount = BigDecimal.ZERO;
      List<InvoiceDetail> invoiceDetailList = new ArrayList<>();

      for (InvoiceDetailRequest item : invoiceRequest.getInvoiceDetails()) {
        Product product = productRepository.getById(item.getProductId());

        if (product == null) {
          logger.error("(create) product" + item.getProductId() + " not found");
          throw new ResourceNotFoundException(
              "product" + item.getProductId() + " not found");
        }

        if (product.getStock() < item.getQuantity()) {
          logger.error("(create) product stock not enough");
          throw new BusinessException(
              "product stock " + item.getProductId() + "not enough");
        }

        BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        totalAmount = totalAmount.add(amount);

        invoiceDetailList.add(new InvoiceDetail(
            invoiceId,
            item.getProductId(),
            item.getQuantity(),
            product.getPrice()
        ));
      }

      Invoice invoice = new Invoice(
          invoiceId,
          invoiceRequest.getCustomerId(),
          LocalDateTime.now(),
          totalAmount
      );

      invoiceRepository.save(connection, invoice);

      for (InvoiceDetail item : invoiceDetailList) {
        invoiceRepository.save(connection, item);

        productRepository.updateStock(connection, item.getProductId(), item.getQuantity());
      }

      connection.commit();
      logger.info("(create) successfully invoice " + invoiceId);

    } catch (Exception e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (Exception rollbackEx) {
          logger.error("(create) connection rollback failed");
        }
      }
      throw new BusinessException("create invoice failed" + e.getMessage());

    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (Exception closeEx) {
          logger.error("(create) connection close failed");
        }
      }
    }
  }

  @Override
  public List<InvoiceResponse> getAll() {
    return invoiceRepository.findAll();
  }

  @Override
  public List<InvoiceResponse> searchByCustomerName(String customerName) {
    logger.info("(searchByCustomerName) searching by customer name: {}", customerName);

    if (customerName == null || customerName.trim().isEmpty()) {
      logger.error("(searchByCustomerName) customerName is null or empty");
      throw new InvalidRequestException("customerName is null or empty");
    }

    return invoiceRepository.searchByCustomerName(customerName);
  }

  @Override
  public List<InvoiceResponse> searchByDate(String dateStr) {
    logger.info("(searchByDate) searching by date: {}", dateStr);

    if (dateStr == null || dateStr.trim().isEmpty()) {
      logger.error("(searchByDate) dateStr is null or empty");
      throw new InvalidRequestException("dateTime can not be null or empty");
    }

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
          .withResolverStyle(ResolverStyle.STRICT);

      LocalDate.parse(dateStr, formatter);

    } catch (DateTimeParseException e) {
      logger.error("(searchByDate) invalid date format: {}", dateStr);
      throw new InvalidRequestException(
          "invalid date format!, please check your date format: yyyy-MM-dd (Example: 2023-12-31)");
    }

    return invoiceRepository.searchByDate(dateStr);
  }

  @Override
  public List<RevenueResponse> getRevenueByDay() {
    return invoiceRepository.getRevenueByDay();
  }

  @Override
  public List<RevenueResponse> getRevenueByMonth() {
    return invoiceRepository.getRevenueByMonth();
  }

  @Override
  public List<RevenueResponse> getRevenueByYear() {
    return invoiceRepository.getRevenueByYear();
  }
}
