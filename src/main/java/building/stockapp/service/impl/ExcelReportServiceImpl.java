package building.stockapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import building.stockapp.exception.ResourceNotDownloadedException;
import building.stockapp.model.ExcelReportProperties;
import building.stockapp.service.ExcelReportService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class ExcelReportServiceImpl implements ExcelReportService {

	private final ApplicationContext applicationContext;

	private final ExcelReportProperties excelProperties;

	@Override
	public byte[] generateExcelForAllRecords() {
		log.info("Excel file generation started");
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			for (String className : excelProperties.getReportClasses()) {
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
						XSSFCell cell = row.createCell(columnNumber++);
						fillCell(cell, property);
					}
				}
			}
			workbook.write(baos);
			stopWatch.stop();
			log.info(String.format("Excel file generation finished in %d ms", stopWatch.getTotalTimeMillis()));
			return baos.toByteArray();
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
