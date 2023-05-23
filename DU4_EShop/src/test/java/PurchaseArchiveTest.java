import cz.cvut.fel.ts1.archive.*;
import cz.cvut.fel.ts1.shop.Item;
import cz.cvut.fel.ts1.shop.Order;
import cz.cvut.fel.ts1.shop.ShoppingCart;
import cz.cvut.fel.ts1.shop.StandardItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PurchaseArchiveTest {
    final static private StandardItem item1 = new StandardItem(2, null, 30, null, 2);
    final static private StandardItem item2=new StandardItem(1,"Bar",14,"Sweets",1);

    static private ShoppingCart cart;
    static private Order order;

    @BeforeAll
    static void addingToShoppingCart(){

        cart=new ShoppingCart();
        cart.addItem(item1);
        cart.addItem(item2);
        order=new Order(cart,"Name","Adress");
    }
    @Test
    void purchaseArchive_putOrder_NulPointException(){
        Assertions.assertThrows(NullPointerException.class,
                () -> {
                    PurchasesArchive archive=new PurchasesArchive();
                    archive.putOrderToPurchasesArchive(null);
                });
    }

    @Test
    void purchaseArchive_putToArchive(){
        PurchasesArchive archive=new PurchasesArchive();
        archive.putOrderToPurchasesArchive(order);
    }
    @Test
    void purchaseArchive_getHowMany_1(){
        PurchasesArchive archive=new PurchasesArchive();
        archive.putOrderToPurchasesArchive(order);
        assertEquals(1,archive.getHowManyTimesHasBeenItemSold(item1));

    }
    @Test
    void purchaseArchive_mockPurchaseArchive(){
        try (MockedConstruction<PurchasesArchive> mocked = mockConstruction(PurchasesArchive.class)) {
            PurchasesArchive purchasesArchive = new PurchasesArchive();
            when(purchasesArchive.printItemPurchaseStatistics()).thenReturn("String");
            assertEquals("String", purchasesArchive.printItemPurchaseStatistics());
            verify(purchasesArchive).printItemPurchaseStatistics();
            assertEquals(1, mocked.constructed().size());
        }
    }
    @Test
    void purchaseArchive_mockIPAE(){
        try (MockedConstruction<ItemPurchaseArchiveEntry> mocked = mockConstruction(ItemPurchaseArchiveEntry.class)) {
            ItemPurchaseArchiveEntry itemPurchaseArchiveEntry = new ItemPurchaseArchiveEntry(null);
            when(itemPurchaseArchiveEntry.getCountHowManyTimesHasBeenSold()).thenReturn(1);
            assertEquals(1, itemPurchaseArchiveEntry.getCountHowManyTimesHasBeenSold());
            verify(itemPurchaseArchiveEntry).getCountHowManyTimesHasBeenSold();
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void purchaseArchive_ItemPurchaseArchiveEntry_nullItem(){
        ItemPurchaseArchiveEntry itemPurchaseArchiveEntry = new ItemPurchaseArchiveEntry(null);
        Item item= itemPurchaseArchiveEntry.getRefItem();
        Assertions.assertEquals(null,item);
    }
    @Test
    void purchaseArchive_ItemPurchaseArchiveEntry_nullPointException(){
        Assertions.assertThrows(NullPointerException.class,
                () -> {
                    ItemPurchaseArchiveEntry itemPurchaseArchiveEntry = new ItemPurchaseArchiveEntry(null);
                    String condition= itemPurchaseArchiveEntry.toString();
                });
    }


}
