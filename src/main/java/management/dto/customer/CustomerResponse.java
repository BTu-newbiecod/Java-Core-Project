package management.dto.customer;

import management.model.Customer;

public class CustomerResponse {

  private final String customerId;
  private final String customerName;
  private final String phoneNumber;
  private final String email;
  private final String address;

  public CustomerResponse(Customer customer) {
    this.customerId = customer.getCustomerId();
    this.customerName = customer.getCustomerName();
    this.phoneNumber = customer.getPhoneNumber();
    this.email = customer.getEmail();
    this.address = customer.getAddress();
  }

  public String getCustomerId() {
    return customerId;
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
    return "CustomerResponse [customerId=" + customerId + ", customerName=" + customerName
        + ", phoneNumber=" + phoneNumber + ", email=" + email + ", address=" + address + "]";
  }
}
