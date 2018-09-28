package de.holhar.kata09.service;

import de.holhar.kata09.domain.Item;
import de.holhar.kata09.domain.PricingRule;
import de.holhar.kata09.domain.ShoppingCart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author hharms
 */
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(value = "test")
public class DefaultCheckoutServiceServiceTest {

    @InjectMocks
    private DefaultCheckoutService checkoutService;

    private static Item a = new Item("A", 30);
    private static Item b = new Item("B", 40);
    private static Item c = new Item("C", 50);
    private static Item d = new Item("D", 25);

    @Before
    public void setup() {
        List<PricingRule> pricingRules = new ArrayList<>();
        pricingRules.add(new PricingRule("A", 3, 60));
        pricingRules.add(new PricingRule("B", 5, 150));
        pricingRules.add(new PricingRule("C", 2, 80));
        checkoutService.setPricingRules(pricingRules);
    }

    @Test(expected = IllegalStateException.class)
    public void checkout_invalidShoppingCart() {
        // given
        ShoppingCart invalidShoppingCart =
                new ShoppingCart(new ArrayList<Item>() {{ add(new Item("INVALID", -5.0)); }});

        // when
        checkoutService.checkout(invalidShoppingCart);
    }

    @Test
    public void checkout_zeroItems() {
        // given
        ShoppingCart emptyCart = new ShoppingCart(Collections.emptyList());

        // when
        double total = checkoutService.checkout(emptyCart);

        // then
        assertEquals(0.0, total);
    }

    @Test
    public void checkout_withSmallShoppingCart() {
        // given
        ShoppingCart smallShoppingCart = new ShoppingCart(createSmallItemList());

        // when
        double total = checkoutService.checkout(smallShoppingCart);

        // then
        assertEquals(145.0, total);
    }

    @Test
    public void checkout_noPricingRuleApplied() {
        // given
        ShoppingCart shoppingCart = new ShoppingCart(createItemListOfOneItemType(2));

        // when
        double total = checkoutService.checkout(shoppingCart);

        // then
        assertEquals(60.0, total);
    }

    @Test
    public void checkout_exactPricingRuleMatch() {
        // given
        ShoppingCart shoppingCart = new ShoppingCart(createItemListOfOneItemType(3));

        // when
        double total = checkoutService.checkout(shoppingCart);

        // then
        assertEquals(60.0, total);
    }

    @Test
    public void checkout_pricingRuleMatchPlusOne() {
        // given
        ShoppingCart shoppingCart = new ShoppingCart(createItemListOfOneItemType(4));

        // when
        double total = checkoutService.checkout(shoppingCart);

        // then
        assertEquals(90.0, total);
    }

    @Test
    public void checkout_withBigShoppingCart() {
        // given
        ShoppingCart bigShoppingCart = new ShoppingCart(createBigItemList());

        // when
        double total = checkoutService.checkout(bigShoppingCart);

        // then
        assertEquals(575.00, total);
    }

    private List<Item> createSmallItemList() {
        return new ArrayList<Item>() {{
            add(c); add(d); add(b); add(a);
        }};
    }

    private List<Item> createItemListOfOneItemType(int itemCount) {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemList.add(a);
        }
        return itemList;
    }

    private List<Item> createBigItemList() {
        return new ArrayList<Item>() {{
            add(a); add(c); add(d); add(a); add(b); add(a); add(d);
            add(a); add(b); add(b); add(a); add(c); add(b); add(b);
            add(c); add(a); add(b); add(d); add(a); add(c);
        }};
    }
}