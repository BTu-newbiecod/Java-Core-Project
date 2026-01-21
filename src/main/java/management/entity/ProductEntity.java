package management.entity;

import java.math.BigDecimal;

public class ProductEntity {

  private String ProductId;
  private String ProductName;
  private String brand;
  private BigDecimal price;
  private int stock;

  public ProductEntity(String ProductId, String ProductName, String brand, BigDecimal price,
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

  public void setProductId(String productId) {
    this.ProductId = productId;
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
}
