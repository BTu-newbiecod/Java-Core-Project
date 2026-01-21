package management.constant.product;

import static management.constant.product.ProductEntityConstant.COLUMN_PRODUCT_BRAND;
import static management.constant.product.ProductEntityConstant.COLUMN_PRODUCT_ID;
import static management.constant.product.ProductEntityConstant.COLUMN_PRODUCT_NAME;
import static management.constant.product.ProductEntityConstant.COLUMN_PRODUCT_PRICE;
import static management.constant.product.ProductEntityConstant.COLUMN_PRODUCT_STOCK;
import static management.constant.product.ProductEntityConstant.TABLE_NAME;

public class ProductSqlConstant {

  private ProductSqlConstant() {
  }

  public static final String SAVE_SQL =
      "INSERT INTO " + String.format("%s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)", TABLE_NAME,
          COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_BRAND, COLUMN_PRODUCT_PRICE,
          COLUMN_PRODUCT_STOCK);

  public static final String GET_BY_ID_SQL =
      "SELECT " + String.format("%s, %s, %s, %s, %s FROM %s WHERE %s = ?", COLUMN_PRODUCT_ID,
          COLUMN_PRODUCT_NAME,
          COLUMN_PRODUCT_BRAND, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_STOCK, TABLE_NAME,
          COLUMN_PRODUCT_ID);

  public static final String UPDATE_SQL =
      "UPDATE " + String.format("%s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", TABLE_NAME,
          COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_BRAND, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_STOCK,
          COLUMN_PRODUCT_ID);

  public static final String DELETE_SQL =
      "DELETE FROM " + String.format("%s WHERE %s = ?", TABLE_NAME, COLUMN_PRODUCT_ID);

  public static final String LIST_PRODUCT_SQL =
      "SELECT " + String.format("%s, %s, %s, %s, %s from %s",
          COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_BRAND, COLUMN_PRODUCT_PRICE,
          COLUMN_PRODUCT_STOCK, TABLE_NAME);

  public static final String SEARCH_BY_BRAND_SQL =
      "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(" + COLUMN_PRODUCT_BRAND + ") LIKE ?";

  public static final String SEARCH_BY_PRICE_SQL =
      "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_PRICE + " >= ? AND "
          + COLUMN_PRODUCT_PRICE + " <= ?";

  public static final String SEARCH_BY_NAME_SQL =
      "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(" + COLUMN_PRODUCT_NAME + ") LIKE ?";

  public static final String UPDATE_STOCK_SQL = String.format(
      "UPDATE %s SET %s = %s - ? WHERE %s = ? AND %s >= ?",
      TABLE_NAME, COLUMN_PRODUCT_STOCK, COLUMN_PRODUCT_STOCK,
      COLUMN_PRODUCT_ID, COLUMN_PRODUCT_STOCK
  );
}
