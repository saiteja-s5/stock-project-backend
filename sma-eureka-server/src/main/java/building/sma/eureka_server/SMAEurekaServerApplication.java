package building.sma.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableEurekaServer
@SpringBootApplication
public class SMAEurekaServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(SMAEurekaServerApplication.class);
    }

    public static void main(String[] args) {
	log.info("Starting Stock Market Application Eureka Server Module");
	SpringApplication.run(SMAEurekaServerApplication.class, args);
    }

}
