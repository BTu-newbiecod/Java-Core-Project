package management.model;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceDetail {

  private final String invoiceDetailId;
  private final String invoiceId;
  private final String productId;
  private int quantity;
  private BigDecimal unitPrice;

  public InvoiceDetail(String invoiceId, String productId, int quantity,
      BigDecimal unitPrice) {
    this.invoiceDetailId = UUID.randomUUID().toString();
    this.invoiceId = invoiceId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public InvoiceDetail(String invoiceDetailId, String invoiceId, String productId, int quantity,
      BigDecimal unitPrice) {
    this.invoiceDetailId = invoiceDetailId;
    this.invoiceId = invoiceId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public String getInvoiceDetailId() {
    return invoiceDetailId;
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Override
  public String toString() {
    return "InvoiceDetail [invoiceDetailId=" + invoiceDetailId + ", invoiceId=" + invoiceId
        + ", productId=" + productId + ", quantity=" + quantity + ", unitPrice=" + unitPrice
        + "]";
  }
}
