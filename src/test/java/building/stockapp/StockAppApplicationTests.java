package building.stockapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockAppApplicationTests {

	@Test
	void contextLoads() {
		assertEquals(5, 2 + 3);
	}

}
