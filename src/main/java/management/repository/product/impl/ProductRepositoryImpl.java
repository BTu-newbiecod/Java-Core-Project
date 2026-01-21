package management.repository.product.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import management.config.DBConnection;
import management.constant.product.ProductEntityConstant;
import management.constant.product.ProductSqlConstant;
import management.entity.ProductEntity;
import management.exception.base.BaseException;
import management.exception.database.QueryException;
import management.model.Product;
import management.repository.BaseJdbcRepository;
import management.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRepositoryImpl extends BaseJdbcRepository implements ProductRepository {

  private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

  private ProductEntity toEntity(Product product) {
    return new ProductEntity(product.getProductId(),
        product.getProductName(),
        product.getBrand(), product.getPrice(), product.getStock());
  }

  private Product toProduct(ProductEntity productEntity) {
    return new Product(
        productEntity.getProductId(),
        productEntity.getProductName(),
        productEntity.getBrand(),
        productEntity.getPrice(),
        productEntity.getStock()
    );
  }

  private ProductEntity mapToEntity(ResultSet resultSet) throws SQLException {
    return new ProductEntity(
        resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_ID),
        resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_NAME),
        resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_BRAND),
        resultSet.getBigDecimal(ProductEntityConstant.COLUMN_PRODUCT_PRICE),
        resultSet.getInt(ProductEntityConstant.COLUMN_PRODUCT_STOCK)
    );
  }

  @Override
  public void save(Product product) {
    ProductEntity productEntity = this.toEntity(product);

    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      int idx = 1;
      try (PreparedStatement preparedStatement = connection.prepareStatement(
          ProductSqlConstant.SAVE_SQL)) {
        preparedStatement.setString(idx++, productEntity.getProductId());
        preparedStatement.setString(idx++, productEntity.getProductName());
        preparedStatement.setString(idx++, productEntity.getBrand());
        preparedStatement.setBigDecimal(idx++, productEntity.getPrice());
        preparedStatement.setInt(idx, productEntity.getStock());

        preparedStatement.executeUpdate();
      }

      connection.commit();

    } catch (SQLException e) {
      super.rollback(connection);
      logger.error("(save) saving product error with id {}: {}", productEntity.getProductId(),
          e.getMessage(), e);
      throw new QueryException(e);

    } finally {
      super.closeConnection(connection);
    }
  }

  @Override
  public Product getById(String id) {
    logger.info("(getById) id: {}", id);

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            ProductSqlConstant.GET_BY_ID_SQL)) {

      preparedStatement.setString(1, id);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {

          ProductEntity productEntity = this.mapToEntity(resultSet);

          return this.toProduct(productEntity);
        }
      }

    } catch (SQLException e) {
      logger.error("(getById) error getting product by id {}: {}", id, e.getMessage(), e);
      throw new QueryException(e);
    }

    return null;
  }

  @Override
  public void update(Product product) {
    ProductEntity productEntity = this.toEntity(product);
    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      int idx = 1;
      try (PreparedStatement preparedStatement = connection.prepareStatement(
          ProductSqlConstant.UPDATE_SQL)) {
        preparedStatement.setString(idx++, productEntity.getProductName());
        preparedStatement.setString(idx++, productEntity.getBrand());
        preparedStatement.setBigDecimal(idx++, productEntity.getPrice());
        preparedStatement.setInt(idx++, productEntity.getStock());
        preparedStatement.setString(idx, productEntity.getProductId());

        int row = preparedStatement.executeUpdate();

        super.checkResult(row, productEntity.getProductId());

        connection.commit();
      }
    } catch (SQLException | BaseException e) {
      super.rollback(connection);
      logger.error("(update) update product failed", e);
      throw new QueryException(e);

    } finally {
      super.closeConnection(connection);
    }
  }

  @Override
  public void delete(String id) {
    logger.info("(delete) id: {}", id);

    Connection connection = null;

    try {
      connection = DBConnection.getMysqlConnection();
      connection.setAutoCommit(false);

      try (PreparedStatement preparedStatement = connection.prepareStatement(
          ProductSqlConstant.DELETE_SQL)) {
        preparedStatement.setString(1, id);

        int row = preparedStatement.executeUpdate();

        super.checkResult(row, id);
      }

      connection.commit();

    } catch (SQLException e) {
      super.rollback(connection);
      logger.error("(delete) error deleting product by id {}: {}", id, e.getMessage(), e);
      throw new QueryException(e);

    } catch (BaseException e) {
      super.rollback(connection);
      logger.error("(delete) error deleting product by id {}: {}", id, e.getMessage(), e);
      throw e;

    } finally {
      super.closeConnection(connection);
    }

    logger.info("(delete) delete successfully for id: {}", id);
  }

  @Override
  public Map<String, Product> getProducts() {

    try (Connection connection = DBConnection.getMysqlConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            ProductSqlConstant.LIST_PRODUCT_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      return this.toProductList(resultSet);

    } catch (SQLException e) {
      logger.error("(getProducts) error getting product list", e);
      throw new QueryException(e);
    }
  }

  private Map<String, Product> toProductList(ResultSet resultSet) throws SQLException {
    Map<String, Product> products = new HashMap<>();

    while (resultSet.next()) {
      String productId = resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_ID);
      String productName = resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_NAME);
      String brand = resultSet.getString(ProductEntityConstant.COLUMN_PRODUCT_BRAND);
      BigDecimal price = resultSet.getBigDecimal(ProductEntityConstant.COLUMN_PRODUCT_PRICE);
      int stock = resultSet.getInt(ProductEntityConstant.COLUMN_PRODUCT_STOCK);

      Product product = new Product(productId, productName, brand, price, stock);
      products.put(productId, product);
    }
    return Collections.unmodifiableMap(products);
  }

  @Override
  public List<Product> findByBrand(String brand) {
    List<Product> result = new ArrayList<>();
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement pstmt = conn.prepareStatement(ProductSqlConstant.SEARCH_BY_BRAND_SQL)) {

      pstmt.setString(1, "%" + brand.toLowerCase() + "%");

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          ProductEntity entity = this.mapToEntity(rs);
          result.add(this.toProduct(entity));
        }
      }
    } catch (SQLException e) {
      throw new QueryException(e);
    }
    return result;
  }

  @Override
  public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
    List<Product> result = new ArrayList<>();
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement pstmt = conn.prepareStatement(ProductSqlConstant.SEARCH_BY_PRICE_SQL)) {

      pstmt.setBigDecimal(1, min);
      pstmt.setBigDecimal(2, max);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          ProductEntity entity = this.mapToEntity(rs);
          result.add(this.toProduct(entity));
        }
      }
    } catch (SQLException e) {
      throw new QueryException(e);
    }
    return result;
  }

  @Override
  public List<Product> findByName(String keyword) {
    List<Product> result = new ArrayList<>();
    try (Connection conn = DBConnection.getMysqlConnection();
        PreparedStatement pstmt = conn.prepareStatement(ProductSqlConstant.SEARCH_BY_NAME_SQL)) {

      pstmt.setString(1, "%" + keyword.toLowerCase() + "%");

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          ProductEntity entity = this.mapToEntity(rs);
          result.add(this.toProduct(entity));
        }
      }
    } catch (SQLException e) {
      throw new QueryException(e);
    }
    return result;
  }

  @Override
  public void updateStock(Connection connection, String productId, int quantity) {
    int idx = 1;

    try (PreparedStatement preparedStatement = connection.prepareStatement(
        ProductSqlConstant.UPDATE_STOCK_SQL)) {

      preparedStatement.setInt(idx++, quantity);
      preparedStatement.setString(idx++, productId);
      preparedStatement.setInt(idx, quantity);

      int row = preparedStatement.executeUpdate();
      super.checkResult(row, productId);

    } catch (SQLException e) {
      logger.error("(updateStock) error updating product {} with quantity {}", productId, quantity,
          e);
      throw new QueryException(e);

    } catch (BaseException e) {
      logger.error("(updateStock) error updating product {} with quantity {}", productId, quantity,
          e);
      throw e;
    }
  }
}
