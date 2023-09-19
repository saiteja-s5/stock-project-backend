package building.sma.market.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_name")
public class CompanyName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Long companyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "market", length = 20)
    private Market market;

    @Column(name = "company_symbol", length = 20)
    private String symbol;

    @NotEmpty(message = "{mandatory}")
    @Column(name = "company_name", length = 100)
    private String name;

    public CompanyName(Market market, String symbol, String name) {
	super();
	this.market = market;
	this.symbol = symbol;
	this.name = name;
    }

    @Override
    public String toString() {
	return market + "," + symbol + "," + name;
    }

}