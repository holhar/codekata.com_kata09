package de.holhar.kata09;

import de.holhar.kata09.domain.ShoppingCart;
import de.holhar.kata09.dto.CheckoutResponse;
import de.holhar.kata09.service.CheckoutService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hharms
 *
 * Provides an endpoint for calculating the total price of a given shopping cart.
 */
@RestController
public class CheckoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutController.class);

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     *
     * @param shoppingCart represents the goods to be purchased
     * @return CheckoutResponse response object providing the purchase total and meta information about the status of
     * the request.
     */
    @PostMapping(value = { "/checkout", "/checkout/" })
    public CheckoutResponse checkout(@RequestBody ShoppingCart shoppingCart) {
        if (shoppingCart.getItems() == null || shoppingCart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Error occurred - provided shoppingCart is invalid");
        }

        LOGGER.info("Receiving checkout request with '{}'", shoppingCart);
        double total = checkoutService.checkout(shoppingCart);
        LOGGER.info("Returning calculated total '{}'", total);

        return new CheckoutResponse(true, "Operation succeeded", total);
    }
}
