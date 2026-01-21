package management.dto.customer;

public class CustomerRequest {

  private final String customerName;
  private final String phoneNumber;
  private final String email;
  private final String address;

  public CustomerRequest(String customerName, String phoneNumber, String email,
      String address) {
    this.customerName = customerName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return "CustomerRequest [customerName=" + customerName + ", phoneNumber=" + phoneNumber
        + ", email="
        + email + ", address=" + address + "]";
  }
}
