package management.controller;

import management.helper.InputHelper;

public class MainController {

  private final AdminController adminController;
  private final ProductController productController;
  private final CustomerController customerController;
  private final InvoiceController invoiceController;

  public MainController(AdminController adminController, ProductController productController,
      CustomerController customerController, InvoiceController invoiceController) {
    this.adminController = adminController;
    this.productController = productController;
    this.customerController = customerController;
    this.invoiceController = invoiceController;
  } 

  public void startApplication() {
    if (adminController.login()) {
      showMainMenu();
    }
  }

  private void showMainMenu() {
    while (true) {
      System.out.println("\n========== STORE MANAGEMENT SYSTEM ==========");
      System.out.println("1. Phone Management");
      System.out.println("2. Customer Management");
      System.out.println("3. Invoice & Statistics");
      System.out.println("4. Logout");
      System.out.println("=============================================");

      int choice = InputHelper.getInteger(" Enter choice: ");
      switch (choice) {
        case 1 -> productController.start();
        case 2 -> customerController.start();
        case 3 -> invoiceController.start();
        case 4 -> {
          System.out.println(" Logged out successfully!");
          return;
        }
        default -> System.err.println(" Invalid choice!");
      }
    }
  }
}