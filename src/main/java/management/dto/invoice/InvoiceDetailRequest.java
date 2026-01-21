package management.dto.invoice;

public class InvoiceDetailRequest {

  private final String productId;
  private final int quantity;

  public InvoiceDetailRequest(String productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  @Override
  public String toString() {
    return "InvoiceDetailRequest{" + "productId=" + productId + ", quantity=" + quantity + '}';
  }
}
