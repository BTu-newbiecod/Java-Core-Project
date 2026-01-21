package management.dto.invoice;

import java.util.List;

public class InvoiceRequest {

  private final String CustomerId;
  private final List<InvoiceDetailRequest> invoiceDetails;

  public InvoiceRequest(String customerId, List<InvoiceDetailRequest> invoiceDetails) {
    this.CustomerId = customerId;
    this.invoiceDetails = invoiceDetails;
  }

  public String getCustomerId() {
    return CustomerId;
  }

  public List<InvoiceDetailRequest> getInvoiceDetails() {
    return invoiceDetails;
  }

  @Override
  public String toString() {
    return "InvoiceRequest{" + "CustomerId=" + CustomerId + ", invoiceDetails=" + invoiceDetails
        + '}';
  }
}
