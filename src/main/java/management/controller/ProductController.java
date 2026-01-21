package management.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import management.dto.product.ProductRequest;
import management.dto.product.ProductResponse;
import management.helper.InputHelper;
import management.service.product.ProductService;

public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  public void start() {
    while (true) {
      System.out.println("\n========== PHONE MANAGEMENT ==========");
      System.out.println("1. Display product list");
      System.out.println("2. Add new product");
      System.out.println("3. Update product info");
      System.out.println("4. Delete product");
      System.out.println("5. Search by Brand");
      System.out.println("6. Search by Price Range");
      System.out.println("7. Search by Name (Check Stock)");
      System.out.println("8. Return to Main Menu");
      System.out.println("======================================");

      int choice = InputHelper.getInteger(" Enter choice: ");
      try {
        switch (choice) {
          case 1 -> displayList();
          case 2 -> create();
          case 3 -> update();
          case 4 -> delete();
          case 5 -> searchByBrand();
          case 6 -> searchByPriceRange();
          case 7 -> searchByName();
          case 8 -> {
            return;
          }
          default -> System.err.println(" Invalid choice!");
        }
      } catch (Exception e) {
        System.err.println(" Error: " + e.getMessage());
      }
    }
  }

  private void displayList() {
    printTable(new ArrayList<>(productService.getProducts().values()));
  }

  private void create() {
    System.out.println("\n--- ADD NEW PRODUCT ---");
    ProductRequest req = inputData();
    ProductResponse res = productService.create(req);
    System.out.println(" Created successfully! ID: " + res.getProductId());
  }

  private void update() {
    System.out.println("\n--- UPDATE PRODUCT ---");
    String id = InputHelper.getString("Enter Product ID: ");
    ProductResponse old = productService.getById(id);
    System.out.println(" Current Info: " + old.getProductName() + " | Price: " + old.getPrice());

    System.out.println(">> Enter new info:");
    ProductRequest req = inputData();
    productService.update(id, req);
    System.out.println(" Updated successfully!");
  }

  private void delete() {
    System.out.println("\n--- DELETE PRODUCT ---");
    String id = InputHelper.getString("Enter Product ID: ");
    productService.getById(id);

    if (InputHelper.confirm(" Are you sure you want to delete this product?")) {
      productService.delete(id);
      System.out.println(" Deleted successfully!");
    }
  }

  private void searchByBrand() {
    String brand = InputHelper.getString("Enter Brand keyword: ");
    printTable(productService.searchByBrand(brand));
  }

  private void searchByPriceRange() {
    BigDecimal min = InputHelper.getPrice("Enter Min Price: ");
    BigDecimal max = InputHelper.getPrice("Enter Max Price: ");
    printTable(productService.searchByPriceRange(min, max));
  }

  private void searchByName() {
    String name = InputHelper.getString("Enter Name keyword: ");
    printTable(productService.searchByName(name));
  }

  private ProductRequest inputData() {
    String name = InputHelper.getString("Product Name: ");
    String brand = InputHelper.getString("Brand: ");
    BigDecimal price = InputHelper.getPrice("Price: ");
    int stock = InputHelper.getInteger("Stock Quantity: ");
    return new ProductRequest(name, brand, price, stock);
  }

  private void printTable(List<ProductResponse> list) {
    if (list.isEmpty()) {
      System.out.println(" List is empty.");
      return;
    }
    System.out.println("\n--- Product List ---");
    for (ProductResponse p : list) {
      System.out.println("ID: " + p.getProductId() + " | Name: " + p.getProductName() +
          " | Brand: " + p.getBrand() + " | Price: " + p.getPrice() + " | Stock: " + p.getStock());
    }
  }
}