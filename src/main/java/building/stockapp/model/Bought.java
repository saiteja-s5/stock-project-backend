package building.stockapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Bought {

	@NotNull(message = "Bought Date field is Mandatory")
	@PastOrPresent(message = "Bought Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "bought_date", nullable = false)
	private LocalDate boughtDate;

	@NotNull(message = "Bought Price field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Bought Price accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Bought Price should be atleast 0.01")
	@Column(name = "bought_price", nullable = false)
	private BigDecimal boughtPrice;

	@Override
	public String toString() {
		return boughtDate + "," + boughtPrice;
	}

}
