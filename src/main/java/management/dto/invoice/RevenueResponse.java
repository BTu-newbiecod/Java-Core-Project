package management.dto.invoice;

import java.math.BigDecimal;

public class RevenueResponse {

  private final String timePeriod;
  private final BigDecimal totalRevenue;

  public RevenueResponse(String timePeriod, BigDecimal totalRevenue) {
    this.timePeriod = timePeriod;
    this.totalRevenue = totalRevenue;
  }

  public String getTimePeriod() {
    return timePeriod;
  }

  public BigDecimal getTotalRevenue() {
    return totalRevenue;
  }
}
