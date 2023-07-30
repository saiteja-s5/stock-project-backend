package building.stockapp.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("excel")
public class ExcelReportProperties {

	private String[] reportClasses;
	// private String[] stockSheetHeaders;

}
