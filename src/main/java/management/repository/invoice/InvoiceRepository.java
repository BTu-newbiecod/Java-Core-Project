package management.repository.invoice;

import java.sql.Connection;
import java.util.List;
import management.dto.invoice.InvoiceResponse;
import management.dto.invoice.RevenueResponse;
import management.model.Invoice;
import management.model.InvoiceDetail;

public interface InvoiceRepository {

  void save(Connection connection, Invoice invoice);

  void save(Connection connection, InvoiceDetail invoiceDetail);

  List<InvoiceResponse> findAll();

  List<InvoiceResponse> searchByCustomerName(String name);

  List<InvoiceResponse> searchByDate(String dateStr);

  List<RevenueResponse> getRevenueByDay();

  List<RevenueResponse> getRevenueByMonth();

  List<RevenueResponse> getRevenueByYear();
}
