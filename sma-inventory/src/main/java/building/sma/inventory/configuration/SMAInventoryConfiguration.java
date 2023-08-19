package building.sma.inventory.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class SMAInventoryConfiguration {

    @Bean
    public MessageSource messageSource() {
	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	messageSource.setBasename("classpath:messages_en");
	messageSource.setDefaultEncoding("UTF-8");
	return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
	LocalValidatorFactoryBean validatorBean = new LocalValidatorFactoryBean();
	validatorBean.setValidationMessageSource(messageSource());
	return validatorBean;
    }
}
