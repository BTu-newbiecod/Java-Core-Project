package management.dto.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceResponse {

  private final String invoiceId;
  private final String customerName;
  private final LocalDateTime createdAt;
  private final BigDecimal totalAmount;

  public InvoiceResponse(String invoiceId, String customerName, LocalDateTime createdAt,
      BigDecimal totalAmount) {
    this.invoiceId = invoiceId;
    this.customerName = customerName;
    this.createdAt = createdAt;
    this.totalAmount = totalAmount;
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
}
