package management.service.customer;

import java.util.Map;
import management.dto.customer.CustomerRequest;
import management.dto.customer.CustomerResponse;

public interface CustomerService {

  CustomerResponse create(CustomerRequest customerRequest);

  CustomerResponse getById(String id);

  CustomerResponse update(String id, CustomerRequest customerRequest);

  void delete(String id);

  Map<String, CustomerResponse> getCustomers();
}
