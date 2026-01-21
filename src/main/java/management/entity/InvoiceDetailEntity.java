package management.entity;

import java.math.BigDecimal;

public class InvoiceDetailEntity {

  private String invoiceDetailId;
  private String invoiceId;
  private String productId;
  private int quantity;
  private BigDecimal unitPrice;

  public InvoiceDetailEntity(String invoiceDetailId, String invoiceId, String productId, int quantity, BigDecimal unitPrice) {
    this.invoiceDetailId = invoiceDetailId;
    this.invoiceId = invoiceId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public String getInvoiceDetailId() {
    return invoiceDetailId;
  }

  public void setInvoiceDetailId(String invoiceDetailId) {
    this.invoiceDetailId = invoiceDetailId;
  }

  public String getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(String invoiceId) {
    this.invoiceId = invoiceId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
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
}
