package building.sma.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class SMAGatewayApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(SMAGatewayApplication.class);
    }

    public static void main(String[] args) {
	log.info("Starting Stock Market Application Gateway Module");
	SpringApplication.run(SMAGatewayApplication.class, args);
    }

}
