package building.stockapp.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("excel-data")
public class ExcelReportDataProperties {

	private String[] allReportClasses;

	private String stockSheetName;
	private String[] stockSheetHeaders;

	private String mutualFundSheetName;
	private String[] mutualFundSheetHeaders;

	private String fundSheetName;
	private String[] fundSheetHeaders;

	private String dividendSheetName;
	private String[] dividendSheetHeaders;

	private String profitLossSheetName;
	private String[] profitLossSheetHeaders;

}
