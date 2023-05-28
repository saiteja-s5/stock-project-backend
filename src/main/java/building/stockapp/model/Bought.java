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
public class Bought {

	@Column(name = "bought_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate boughtDate;

	@Column(name = "bought_price", nullable = false)
	private Double boughtPrice;

	@Override
	public String toString() {
		return boughtDate + "," + boughtPrice;
	}

}
