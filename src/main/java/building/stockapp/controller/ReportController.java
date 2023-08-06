package building.stockapp.controller;

import java.time.LocalDate;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.service.ExcelReportService;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ExcelReportService excelReportService;

	@GetMapping("/all")
	public ResponseEntity<Resource> getAllRecords() {
		String fileName = "Report-" + LocalDate.now() + ".xlsx";
		byte[] excelData = excelReportService.generateExcelForAllRecords();
		ByteArrayResource resource = new ByteArrayResource(excelData);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.header("Access-Control-Expose-Headers", "Content-Disposition").contentLength(excelData.length)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}

	@GetMapping("/stocks")
	public ResponseEntity<Resource> getAllStockRecords() {
		String fileName = "StockReport-" + LocalDate.now() + ".xlsx";
		byte[] excelData = excelReportService.generateExcelForStockRecords();
		ByteArrayResource resource = new ByteArrayResource(excelData);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.header("Access-Control-Expose-Headers", "Content-Disposition").contentLength(excelData.length)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}

}
