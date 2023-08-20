package building.sma.email.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("email")
public class EmailProperties {

    private String from;
    private String to;
    private String[] cc = {};
    private String[] bcc = {};

}
