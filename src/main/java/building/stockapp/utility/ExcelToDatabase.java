package building.stockapp.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import building.stockapp.model.CompanyNameDropdown;
import building.stockapp.model.Excel;

public class ExcelToDatabase {

	private Excel excel;

	private static final Logger LOGGER = Logger.getLogger(ExcelToDatabase.class.getName());

	public ExcelToDatabase(Excel excel) {
		this.excel = excel;
	}

	public List<CompanyNameDropdown> readDataFromExcel() {
		List<CompanyNameDropdown> companies = new ArrayList<>();
		File file = new File(excel.getExcelPath());
		LOGGER.log(Level.INFO, "Reading from sheet {0}",
				excel.getExcelPath().substring(excel.getExcelPath().lastIndexOf("\\") + 1));
		try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
			XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
			int rowEndNumber = sheet.getLastRowNum();
			for (int i = excel.getRowStartNumber(); i <= rowEndNumber; i++) {
				XSSFRow row = sheet.getRow(i);
				CompanyNameDropdown company = new CompanyNameDropdown(
						row.getCell(excel.getColumnCharactersToRead()[0] - 65).getStringCellValue(),
						row.getCell(excel.getColumnCharactersToRead()[1] - 65).getStringCellValue());
				companies.add(company);
			}
			LOGGER.log(Level.INFO, "Reading from sheet {0} completed",
					excel.getExcelPath().substring(excel.getExcelPath().lastIndexOf("\\") + 1));

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Reading from Excel Failed");
		}
		return companies;
	}

}
