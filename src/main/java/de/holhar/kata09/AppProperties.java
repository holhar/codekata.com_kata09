package de.holhar.kata09;

import de.holhar.kata09.domain.PricingRule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hharms
 */
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    private List<PricingRule> pricingRules = new ArrayList<>();

    public List<PricingRule> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(List<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }
}
