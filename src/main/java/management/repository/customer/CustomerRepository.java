package management.repository.customer;

import java.util.Map;
import management.model.Customer;

public interface CustomerRepository {

  void save(Customer customer);

  Customer getById(String id);

  void update(Customer customer);

  Customer findByEmail(String email);

  void delete(String id);

  Map<String, Customer> getCustomers();
}
