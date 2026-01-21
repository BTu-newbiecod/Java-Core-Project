package management.controller;

import java.util.ArrayList;
import management.dto.customer.CustomerRequest;
import management.dto.customer.CustomerResponse;
import management.helper.InputHelper;
import management.service.customer.CustomerService;

public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  public void start() {
    while (true) {
      System.out.println("\n========== CUSTOMER MANAGEMENT ==========");
      System.out.println("1. Display customer list");
      System.out.println("2. Add new customer");
      System.out.println("3. Update customer info");
      System.out.println("4. Delete customer");
      System.out.println("5. Return to Main Menu");
      System.out.println("=========================================");

      int choice = InputHelper.getInteger(" Enter choice: ");
      try {
        switch (choice) {
          case 1 -> displayList();
          case 2 -> create();
          case 3 -> update();
          case 4 -> delete();
          case 5 -> {
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
    var list = new ArrayList<>(customerService.getCustomers().values());
    if (list.isEmpty()) {
      System.out.println(" List is empty.");
      return;
    }
    System.out.println("\n--- Customer List ---");
    for (CustomerResponse c : list) {
      System.out.println("ID: " + c.getCustomerId() + " | Name: " + c.getCustomerName() +
          " | Phone: " + c.getPhoneNumber() + " | Email: " + c.getEmail());
    }
  }

  private void create() {
    System.out.println("\n--- ADD NEW CUSTOMER ---");
    CustomerRequest req = inputData();
    CustomerResponse res = customerService.create(req);
    System.out.println(" Created successfully! ID: " + res.getCustomerId());
  }

  private void update() {
    System.out.println("\n--- UPDATE CUSTOMER ---");
    String id = InputHelper.getString("Enter Customer ID: ");
    customerService.getById(id);
    System.out.println(">> Enter new info:");
    CustomerRequest req = inputData();
    customerService.update(id, req);
    System.out.println(" Updated successfully!");
  }

  private void delete() {
    System.out.println("\n--- DELETE CUSTOMER ---");
    String id = InputHelper.getString("Enter Customer ID: ");
    customerService.getById(id);
    if (InputHelper.confirm(" Are you sure you want to delete this customer?")) {
      customerService.delete(id);
      System.out.println(" Deleted successfully!");
    }
  }

  private CustomerRequest inputData() {
    String name = InputHelper.getString("Full Name: ");
    String phone = InputHelper.getString("Phone Number: ");
    String email = InputHelper.getString("Email: ");
    String address = InputHelper.getString("Address: ");
    return new CustomerRequest(name, phone, email, address);
  }
}