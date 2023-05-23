import cz.cvut.fel.ts1.shop.StandardItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class StandartItemTest {
    static private StandardItem item;
    @BeforeAll
    static void makingItem() {
        item = new StandardItem(1, "item", 15.5f, "category", 3);

    }
    @Test
    void standartItem_constructor(){
        StandardItem standardItem=new StandardItem(1,null,14,null,2);
        System.out.println(standardItem.toString());
    }
    @Test
    void standartItem_toString_true(){
        String expected = "Item ID 1 NAME item CATEGORY category PRICE 15.5 LOYALTY POINTS 3";
        Assertions.assertEquals(item.toString(),expected);
    }
    @Test
    void standartItem_copy(){
        StandardItem standardItem2=item.copy();
        Assertions.assertEquals(standardItem2,item);
    }
    @Test
    void standartItem_equals_true() {
        StandardItem standardItem = new StandardItem(1, "item", 15.5f, "category", 3);
        Assertions.assertTrue(standardItem.equals(item));
    }
    @Test
    void standartItem_equals_false(){
        StandardItem standardItem = new StandardItem(2, "blabla", 15.5f, "category", 3);
        Assertions.assertFalse(standardItem.equals(item));
    }

}
