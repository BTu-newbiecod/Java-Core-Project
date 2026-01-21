package management.constant.invoice;

import management.constant.customer.CustomerEntityConstant;

public class InvoiceSqlConstant {

  private InvoiceSqlConstant() {
  }

  public static final String SAVE_INVOICE_SQL = String.format(
      "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_INVOICE_ID,
      InvoiceEntityConstant.COLUMN_CUSTOMER_ID,
      InvoiceEntityConstant.COLUMN_CREATE_AT, InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT);

  public static final String SAVE_INVOICE_DETAIL_SQL = String.format(
      "INSERT INTO %s  (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
      InvoiceDetailEntityConstant.TABLE_NAME,
      InvoiceDetailEntityConstant.COLUMN_INVOICE_DETAIL_ID,
      InvoiceDetailEntityConstant.COLUMN_INVOICE_ID,
      InvoiceDetailEntityConstant.COLUMN_PRODUCT_ID,
      InvoiceDetailEntityConstant.COLUMN_QUANTITY,
      InvoiceDetailEntityConstant.COLUMN_UNIT_PRICE
  );

  private static final String BASE_SELECT_JOIN_SQL = String.format(
      "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s JOIN %s ON %s.%s = %s.%s",
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_INVOICE_ID,
      CustomerEntityConstant.TABLE_NAME, CustomerEntityConstant.COLUMN_CUSTOMER_NAME,
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_CREATE_AT,
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT,
      InvoiceEntityConstant.TABLE_NAME,
      CustomerEntityConstant.TABLE_NAME,
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_CUSTOMER_ID,
      CustomerEntityConstant.TABLE_NAME, CustomerEntityConstant.COLUMN_CUSTOMER_ID
  );

  public static final String FIND_ALL_SQL = BASE_SELECT_JOIN_SQL;

  public static final String SEARCH_BY_CUSTOMER_NAME_SQL = BASE_SELECT_JOIN_SQL +
      " WHERE LOWER(" + CustomerEntityConstant.TABLE_NAME + "."
      + CustomerEntityConstant.COLUMN_CUSTOMER_NAME + ") LIKE ?";

  public static final String SEARCH_BY_DATE_SQL = BASE_SELECT_JOIN_SQL +
      " WHERE DATE(" + InvoiceEntityConstant.TABLE_NAME + "."
      + InvoiceEntityConstant.COLUMN_CREATE_AT + ") = ?";

  public static final String STATISTIC_BY_DAY_SQL = String.format(
      "SELECT DATE(%s) as time_period, SUM(%s) as total_revenue FROM %s GROUP BY DATE(%s) ORDER BY time_period DESC",
      InvoiceEntityConstant.COLUMN_CREATE_AT, InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT,
      InvoiceEntityConstant.TABLE_NAME, InvoiceEntityConstant.COLUMN_CREATE_AT
  );

  public static final String STATISTIC_BY_MONTH_SQL = String.format(
      "SELECT DATE_FORMAT(%s, '%%Y-%%m') as time_period, SUM(%s) as total_revenue FROM %s GROUP BY time_period ORDER BY time_period DESC",
      InvoiceEntityConstant.COLUMN_CREATE_AT, InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT,
      InvoiceEntityConstant.TABLE_NAME
  );

  public static final String STATISTIC_BY_YEAR_SQL = String.format(
      "SELECT YEAR(%s) as time_period, SUM(%s) as total_revenue FROM %s GROUP BY time_period ORDER BY time_period DESC",
      InvoiceEntityConstant.COLUMN_CREATE_AT, InvoiceEntityConstant.COLUMN_TOTAL_AMOUNT,
      InvoiceEntityConstant.TABLE_NAME
  );
}
