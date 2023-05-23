import cz.cvut.fel.ts1.shop.Item;
import cz.cvut.fel.ts1.shop.Order;
import cz.cvut.fel.ts1.shop.ShoppingCart;
import cz.cvut.fel.ts1.shop.StandardItem;
import cz.cvut.fel.ts1.storage.ItemStock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class OrderTest {
    static private ShoppingCart cart;
    final private String customerName="Name";
    final private String customerAddress="Adress";
    @BeforeAll
    static void setUp() {
        cart = mock(ShoppingCart.class);
        when(cart.getCartItems()).thenReturn(
                new ArrayList<>(Arrays.asList(
                        new StandardItem(1, "Item 1", 13.0f, "Category", 1),
                        new StandardItem(2, "Item 2", 10.1f, "Category", 2)))
        );
    }

    @Test
    void Order_MockConstrucotr_MockedAdress(){
        try (MockedConstruction<Order> mocked = mockConstruction(Order.class)){
            Order order=new Order(cart,customerName,customerAddress);
            when(order.getCustomerAddress()).thenReturn("MockedAdress");
            Assertions.assertEquals("MockedAdress",order.getCustomerAddress());
            verify(order).getCustomerAddress();
            Assertions.assertEquals(1,mocked.constructed().size());
        }
    }
    @Test
    void Order_EmptyConstrucotr_NullPointerException(){
        Assertions.assertThrows(NullPointerException.class,
                () -> {
                    Order order = new Order(null,customerName,customerAddress);
                });
    }
    @Test
    void Order_ConstructorWithoutState_0(){
        Order order = new Order(cart, customerName, customerAddress);
        Assertions.assertEquals(0,order.getState());
    }
    @Test
    void Order_RemoveItem(){
        ShoppingCart newCart=new ShoppingCart();
        newCart.addItem(new StandardItem(1, "Item 1", 13.0f, "Category", 1));
        newCart.addItem(new StandardItem(2, "Item 2", 10.1f, "Category", 2));
        newCart.removeItem(2);
        Assertions.assertEquals(1,newCart.getItemsCount());
    }
    @Test
    void Order_RemoveNonExistingItem(){
        ShoppingCart newCart=new ShoppingCart();
        newCart.addItem(new StandardItem(1, "Item 1", 13.0f, "Category", 1));
        newCart.addItem(new StandardItem(2, "Item 2", 10.1f, "Category", 2));
        newCart.removeItem(3);
        Assertions.assertEquals(2,newCart.getItemsCount());
    }

}
