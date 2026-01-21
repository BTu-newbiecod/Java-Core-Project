package management.service.invoice;

import java.util.List;
import management.dto.invoice.InvoiceRequest;
import management.dto.invoice.InvoiceResponse;
import management.dto.invoice.RevenueResponse;

public interface InvoiceService {

  void create(InvoiceRequest invoiceRequest);

  List<InvoiceResponse> getAll();

  List<InvoiceResponse> searchByCustomerName(String customerName);

  List<InvoiceResponse> searchByDate(String dateStr);

  List<RevenueResponse> getRevenueByDay();

  List<RevenueResponse> getRevenueByMonth();

  List<RevenueResponse> getRevenueByYear();
}

