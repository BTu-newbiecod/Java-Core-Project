import management.controller.AdminController;
import management.controller.CustomerController;
import management.controller.InvoiceController;
import management.controller.MainController;
import management.controller.ProductController;
import management.repository.admin.AdminRepositoryImpl;
import management.repository.customer.impl.CustomerRepositoryImpl;
import management.repository.invoice.impl.InvoiceRepositoryImpl;
import management.repository.product.impl.ProductRepositoryImpl;
import management.service.admin.AdminServiceImpl;
import management.service.customer.CustomerServiceImpl;
import management.service.invoice.InvoiceServiceImpl;
import management.service.product.ProductServiceImpl;

public class Main {

  public static void main(String[] args) {
    try {
      System.out.println(" Starting Application...");

      var productRepo = new ProductRepositoryImpl();
      var customerRepo = new CustomerRepositoryImpl();
      var invoiceRepo = new InvoiceRepositoryImpl();
      var adminRepo = new AdminRepositoryImpl();

      var productService = new ProductServiceImpl(productRepo);
      var customerService = new CustomerServiceImpl(customerRepo);
      var adminService = new AdminServiceImpl(adminRepo);

      var invoiceService = new InvoiceServiceImpl(productRepo, invoiceRepo);

      var adminController = new AdminController(adminService);
      var productController = new ProductController(productService);
      var customerController = new CustomerController(customerService);
      var invoiceController = new InvoiceController(invoiceService, customerService,
          productService);

      var mainController = new MainController(
          adminController,
          productController,
          customerController,
          invoiceController
      );

      mainController.startApplication();

    } catch (Exception e) {
      System.err.println(" CRITICAL ERROR: Application failed to start.");
      e.printStackTrace();
    }
  }
}