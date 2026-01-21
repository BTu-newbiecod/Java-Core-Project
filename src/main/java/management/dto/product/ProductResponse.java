package management.dto.product;

import java.math.BigDecimal;
import management.model.Product;

public class ProductResponse {

  private final String productId;
  private final String productName;
  private final String brand;
  private final BigDecimal price;
  private final int stock;

  public ProductResponse(Product product) {
    this.productId = product.getProductId();
    this.productName = product.getProductName();
    this.brand = product.getBrand();
    this.price = product.getPrice();
    this.stock = product.getStock();
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getBrand() {
    return brand;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }

  @Override
  public String toString() {
    return "ProductResponse [productId=" + productId + ", productName=" + productName + ", brand="
        + brand + ", price=" + price + ", stock=" + stock + "]";
  }
}
