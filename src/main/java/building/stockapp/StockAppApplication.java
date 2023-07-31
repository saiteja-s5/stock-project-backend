package building.stockapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class StockAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StockAppApplication.class);
	}

	public static void main(String[] args) {
		log.info("Starting Stock App Application");
		SpringApplication.run(StockAppApplication.class, args);
	}

}
