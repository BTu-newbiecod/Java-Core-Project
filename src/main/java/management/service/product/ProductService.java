package management.service.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import management.dto.product.ProductRequest;
import management.dto.product.ProductResponse;

public interface ProductService {

  ProductResponse create(ProductRequest productRequest);

  ProductResponse getById(String id);

  ProductResponse update(String id, ProductRequest productRequest);

  void delete(String id);

  Map<String, ProductResponse> getProducts();

  List<ProductResponse> searchByBrand(String brand);

  List<ProductResponse> searchByPriceRange(BigDecimal min, BigDecimal max);

  List<ProductResponse> searchByName(String keyword);
}
