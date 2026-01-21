package management.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private final String ProductId;
  private String ProductName;
  private String brand;
  private BigDecimal price;
  private int stock;

  public Product(String ProductName, String brand, BigDecimal price,
      int stock) {
    this.ProductId = UUID.randomUUID().toString();
    this.ProductName = ProductName;
    this.brand = brand;
    this.price = price;
    this.stock = stock;
  }

  public Product(String ProductId, String ProductName, String brand, BigDecimal price,
      int stock) {
    this.ProductId = ProductId;
    this.ProductName = ProductName;
    this.brand = brand;
    this.price = price;
    this.stock = stock;
  }

  public String getProductId() {
    return ProductId;
  }

  public String getProductName() {
    return ProductName;
  }

  public void setProductName(String productName) {
    this.ProductName = productName;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  @Override
  public String toString() {
    return "Product [ProductId=" + ProductId + ", ProductName=" + ProductName + ", brand="
        + brand + ", price=" + price + ", stock=" + stock + "]";
  }
}
