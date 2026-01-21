package management.service.admin;

public interface AdminService {
  boolean authenticate(String username, String password);
}