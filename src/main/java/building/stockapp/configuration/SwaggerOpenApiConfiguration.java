package building.stockapp.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Saiteja", email = "saithejasabbani1@gmail.com", url = "https://github.com/saiteja-s5"), description = "OpenApi documentation for Stock Market Application", title = "Stock Application Documentation", version = "1.0"), servers = {
		@Server(description = "DEV ENV", url = "http://localhost:5050/stock-app/v1"),
		@Server(description = "UAT ENV", url = "http://localhost:6060/stock-app/v1"),
		@Server(description = "PROD ENV", url = "http://localhost:7070/stock-app/v1") })
public class SwaggerOpenApiConfiguration {

}
