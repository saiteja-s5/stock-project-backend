package building.stockapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Sold {

	@Column(name = "sold_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate soldDate;

	@Column(name = "sold_price", nullable = false)
	private Double soldPrice;

	@Override
	public String toString() {
		return soldDate + "," + soldPrice;
	}

}
