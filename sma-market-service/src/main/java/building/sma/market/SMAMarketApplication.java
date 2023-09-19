package building.sma.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import building.sma.market.service.StockDailyRecordService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class SMAMarketApplication extends SpringBootServletInitializer {

    @Autowired
    private StockDailyRecordService dailyRecordService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(SMAMarketApplication.class);
    }

    public static void main(String[] args) {
	log.info("Starting Stock Market Application Market Module");
	SpringApplication.run(SMAMarketApplication.class, args);

    }

    @PostConstruct
    void run() {
	dailyRecordService.saveRecords();
    }

}
