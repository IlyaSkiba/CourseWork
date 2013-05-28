package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Ilya Skiba
 */
@Configuration
@ImportResource(value = "classpath:integration-context.xml")
public class IntegrationConfig {
}
