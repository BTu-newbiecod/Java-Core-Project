package management.service.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import management.dto.product.ProductRequest;
import management.dto.product.ProductResponse;
import management.exception.business.InvalidRequestException;
import management.exception.business.ResourceNotFoundException;
import management.model.Product;
import management.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponse create(ProductRequest productRequest) {
    logger.info("(create) productRequest={}", productRequest);

    this.validateProductRequest(productRequest);

    Product product = new Product(
        productRequest.getProductName(),
        productRequest.getBrand(),
        productRequest.getPrice(),
        productRequest.getStock());

    productRepository.save(product);

    logger.info("(create) created product={}", product);
    return new ProductResponse(product);
  }

  private void validateProductRequest(ProductRequest productRequest) {
    if (productRequest == null) {
      logger.error("(validateProductRequest) request is null");
      throw new InvalidRequestException("Request is null!");
    }

    if (productRequest.getProductName() == null || productRequest.getProductName().trim()
        .isEmpty()) {
      logger.error("(validateProductRequest) productName is null or empty");
      throw new InvalidRequestException("productName is null or empty!");
    }

    if (productRequest.getBrand() == null || productRequest.getBrand().trim().isEmpty()) {
      logger.error("(validateProductRequest) productBrand is null or empty");
      throw new InvalidRequestException("productBrand is null or empty!");
    }

    if (productRequest.getPrice() == null || productRequest.getPrice().doubleValue() <= 0) {
      logger.error("(validateProductRequest) productPrice can not null and can not negative");
      throw new InvalidRequestException("productPrice can not null and can not negative!");
    }

    if (productRequest.getStock() < 0) {
      logger.error("(validateProductRequest) productStock can not negative");
      throw new InvalidRequestException("productStock can not negative!");
    }
  }

  @Override
  public ProductResponse getById(String id) {
    logger.info("(getById) id={}", id);

    if (id == null || id.trim().isEmpty()) {
      logger.error("(getById) id is null or empty");
      throw new InvalidRequestException("id is null or empty!");
    }

    Product product = productRepository.getById(id);
    if (product == null) {
      logger.error("(getById) product not found with id={}", id);
      throw new ResourceNotFoundException("product not found with id=" + id);
    }

    return new ProductResponse(product);
  }

  @Override
  public ProductResponse update(String id, ProductRequest productRequest) {
    logger.info("(update) id={} productRequest={}", id, productRequest);

    this.validateProductRequest(productRequest);

    Product existingProduct = productRepository.getById(id);

    if (existingProduct == null) {
      logger.error("(update) product not found with id={}", id);
      throw new ResourceNotFoundException("product not found with id=" + id);
    }

    Product productToUpdate = new Product(
        existingProduct.getProductId(),
        productRequest.getProductName(),
        productRequest.getBrand(),
        productRequest.getPrice(),
        productRequest.getStock()
    );

    productRepository.update(productToUpdate);

    logger.info("(update) updated product={}", productToUpdate);
    return new ProductResponse(productToUpdate);
  }

  @Override
  public void delete(String id) {
    logger.info("(delete) id={}", id);

    if (id == null || id.trim().isEmpty()) {
      logger.error("(delete) id is null or empty");
      throw new InvalidRequestException("id is null or empty!");
    }

    productRepository.delete(id);
  }

  @Override
  public Map<String, ProductResponse> getProducts() {
    Map<String, Product> products = productRepository.getProducts();

    Map<String, ProductResponse> productResponseMap = new HashMap<>();

    for (Map.Entry<String, Product> entry : products.entrySet()) {
      ProductResponse productResponse = new ProductResponse(entry.getValue());
      productResponseMap.put(entry.getKey(), productResponse);
    }

    return productResponseMap;
  }

  @Override
  public List<ProductResponse> searchByBrand(String brand) {
    logger.info("(searchByBrand) brand={}", brand);

    if (brand == null || brand.trim().isEmpty()) {
      logger.error("(searchByBrand) brand is null or empty");
      throw new InvalidRequestException("brand is null or empty!");
    }

    List<Product> products = productRepository.findByBrand(brand);

    List<ProductResponse> productResponseList = this.convertToProductResponseList(products);

    logger.info("(searchByBrand) productResponseList={}", productResponseList);
    return Collections.unmodifiableList(productResponseList);
  }

  @Override
  public List<ProductResponse> searchByPriceRange(BigDecimal min, BigDecimal max) {
    logger.info("(searchByPriceRange) min={}, max={}", min, max);

    this.validatePriceRange(min, max);

    List<Product> products = productRepository.findByPriceRange(min, max);

    List<ProductResponse> productResponseList = this.convertToProductResponseList(products);

    logger.info("(searchByPriceRange) productResponseList={}", productResponseList);
    return Collections.unmodifiableList(productResponseList);
  }

  private void validatePriceRange(BigDecimal min, BigDecimal max) {
    if (min == null || max == null) {
      logger.error("(validatePriceRange) min or max is null");
      throw new InvalidRequestException("min or max is null");
    }

    if (min.compareTo(max) > 0) {
      logger.error("(validatePriceRange) min is greater than max");
      throw new InvalidRequestException("min is greater than max");
    }

    if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(BigDecimal.ZERO) < 0) {
      logger.error("(validatePriceRange) price can not negative");
      throw new InvalidRequestException("price can not negative");
    }
  }

  @Override
  public List<ProductResponse> searchByName(String keyword) {
    logger.info("(searchByName) keyword={}", keyword);

    if (keyword == null || keyword.trim().isEmpty()) {
      logger.error("(searchByName) keyword is null or empty");
      throw new InvalidRequestException("keyword is null or empty!");
    }

    List<Product> products = productRepository.findByName(keyword);

    List<ProductResponse> productResponseList = this.convertToProductResponseList(products);

    logger.info("(searchByName) productResponseList={}", productResponseList);
    return Collections.unmodifiableList(productResponseList);
  }

  private List<ProductResponse> convertToProductResponseList(List<Product> products) {
    List<ProductResponse> productResponseList = new ArrayList<>();

    for (Product product : products) {
      ProductResponse productResponse = new ProductResponse(product);
      productResponseList.add(productResponse);
    }

    return productResponseList;
  }
}
