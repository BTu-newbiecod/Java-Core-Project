package management.model;

public class Admin {

  private String id;
  private String username;
  private String password;

  public Admin(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}