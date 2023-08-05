package building.stockapp.utility;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import building.stockapp.model.ExcelReportStyleProperties;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ExcelUtil {

	private final ExcelReportStyleProperties excelStyleProperties;

	public XSSFCellStyle getStyleForHeaders(XSSFWorkbook workbook) {

		// Style for headers
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();

		// Header Font Styling
		XSSFFont headerFont = workbook.createFont();
		headerFont.setBold(excelStyleProperties.getHeaderFontBold());
		headerFont.setFontHeight(excelStyleProperties.getHeaderFontSize());
		headerFont.setFontName(excelStyleProperties.getHeaderFontFamily());
		int[] headerColors = excelStyleProperties.getHeaderFontColor();
		XSSFColor fontColor = new XSSFColor(new java.awt.Color(headerColors[0], headerColors[1], headerColors[2]),
				new DefaultIndexedColorMap());
		headerFont.setColor(fontColor);
		headerCellStyle.setFont(headerFont);

		// Header Background Color Styling
		int[] backgroundColors = excelStyleProperties.getHeaderBackgroundColor();
		XSSFColor backgroundColor = new XSSFColor(
				new java.awt.Color(backgroundColors[0], backgroundColors[1], backgroundColors[2]),
				new DefaultIndexedColorMap());
		headerCellStyle.setFillForegroundColor(backgroundColor);

		// Misc Styling
		headerCellStyle.setBorderTop(BorderStyle.THICK);
		headerCellStyle.setBorderRight(BorderStyle.THICK);
		headerCellStyle.setBorderBottom(BorderStyle.THICK);
		headerCellStyle.setBorderLeft(BorderStyle.THICK);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return headerCellStyle;
	}

	public XSSFCellStyle getStyleForBody(XSSFWorkbook workbook) {

		// Style for body
		XSSFCellStyle bodyCellStyle = workbook.createCellStyle();

		// Body Font Styling
		XSSFFont bodyFont = workbook.createFont();
		bodyFont.setBold(excelStyleProperties.getBodyFontBold());
		bodyFont.setFontHeight(excelStyleProperties.getBodyFontSize());
		bodyFont.setFontName(excelStyleProperties.getBodyFontFamily());
		int[] bodyColors = excelStyleProperties.getBodyFontColor();
		XSSFColor fontColor = new XSSFColor(new java.awt.Color(bodyColors[0], bodyColors[1], bodyColors[2]),
				new DefaultIndexedColorMap());
		bodyFont.setColor(fontColor);
		bodyCellStyle.setFont(bodyFont);

		// Body Background Color Styling
		int[] backgroundColors = excelStyleProperties.getBodyBackgroundColor();
		XSSFColor backgroundColor = new XSSFColor(
				new java.awt.Color(backgroundColors[0], backgroundColors[1], backgroundColors[2]),
				new DefaultIndexedColorMap());
		bodyCellStyle.setFillForegroundColor(backgroundColor);

		// Misc Styling
		bodyCellStyle.setBorderTop(BorderStyle.MEDIUM);
		bodyCellStyle.setBorderRight(BorderStyle.MEDIUM);
		bodyCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		bodyCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		bodyCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return bodyCellStyle;
	}

}
