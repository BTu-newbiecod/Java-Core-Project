package management.helper;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputHelper {

  private static final Scanner scanner = new Scanner(System.in);

  public static int getInteger(String message) {
    while (true) {
      try {
        System.out.println(message);
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);

      } catch (NumberFormatException e) {
        System.err.println("Please enter a valid number");
      }
    }
  }

  public static BigDecimal getPrice(String message) {
    while (true) {
      try {
        System.out.println(message);
        String input = scanner.nextLine().trim();

        BigDecimal price = new BigDecimal(input);

        if (price.compareTo(BigDecimal.ZERO) < 0) {
          System.err.println("price cannot be negative");
          continue;
        }

        return price;
      } catch (NumberFormatException e) {
        System.err.println("please enter a valid price");
      }
    }
  }

  public static String getString(String message) {
    while (true) {
      System.out.println(message);
      String input = scanner.nextLine().trim();

      if (!input.isEmpty()) {
        return input;
      }
      System.err.println("Please do not leave empty string");
    }
  }

  public static boolean confirm(String message) {
    while (true) {
      System.out.print(message + " (Y/N): ");
      String input = scanner.nextLine().trim().toUpperCase();
      if (input.equals("Y")) {
        return true;
      }
      if (input.equals("N")) {
        return false;
      }
      System.err.println("Vui lòng chỉ nhập Y hoặc N.");
    }
  }
}
