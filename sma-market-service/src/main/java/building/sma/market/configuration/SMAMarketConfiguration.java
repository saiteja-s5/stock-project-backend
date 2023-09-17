package building.sma.market.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SMAMarketConfiguration {

    @Bean
    public WebClient.Builder getWebClient() {
	return WebClient.builder();
    }

}
