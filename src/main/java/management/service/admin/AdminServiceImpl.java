package management.service.admin;

import management.model.Admin;
import management.repository.admin.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServiceImpl implements AdminService {

  private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
  private final AdminRepository adminRepository;

  public AdminServiceImpl(AdminRepository adminRepository) {
    this.adminRepository = adminRepository;
  }

  @Override
  public boolean authenticate(String username, String password) {
    Admin admin = adminRepository.findByUsername(username);

    if (admin == null) {
      logger.warn("Login failed: Username '{}' not found", username);
      return false;
    }

    if (admin.getPassword().equals(password)) {
      logger.info("Login successful for user: {}", username);
      return true;
    } else {
      logger.warn("Login failed: Wrong password for user '{}'", username);
      return false;
    }
  }
}