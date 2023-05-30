package building.stockapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Excel {

	private String excelPath;
	private String sheetName;
	private int rowStartNumber;
	private char[] columnCharactersToRead;

}
