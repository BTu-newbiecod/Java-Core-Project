package management.controller;

import java.util.ArrayList;
import java.util.List;
import management.dto.invoice.InvoiceDetailRequest;
import management.dto.invoice.InvoiceRequest;
import management.dto.invoice.InvoiceResponse;
import management.dto.invoice.RevenueResponse;
import management.helper.InputHelper;
import management.service.customer.CustomerService;
import management.service.invoice.InvoiceService;
import management.service.product.ProductService;

public class InvoiceController {

  private final InvoiceService invoiceService;
  private final CustomerService customerService;
  private final ProductService productService;

  public InvoiceController(InvoiceService invoiceService, CustomerService customerService,
      ProductService productService) {
    this.invoiceService = invoiceService;
    this.customerService = customerService;
    this.productService = productService;
  }

  public void start() {
    while (true) {
      System.out.println("\n========== INVOICE & STATISTICS ==========");
      System.out.println("1. Display All Invoices");
      System.out.println("2. Create New Invoice (Sales)");
      System.out.println("3. Search Invoices");
      System.out.println("4. Revenue Statistics");
      System.out.println("5. Return to Main Menu");
      System.out.println("==========================================");

      int choice = InputHelper.getInteger(" Enter choice: ");
      try {
        switch (choice) {
          case 1 -> displayList();
          case 2 -> createInvoice();
          case 3 -> searchMenu();
          case 4 -> statisticMenu();
          case 5 -> {
            return;
          }
          default -> System.err.println(" Invalid choice!");
        }
      } catch (Exception e) {
        System.err.println(" Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  private void createInvoice() {
    System.out.println("\n--- CREATE NEW INVOICE ---");

    String customerId;
    while (true) {
      customerId = InputHelper.getString("Enter Customer ID: ");
      try {
        customerService.getById(customerId);
        break;
      } catch (Exception e) {
        System.err.println(" Customer not found. Please try again.");
      }
    }

    List<InvoiceDetailRequest> cart = new ArrayList<>();
    boolean shopping = true;
    while (shopping) {
      System.out.println("\n>> Add Item to Cart:");
      String pid = InputHelper.getString("   - Product ID: ");
      try {
        var p = productService.getById(pid);
        System.out.println(
            "      Info: " + p.getProductName() + " | Price: " + p.getPrice() + " | Stock: "
                + p.getStock());

        int qty = InputHelper.getInteger("   - Quantity: ");

        if (qty > p.getStock()) {
          System.err.println("      Error: Not enough stock! Available: " + p.getStock());
        } else {
          cart.add(new InvoiceDetailRequest(pid, qty));
          System.out.println("      Added to cart.");
        }

      } catch (Exception e) {
        System.err.println("      Product not found.");
      }
      shopping = InputHelper.confirm("    Add more items?");
    }

    if (cart.isEmpty()) {
      System.out.println(" Cart is empty. Process cancelled.");
      return;
    }

    InvoiceRequest req = new InvoiceRequest(customerId, cart);
    invoiceService.create(req);
    System.out.println(" Invoice created successfully!");
  }

  private void displayList() {
    printTable(invoiceService.getAll());
  }

  private void searchMenu() {
    System.out.println("\n--- SEARCH INVOICES ---");
    System.out.println("1. Search by Customer Name");
    System.out.println("2. Search by Date (yyyy-MM-dd)");
    System.out.println("3. Back");

    int choice = InputHelper.getInteger("Select: ");
    switch (choice) {
      case 1 -> {
        String name = InputHelper.getString("Enter Customer Name: ");
        printTable(invoiceService.searchByCustomerName(name));
      }
      case 2 -> {
        String date = InputHelper.getString("Enter Date (yyyy-MM-dd): ");

        if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
          System.err.println(" Invalid date format! Please use yyyy-MM-dd (e.g., 2025-01-30)");
          return;
        }

        try {
          printTable(invoiceService.searchByDate(date));
        } catch (Exception e) {
          System.err.println(" Search failed: " + e.getMessage());
        }
      }
      case 3 -> {
      }
    }
  }

  private void statisticMenu() {
    System.out.println("\n--- REVENUE STATISTICS ---");
    System.out.println("1. By Day");
    System.out.println("2. By Month");
    System.out.println("3. By Year");
    System.out.println("4. Back");

    int choice = InputHelper.getInteger("Select: ");
    List<RevenueResponse> list = null;
    switch (choice) {
      case 1 -> list = invoiceService.getRevenueByDay();
      case 2 -> list = invoiceService.getRevenueByMonth();
      case 3 -> list = invoiceService.getRevenueByYear();
      case 4 -> {
        return;
      }
    }

    if (list != null && !list.isEmpty()) {
      System.out.println("\n--- Revenue Results ---");
      for (RevenueResponse r : list) {
        System.out.printf("Time: %-15s | Revenue: %,.2f%n", r.getTimePeriod(), r.getTotalRevenue());
      }
    } else {
      System.out.println(" No data available.");
    }
  }

  private void printTable(List<InvoiceResponse> list) {
    if (list.isEmpty()) {
      System.out.println(" List is empty.");
      return;
    }
    System.out.println("\n--- Invoice List ---");
    for (InvoiceResponse i : list) {
      System.out.println("ID: " + i.getInvoiceId() + " | Customer: " + i.getCustomerName() +
          " | Date: " + i.getCreatedAt().toLocalDate() + " | Total: " + i.getTotalAmount());
    }
  }
}