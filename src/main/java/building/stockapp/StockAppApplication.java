package building.stockapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StockAppApplication extends SpringBootServletInitializer {

	private static final Logger MYLOGGER = Logger.getLogger(StockAppApplication.class.getName());

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StockAppApplication.class);
	}

	public static void main(String[] args) {
		MYLOGGER.log(Level.INFO, "Starting the Stock App Application");
		SpringApplication.run(StockAppApplication.class, args);
	}

}
