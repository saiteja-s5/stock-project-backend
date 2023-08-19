package building.sma.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPostRequestDTO {

    @NotEmpty(message = "{mandatory}")
    private String stockName;

    @NotNull(message = "{mandatory}")
    @PastOrPresent(message = "{future}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate investmentDate;

    @NotNull(message = "{mandatory}")
    @Min(value = 1, message = "{numeric-minimum}")
    private Integer quantity;

    @NotNull(message = "{mandatory}")
    @Digits(integer = 10, fraction = 2, message = "{decimal-places}")
    @DecimalMin(value = "0.01", message = "{decimal-minimum-2-digits}")
    private BigDecimal buyPrice;

    @Override
    public String toString() {
	return stockName + "," + investmentDate + "," + quantity + "," + buyPrice;
    }

}
