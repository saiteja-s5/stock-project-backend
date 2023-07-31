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
public class Sold {

	@NotNull(message = "Sold Date field is Mandatory")
	@PastOrPresent(message = "Sold Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "sold_date", nullable = false)
	private LocalDate soldDate;

	@NotNull(message = "Sold Price field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Sold Price accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Sold Price should be atleast 0.01")
	@Column(name = "sold_price", nullable = false)
	private BigDecimal soldPrice;

	@Override
	public String toString() {
		return soldDate + "," + soldPrice;
	}

}
