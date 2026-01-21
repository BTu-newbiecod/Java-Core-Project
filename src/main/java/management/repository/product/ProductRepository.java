package management.repository.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import management.model.Product;

public interface ProductRepository {

  void save(Product product);

  Product getById(String id);

  void update(Product product);

  void delete(String id);

  Map<String, Product> getProducts();

  List<Product> findByBrand(String brand);

  List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

  List<Product> findByName(String name);

  void updateStock (Connection connection, String productId, int quantity);
}
