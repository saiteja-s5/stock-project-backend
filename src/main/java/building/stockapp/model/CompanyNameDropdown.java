package building.stockapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_name_dropdown")
public class CompanyNameDropdown {

	@Id
	@Column(name = "company_symbol", length = 20, nullable = false)
	private String companySymbol;

	@Column(name = "company_name", length = 100, nullable = false)
	private String companyName;

}
