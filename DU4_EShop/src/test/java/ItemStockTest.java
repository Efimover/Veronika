
import cz.cvut.fel.ts1.shop.Item;
import cz.cvut.fel.ts1.shop.StandardItem;
import cz.cvut.fel.ts1.storage.ItemStock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;


import static org.mockito.Mockito.*;

public class ItemStockTest {
    @Test
    void itemStock_Construcotr(){
        try (MockedConstruction<ItemStock> mocked = mockConstruction(ItemStock.class)){
            ItemStock stock=new ItemStock(null);
            StandardItem item=new StandardItem(1,"Name",15.5f,"Category",2);
            when(stock.getItem()).thenReturn(item);
            Assertions.assertEquals(item,stock.getItem());
            verify(stock).getItem();
            Assertions.assertEquals(1,mocked.constructed().size());
        }
    }
    @Test
    void itemStock_increaseItemCount_1(){
        Item mockedItem = mock(Item.class);
        ItemStock stock=new ItemStock(mockedItem);
        stock.IncreaseItemCount(1);
        Assertions.assertEquals(1,stock.getCount());
    }
    @Test
    void itemStock_decreaseItemCount_(){
        Item mockedItem = mock(Item.class);
        ItemStock stock=new ItemStock(mockedItem);
        stock.decreaseItemCount(1);
        Assertions.assertEquals(-1,stock.getCount());
    }
}

