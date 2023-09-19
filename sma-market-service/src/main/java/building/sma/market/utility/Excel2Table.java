package building.sma.market.utility;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import building.sma.market.model.CompanyName;
import building.sma.market.model.Market;
import building.sma.market.repository.CompanyNameRepository;

@Component
public class Excel2Table {

    @Autowired
    private CompanyNameRepository companyNameRepository;

    private String excelPath = "D:\\StockMarketData\\BSE Companies Data.xlsx";
    private String sheetName = "BSE Companies";
    private Market market = Market.BSE;

    public void fillCompanyDetailsInTable() {
	try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(excelPath)))) {
	    XSSFSheet sheet = workbook.getSheet(sheetName);
	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		companyNameRepository.save(new CompanyName(market, sheet.getRow(i).getCell(1).toString(),
			sheet.getRow(i).getCell(2).toString()));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
