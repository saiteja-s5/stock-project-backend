package building.stockapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import building.stockapp.dto.FundTableRowDTO;
import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.exception.ResourceNotDownloadedException;
import building.stockapp.model.EmailProperties;
import building.stockapp.model.ExcelReportDataProperties;
import building.stockapp.repository.FundRepository;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.EmailService;
import building.stockapp.service.ExcelReportService;
import building.stockapp.utility.ExcelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class ExcelReportServiceImpl implements ExcelReportService {

	private final ApplicationContext applicationContext;

	private final ExcelReportDataProperties excelDataProperties;

	private final ExcelUtil excelUtil;

	private final StockRepository stockRepository;

	private final FundRepository fundRepository;

	private final EmailService emailService;

	private final EmailProperties emailProperties;

	@Override
	public byte[] generateExcelForStockRecords() {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			XSSFSheet sheet = workbook.createSheet(excelDataProperties.getStockSheetName());
			XSSFRow headerRow = sheet.createRow(0);
			String[] stockSheetHeaders = excelDataProperties.getStockSheetHeaders();
			int headerColNumber = 0;
			for (String header : stockSheetHeaders) {
				XSSFCell cell = headerRow.createCell(headerColNumber++);
				cell.setCellValue(header);
				cell.setCellStyle(excelUtil.getStyleForHeaders(workbook));
			}
			List<StockTableRowDTO> stocks = stockRepository.getAllStocksForTable();
			int rowNumber = 1;
			for (StockTableRowDTO stock : stocks) {
				XSSFRow row = sheet.createRow(rowNumber++);
				String[] entityProperties = stock.toString().split(",");
				int columnNumber = 0;
				for (String property : entityProperties) {
					int tempColNumber = columnNumber++;
					XSSFCell cell = row.createCell(tempColNumber);
					fillCell(cell, property);
					cell.setCellStyle(excelUtil.getStyleForBody(workbook));
					sheet.autoSizeColumn(tempColNumber);
				}
			}
			workbook.write(baos);
			byte[] excelData = baos.toByteArray();
			emailService.sendReportEmail(emailProperties.getFrom(), emailProperties.getTo(), "Stock Report",
					"Please find the attachment for the Stock Report", "StockReport-" + LocalDate.now() + ".xlsx",
					excelData);
			return excelData;
		} catch (Exception e) {
			log.error("Unable to generate stock excel, an exception occured", e);
			throw new ResourceNotDownloadedException(e.getMessage());
		}
	}

	@Override
	public byte[] generateExcelForFundRecords() {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			XSSFSheet sheet = workbook.createSheet(excelDataProperties.getFundSheetName());
			XSSFRow headerRow = sheet.createRow(0);
			String[] fundSheetHeaders = excelDataProperties.getFundSheetHeaders();
			int headerColNumber = 0;
			for (String header : fundSheetHeaders) {
				XSSFCell cell = headerRow.createCell(headerColNumber++);
				cell.setCellValue(header);
				cell.setCellStyle(excelUtil.getStyleForHeaders(workbook));
			}
			List<FundTableRowDTO> funds = fundRepository.getAllFundsForTable();
			int rowNumber = 1;
			for (FundTableRowDTO fund : funds) {
				XSSFRow row = sheet.createRow(rowNumber++);
				String[] entityProperties = fund.toString().split(",");
				int columnNumber = 0;
				for (String property : entityProperties) {
					int tempColNumber = columnNumber++;
					XSSFCell cell = row.createCell(tempColNumber);
					fillCell(cell, property);
					cell.setCellStyle(excelUtil.getStyleForBody(workbook));
					sheet.autoSizeColumn(tempColNumber);
				}
			}
			workbook.write(baos);
			byte[] excelData = baos.toByteArray();
			emailService.sendReportEmail(emailProperties.getFrom(), emailProperties.getTo(), "Fund Report",
					"Please find the attachment for the Fund Report", "FundReport-" + LocalDate.now() + ".xlsx",
					excelData);
			return excelData;
		} catch (Exception e) {
			log.error("Unable to generate fund excel, an exception occured", e);
			throw new ResourceNotDownloadedException(e.getMessage());
		}
	}

	@Override
	public byte[] generateExcelForAllRecords() {
		log.info("Excel file generation started");
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			for (String className : excelDataProperties.getAllReportClasses()) {
				XSSFSheet sheet = workbook.createSheet(className);
				log.debug(String.format("Writing to Sheet:%s", className));
				Class<?> reportClass = Class.forName("building.stockapp.repository." + className + "Repository");
				Method findAllMethod = reportClass.getMethod("findAll");
				Object reportClassObj = applicationContext.getBean(reportClass);
				List<?> resultList = (List<?>) findAllMethod.invoke(reportClassObj);
				int rowNumber = 1;
				for (Object listLine : resultList) {
					XSSFRow row = sheet.createRow(rowNumber++);
					String[] entityProperties = listLine.toString().split(",");
					int columnNumber = 0;
					for (String property : entityProperties) {
						int tempColNumber = columnNumber++;
						XSSFCell cell = row.createCell(tempColNumber);
						fillCell(cell, property);
						cell.setCellStyle(excelUtil.getStyleForBody(workbook));
						sheet.autoSizeColumn(tempColNumber);
					}
				}
			}
			workbook.write(baos);
			stopWatch.stop();
			log.info(String.format("Excel file generation finished in %d ms", stopWatch.getTotalTimeMillis()));
			byte[] excelData = baos.toByteArray();
			emailService.sendReportEmail(emailProperties.getFrom(), emailProperties.getTo(), "All Data Report",
					"Please find the attachment for the All Data Report", "Report-" + LocalDate.now() + ".xlsx",
					excelData);
			return excelData;
		} catch (Exception e) {
			log.error("Unable to generate excel, an exception occured", e);
			throw new ResourceNotDownloadedException(e.getMessage());
		}
	}

	private void fillCell(XSSFCell cell, String property) {
		try {
			Double.valueOf(property);
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(Double.valueOf(property));
		} catch (NumberFormatException nfe) {
			cell.setCellValue(property);
		}
	}

}
