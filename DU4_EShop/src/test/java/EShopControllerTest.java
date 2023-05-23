import cz.cvut.fel.ts1.archive.PurchasesArchive;
import cz.cvut.fel.ts1.shop.*;
import cz.cvut.fel.ts1.shop.Order;
import cz.cvut.fel.ts1.storage.Storage;
import org.junit.jupiter.api.*;
import cz.cvut.fel.ts1.storage.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class EShopControllerTest {

    static private final int[] itemCount = {10,10,4,5,10,2};
    static private final Item[] storageItems = {
            new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
            new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
            new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
            new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
            new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
            new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
    };
    static private EShopController controller;
    static private ShoppingCart newCart;

    @BeforeEach
    void creatingEshop(){
        controller = new EShopController();
        for (int i = 0; i < storageItems.length; i++) {
            controller.storage.insertItems(storageItems[i], itemCount[i]);
        }
        newCart = controller.newCart();
    }
    @Test

    public void testPurchaseShoppingCartWithEmptyCart_EmptyCart() throws EmptyCartException {
        assertThrows(
                EmptyCartException.class,
                () -> controller.purchaseShoppingCart(newCart,"Test Name", "Test Address")
        );
    }
    @Test
    public void testPurchaseItemTooManyTimes() throws NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        newCart.addItem(storageItems[5]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(720,newCart.getTotalPrice());
        newCart.addItem(storageItems[5]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals(720*2,newCart.getTotalPrice());
        newCart.addItem(storageItems[5]);
        Assertions.assertEquals(3,newCart.getItemsCount());
        Assertions.assertEquals(720*3,newCart.getTotalPrice());
        assertThrows(
                NoItemInStorage.class,
                () ->
                        controller.purchaseShoppingCart(
                        newCart, "Test Name", "Test Address")
        );
        Assertions.assertEquals(0,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[5]));
        Assertions.assertEquals(0,controller.orders.size());
    }
    @Test
    public void testPurchaseShoppingCart() throws EmptyCartException, NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        newCart.addItem(storageItems[1]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(6000,newCart.getTotalPrice());
        newCart.addItem(storageItems[2]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals( 6200,newCart.getTotalPrice());
        newCart.addItem(storageItems[3]);
        Assertions.assertEquals(6550,newCart.getTotalPrice());
        Assertions.assertEquals(3,newCart.getItemsCount());
        controller.purchaseShoppingCart(newCart, "Test Name", "Test Address");
        Assertions.assertEquals(1,controller.orders.size());
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[2]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[3]));
        Assertions.assertEquals(9,controller.storage.getItemCount(2));
        Assertions.assertEquals(3,controller.storage.getItemCount(3));
        Assertions.assertEquals(4,controller.storage.getItemCount(4));
    }
    @Test
    public void testPurchaseShoppingCartAfterRemoving() throws EmptyCartException, NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        newCart.addItem(storageItems[1]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(6000,newCart.getTotalPrice());
        newCart.addItem(storageItems[2]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals(6200,newCart.getTotalPrice());
        newCart.removeItem(2);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(200,newCart.getTotalPrice());
        controller.purchaseShoppingCart(newCart, "Test Name", "Test Address");
        Assertions.assertEquals(1,controller.orders.size());
        Assertions.assertEquals(0,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[2]));
    }
    @Test
    public void testOrdersListAdding() throws EmptyCartException, NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        newCart.addItem(storageItems[1]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(6000,newCart.getTotalPrice());
        newCart.addItem(storageItems[2]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals(6200,newCart.getTotalPrice());
        newCart.addItem(storageItems[3]);
        Assertions.assertEquals(3,newCart.getItemsCount());
        Assertions.assertEquals(6550,newCart.getTotalPrice());
        controller.purchaseShoppingCart(newCart, "Test Name", "Test Address");
        Assertions.assertEquals(1,controller.orders.size());
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[3]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[2]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(4,controller.storage.getItemCount(4));
        Assertions.assertEquals(3,controller.storage.getItemCount(3));
        Assertions.assertEquals(9,controller.storage.getItemCount(2));
    }

    @Test
    public void testAddingItemNotInStorage() {
        Assertions.assertEquals(1,controller.carts.size());
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () ->
                        newCart.addItem(storageItems[6])

        );
        Assertions.assertEquals(0,newCart.getItemsCount());
        Assertions.assertEquals(0,controller.orders.size());
    }
    @Test
    public void testMultipleOrders() throws EmptyCartException, NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        newCart.addItem(storageItems[2]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(200,newCart.getTotalPrice());
        newCart.addItem(storageItems[2]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals(400,newCart.getTotalPrice());
        controller.purchaseShoppingCart(newCart,"Test Name", "Test Address");
        controller.purchaseShoppingCart(newCart,"Test Name", "Test Address");
        assertThrows(
                NoItemInStorage.class,
                () ->
                controller.purchaseShoppingCart(newCart,"Test Name", "Test Address")
        );
        Assertions.assertEquals(2,controller.orders.size());
        Assertions.assertEquals(4,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[2]));
        Assertions.assertEquals(0,controller.storage.getItemCount(3));
    }
    @Test
    public void testMultipleOrdersArchive() throws EmptyCartException, NoItemInStorage {
        Assertions.assertEquals(1,controller.carts.size());
        ShoppingCart secondCart=controller.newCart();
        Assertions.assertEquals(2,controller.carts.size());
        newCart.addItem(storageItems[4]);
        Assertions.assertEquals(1,newCart.getItemsCount());
        Assertions.assertEquals(240,newCart.getTotalPrice());
        newCart.addItem(storageItems[3]);
        Assertions.assertEquals(2,newCart.getItemsCount());
        Assertions.assertEquals(240+350,newCart.getTotalPrice());
        secondCart.addItem(storageItems[4]);
        Assertions.assertEquals(1,secondCart.getItemsCount());
        Assertions.assertEquals(240,secondCart.getTotalPrice());
        secondCart.addItem(storageItems[3]);
        Assertions.assertEquals(2,secondCart.getItemsCount());
        Assertions.assertEquals(240+350,secondCart.getTotalPrice());
        controller.purchaseShoppingCart(newCart, "Test Name", "Test Address");
        Assertions.assertEquals(1,controller.orders.size());
        Assertions.assertEquals(9,controller.storage.getItemCount(5));
        Assertions.assertEquals(4,controller.storage.getItemCount(4));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[3]));
        Assertions.assertEquals(1,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[4]));
        controller.purchaseShoppingCart(secondCart, "Test Name", "Test Address");
        Assertions.assertEquals(2,controller.orders.size());
        Assertions.assertEquals(8,controller.storage.getItemCount(5));
        Assertions.assertEquals(3,controller.storage.getItemCount(4));
        Assertions.assertEquals(2,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[3]));
        Assertions.assertEquals(2,controller.archive.getHowManyTimesHasBeenItemSold(storageItems[4]));
    }

}
