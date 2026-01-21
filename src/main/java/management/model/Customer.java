package management.model;

import java.util.UUID;

public class Customer {

  private final String customerId;
  private String customerName;
  private String phoneNumber;
  private String email;
  private String address;

  public Customer(String customerName, String phoneNumber, String email, String address) {
    this.customerId = UUID.randomUUID().toString();
    this.customerName = customerName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
  }

  public Customer(String customerId, String customerName, String phoneNumber, String email,
      String address) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerId() {
    return customerId;
  }

  @Override
  public String toString() {
    return "Customer [customerId=" + customerId + ", customerName=" + customerName
        + ", phoneNumber="
        + phoneNumber + ", email=" + email + ", address=" + address + "]";
  }
}
