package de.holhar.kata09;

import de.holhar.kata09.service.CheckoutService;
import de.holhar.kata09.service.DefaultCheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author hharms
 */
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    @Autowired
    private AppProperties appProperties;

    @Bean
    CheckoutService checkoutService() {
        return new DefaultCheckoutService(appProperties.getPricingRules());
    }
}
