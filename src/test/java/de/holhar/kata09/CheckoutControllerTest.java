package de.holhar.kata09;

import de.holhar.kata09.domain.Item;
import de.holhar.kata09.domain.ShoppingCart;
import de.holhar.kata09.service.CheckoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hharms
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CheckoutController.class)
@ActiveProfiles(value = "test")
public class CheckoutControllerTest {

    @MockBean
    private CheckoutService checkoutService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void checkout_successful() throws Exception {
        // given
        ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<Item>() {{ add(new Item("A", 50)); }});
        when(checkoutService.checkout(any(ShoppingCart.class))).thenReturn(50.00);

        // when
        ResultActions result = mvc.perform(post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonContent(shoppingCart)));

        // then
        verify(checkoutService, times(1)).checkout(any(ShoppingCart.class));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Operation succeeded"))
                .andExpect(jsonPath("$.total", is(50.00)));
    }

    @Test
    public void checkout_invalidShoppingCart() throws Exception {
        // given
        ShoppingCart shoppingCart = new ShoppingCart(Collections.emptyList());

        // when
        ResultActions result = mvc.perform(post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonContent(shoppingCart)));

        // then
        verifyZeroInteractions(checkoutService);

        result.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error occurred - provided shoppingCart is invalid"))
                .andExpect(jsonPath("$.total", is(0.0)));
    }

    private static String toJsonContent(ShoppingCart shoppingCart) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(shoppingCart);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to transform object into json");
        }
    }
}