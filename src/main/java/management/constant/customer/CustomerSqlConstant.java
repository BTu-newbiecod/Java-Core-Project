package management.constant.customer;

public class CustomerSqlConstant {

  private CustomerSqlConstant() {
  }

  public static final String SAVE_SQL =
      "INSERT INTO " + String.format("%s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)",
          CustomerEntityConstant.TABLE_NAME,
          CustomerEntityConstant.COLUMN_CUSTOMER_ID, CustomerEntityConstant.COLUMN_CUSTOMER_NAME,
          CustomerEntityConstant.COLUMN_PHONE_NUMBER, CustomerEntityConstant.COLUMN_EMAIL,
          CustomerEntityConstant.COLUMN_ADDRESS);

  public static final String GET_BY_ID_SQL =
      "SELECT " + String.format("%s, %s, %s, %s, %s FROM %s WHERE %s = ?",
          CustomerEntityConstant.COLUMN_CUSTOMER_ID,
          CustomerEntityConstant.COLUMN_CUSTOMER_NAME, CustomerEntityConstant.COLUMN_PHONE_NUMBER,
          CustomerEntityConstant.COLUMN_EMAIL,
          CustomerEntityConstant.COLUMN_ADDRESS, CustomerEntityConstant.TABLE_NAME,
          CustomerEntityConstant.COLUMN_CUSTOMER_ID);

  public static final String FIND_BY_EMAIL_SQL =
      "SELECT " + String.format("%s, %s, %s, %s, %s FROM %s WHERE %s = ?",
          CustomerEntityConstant.COLUMN_CUSTOMER_ID,
          CustomerEntityConstant.COLUMN_CUSTOMER_NAME, CustomerEntityConstant.COLUMN_PHONE_NUMBER,
          CustomerEntityConstant.COLUMN_EMAIL,
          CustomerEntityConstant.COLUMN_ADDRESS, CustomerEntityConstant.TABLE_NAME,
          CustomerEntityConstant.COLUMN_EMAIL);

  public static final String UPDATE_SQL =
      "UPDATE " + String.format("%s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
          CustomerEntityConstant.TABLE_NAME,
          CustomerEntityConstant.COLUMN_CUSTOMER_NAME,
          CustomerEntityConstant.COLUMN_PHONE_NUMBER,
          CustomerEntityConstant.COLUMN_EMAIL,
          CustomerEntityConstant.COLUMN_ADDRESS,
          CustomerEntityConstant.COLUMN_CUSTOMER_ID);

  public static final String DELETE_SQL =
      "DELETE FROM " + String.format("%s WHERE %s = ?", CustomerEntityConstant.TABLE_NAME,
          CustomerEntityConstant.COLUMN_CUSTOMER_ID);

  public static final String LIST_CUSTOMER_SQL =
      "SELECT " + String.format("%s, %s, %s, %s, %s from %s",
          CustomerEntityConstant.COLUMN_CUSTOMER_ID, CustomerEntityConstant.COLUMN_CUSTOMER_NAME,
          CustomerEntityConstant.COLUMN_PHONE_NUMBER, CustomerEntityConstant.COLUMN_EMAIL,
          CustomerEntityConstant.COLUMN_ADDRESS, CustomerEntityConstant.TABLE_NAME);
}
