package management.service.customer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import management.dto.customer.CustomerRequest;
import management.dto.customer.CustomerResponse;
import management.exception.business.InvalidRequestException;
import management.exception.business.ResourceNotFoundException;
import management.model.Customer;
import management.repository.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerServiceImpl implements CustomerService {

  private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  private void validateCustomerRequest(CustomerRequest customerRequest) {
    if (customerRequest.getCustomerName() == null || customerRequest.getCustomerName().isEmpty()) {
      logger.info("(validateCustomerRequest) customerName is null or empty");
      throw new InvalidRequestException("customerName is null or empty");
    }

    String phone = customerRequest.getPhoneNumber();
    if (phone == null || !phone.matches("^0\\d{9}$")) {
      logger.info("(validateCustomerRequest) phoneNumber is null or invalid");
      throw new InvalidRequestException(
          "Phone number has to start with 0 and has exactly 10 characters");
    }

    String email = customerRequest.getEmail();
    if (email == null || !email.matches("^\\w+@(.+)$")) {
      logger.info("(validateCustomerRequest) email is null or invalid");
      throw new InvalidRequestException("email need to be specified");
    }
  }

  @Override
  public CustomerResponse create(CustomerRequest customerRequest) {
    logger.info("(create) customerRequest={}", customerRequest);

    this.validateCustomerRequest(customerRequest);

    if (customerRepository.findByEmail(customerRequest.getEmail()) != null) {
      throw new InvalidRequestException("Email already exists");
    }

    Customer customer = new Customer(
        customerRequest.getCustomerName(),
        customerRequest.getPhoneNumber(),
        customerRequest.getEmail(),
        customerRequest.getAddress()
    );

    customerRepository.save(customer);

    logger.info("(create) created customer={}", customer);
    return new CustomerResponse(customer);
  }

  @Override
  public CustomerResponse getById(String id) {
    logger.info("(getById) id={}", id);

    if (id == null || id.trim().isEmpty()) {
      logger.error("(getById) id is null or empty");
      throw new InvalidRequestException("id is null or empty");
    }

    Customer customer = customerRepository.getById(id);
    if (customer == null) {
      logger.error("(getById) customer not found with id={}", id);
      throw new ResourceNotFoundException("customer not found with id=" + id);
    }

    logger.info("(getById) find by Id successfully, customer={}", customer);
    return new CustomerResponse(customer);
  }

  @Override
  public CustomerResponse update(String id, CustomerRequest customerRequest) {
    logger.info("(update) id={} customerRequest={}", id, customerRequest);

    this.validateCustomerRequest(customerRequest);

    if (customerRepository.findByEmail(customerRequest.getEmail()) != null) {
      throw new InvalidRequestException("Email already exists");
    }

    Customer existingCustomer = customerRepository.getById(id);

    if (existingCustomer == null) {
      logger.error("(update) customer not found with id={}", id);
      throw new ResourceNotFoundException("customer not found with id=" + id);
    }

    Customer customerToUpdate = new Customer(
        existingCustomer.getCustomerId(),
        customerRequest.getCustomerName(),
        customerRequest.getPhoneNumber(),
        customerRequest.getEmail(),
        customerRequest.getAddress()
    );

    customerRepository.update(customerToUpdate);

    logger.info("(update) updated customer={}", customerToUpdate);
    return new CustomerResponse(customerToUpdate);
  }

  @Override
  public void delete(String id) {
    logger.info("(delete) id={}", id);

    if (id == null || id.trim().isEmpty()) {
      logger.error("(delete) id is null or empty");
      throw new InvalidRequestException("id  is null or empty");
    }

    customerRepository.delete(id);
  }

  @Override
  public Map<String, CustomerResponse> getCustomers() {
    Map<String, Customer> customers = customerRepository.getCustomers();

    Map<String, CustomerResponse> customerResponseMap = new HashMap<>();

    for (Map.Entry<String, Customer> entry : customers.entrySet()) {
      customerResponseMap.put(entry.getKey(), new CustomerResponse(entry.getValue()));
    }

    return Collections.unmodifiableMap(customerResponseMap);
  }
}
