package management.constant.admin;

public class AdminSqlConstant {

  private AdminSqlConstant() {
  }

  public static final String FIND_BY_USERNAME = String.format(
      "SELECT * FROM %s WHERE %s = ?",
      AdminEntityConstant.TABLE_NAME,
      AdminEntityConstant.COLUMN_USERNAME
  );
}