package de.holhar.kata09.service;

import de.holhar.kata09.domain.Item;
import de.holhar.kata09.domain.PricingRule;
import de.holhar.kata09.domain.Purchase;
import de.holhar.kata09.domain.ShoppingCart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @author hharms
 *
 * This service provides functionality to calculate the total of a shoppingcart also incorporating given pricing rules
 * that might reduce the final price.
 */
public class DefaultCheckoutService implements CheckoutService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCheckoutService.class);

    private List<PricingRule> pricingRules;

    public DefaultCheckoutService(List<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    @Override
    public double checkout(ShoppingCart shoppingCart) {
        Purchase purchase = new Purchase(new HashMap<>(), 0);
        shoppingCart.getItems().forEach(item -> scanItem(item, purchase));
        LOGGER.info("Created purchase itemsMap'{}'", purchase.getItemsMap());
        calculateTotal(purchase);

        if (purchase.getTotal() < 0) {
            throw new IllegalStateException("Error occurred during checkout calculation");
        }
        return purchase.getTotal();
    }

    void setPricingRules(List<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    private void scanItem(Item item, Purchase p) {
        if (p.getItemsMap().containsKey(item)) {
            p.getItemsMap().put(item, p.getItemsMap().get(item) + 1);
        } else {
            p.getItemsMap().put(item, 1);
        }
    }

    private void calculateTotal(Purchase p) {
        applyPricingRules(p);
        LOGGER.info("Applying of pricing rules finished => updated itemsMap '{}'", p.getItemsMap());
        p.getItemsMap().forEach(((item, amount) -> {
            p.addToTotal(item.getPrice() * amount);
            LOGGER.info("Added '{}' '{}' times to total => total is now '{}'", item, amount, p.getTotal());
        }));
    }

    private void applyPricingRules(Purchase p) {
        p.getItemsMap().forEach((item, itemCount) ->
            pricingRules.forEach(rule -> {
                if (rule.getSku().equals(item.getSku()))
                    applyPricingRule(rule, item, p);
            })
        );
    }

    private void applyPricingRule(PricingRule rule, Item item, Purchase p) {
        int itemCount = p.getItemsMap().get(item);
        if (itemCount < rule.getAmount()) {
            LOGGER.info("Item count is less than required pricing rule amount => returning");
            return;
        }
        if (rule.getAmount() <= itemCount) {
            p.addToTotal(rule.getPrice());
            p.getItemsMap().put(item, p.getItemsMap().get(item) - rule.getAmount());
            LOGGER.info("Applied pricing rule for '{}' => added '{}' to total => total is now '{}'", item, rule.getPrice(), p.getTotal());
        }
        applyPricingRule(rule, item, p);
    }
}
