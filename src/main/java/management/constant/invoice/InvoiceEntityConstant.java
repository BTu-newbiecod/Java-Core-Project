package management.constant.invoice;

public class InvoiceEntityConstant {

  private InvoiceEntityConstant() {
  }

  public static final String TABLE_NAME = "Invoice";
  public static final String COLUMN_INVOICE_ID = "id";
  public static final String COLUMN_CUSTOMER_ID = "customer_id";
  public static final String COLUMN_CREATE_AT = "created_at";
  public static final String COLUMN_TOTAL_AMOUNT = "total_amount";

  public static final String REVENUE_TIME_PERIOD = "time_period";
  public static final String REVENUE_TOTAL_REVENUE = "total_revenue";
}
