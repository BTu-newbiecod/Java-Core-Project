package management.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceEntity {

  private String invoiceId;
  private String customerId;
  private LocalDateTime createAt;
  private BigDecimal totalAmount;

  public InvoiceEntity(String invoiceId, String customerId, LocalDateTime createAt,
      BigDecimal totalAmount) {
    this.invoiceId = invoiceId;
    this.customerId = customerId;
    this.createAt = createAt;
    this.totalAmount = totalAmount;
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(String invoiceId) {
    this.invoiceId = invoiceId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
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
}
