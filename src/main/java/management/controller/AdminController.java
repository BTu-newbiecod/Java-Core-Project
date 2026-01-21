package management.controller;

import management.helper.InputHelper;
import management.service.admin.AdminService;

public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  public boolean login() {
    System.out.println("\n========== SYSTEM LOGIN ==========");
    while (true) {
      String username = InputHelper.getString("Username: ");
      String password = InputHelper.getString("Password: ");

      if (adminService.authenticate(username, password)) {
        System.out.println(" Login successful! Welcome " + username);
        return true;
      } else {
        System.err.println(" Invalid username or password.");
        if (!InputHelper.confirm("Do you want to try again?")) {
          System.out.println(" Goodbye!");
          System.exit(0);
        }
      }
    }
  }
}