package building.sma.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class SMAInventoryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(SMAInventoryApplication.class);
    }

    public static void main(String[] args) {
	log.info("Starting Stock Market Application Inventory Module");
	SpringApplication.run(SMAInventoryApplication.class, args);
    }

}
