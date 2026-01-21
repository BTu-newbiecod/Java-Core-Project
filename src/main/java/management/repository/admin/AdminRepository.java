package management.repository.admin;

import management.model.Admin;

public interface AdminRepository {
  Admin findByUsername(String username);
}