package building.stockapp.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("excel-style")
public class ExcelReportStyleProperties {

	Boolean headerFontBold = false;
	Boolean headerFontItalic = false;
	Double headerFontSize = 10.0;
	String headerFontFamily = "Times New Roman";
	int[] headerFontColor = { 0, 0, 0 };
	int[] headerBackgroundColor = { 255, 255, 255 };

	Boolean bodyFontBold = false;
	Boolean bodyFontItalic = false;
	Double bodyFontSize = 10.0;
	String bodyFontFamily = "Times New Roman";
	int[] bodyFontColor = { 0, 0, 0 };
	int[] bodyBackgroundColor = { 255, 255, 255 };

}
