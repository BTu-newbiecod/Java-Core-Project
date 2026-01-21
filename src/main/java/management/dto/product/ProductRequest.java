package management.dto.product;

import java.math.BigDecimal;

public class ProductRequest {

  private final String productName;
  private final String brand;
  private final BigDecimal price;
  private final int stock;

  public ProductRequest(String productName, String brand, BigDecimal price,
      int stock) {
    this.productName = productName;
    this.brand = brand;
    this.price = price;
    this.stock = stock;
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
    return "ProductRequest [productName=" + productName + ", brand=" + brand + ", price=" + price
        + ", stock=" + stock + "]";
  }
}
