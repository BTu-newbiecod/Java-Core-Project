package management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {

  private final String invoiceId;
  private final String customerId;
  private LocalDateTime createAt;
  private BigDecimal totalAmount;

  public Invoice(String invoiceId, String customerId, LocalDateTime createAt,
      BigDecimal totalAmount) {
    this.invoiceId = invoiceId;
    this.customerId = customerId;
    this.createAt = createAt;
    this.totalAmount = totalAmount;
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Override
  public String toString() {
    return "Invoice [invoiceId=" + invoiceId + ", customerId=" + customerId + ", createAt="
        + createAt + ", totalAmount=" + totalAmount + "]";
  }
}
