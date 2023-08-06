package building.stockapp.service;

public interface ExcelReportService {

	byte[] generateExcelForAllRecords();

	byte[] generateExcelForStockRecords();

	byte[] generateExcelForFundRecords();

}
