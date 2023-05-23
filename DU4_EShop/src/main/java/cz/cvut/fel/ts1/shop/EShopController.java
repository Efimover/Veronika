package cz.cvut.fel.ts1.shop;



import java.util.ArrayList;

import cz.cvut.fel.ts1.storage.*;
import cz.cvut.fel.ts1.archive.*;


public class EShopController {

    public Storage storage;
    public PurchasesArchive archive;
    public ArrayList<ShoppingCart> carts;
    public ArrayList<Order> orders;


    public EShopController() {
        this.storage = new Storage();
        this.archive = new PurchasesArchive();
        this.carts = new ArrayList<>();
        this.orders = new ArrayList<>();
    }


    public void purchaseShoppingCart(ShoppingCart cart, String customerName, String customerAddress)
            throws  NoItemInStorage, EmptyCartException {
        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Shopping cart is empty");
        }
        Order order = new Order(cart, customerName, customerAddress);
        try {
            storage.processOrder(order);
            orders.add(order);
        } catch (NoItemInStorage e) {
            throw new NoItemInStorage();
        }
        archive.putOrderToPurchasesArchive(order);
    }

    public ShoppingCart newCart() {
        ShoppingCart newCart = new ShoppingCart();
        carts.add(newCart);
        return newCart;
    }



    public void main(String[] args) throws NoItemInStorage, EmptyCartException {

        /* make up an artificial data */

        int[] itemCount = {10,10,4,5,10,2};


        Item[] storageItems = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
                new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
                new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
        };

        // insert data to the storage
        for (int i = 0; i < storageItems.length; i++) {
            storage.insertItems(storageItems[i], itemCount[i]);
        }


        storage.printListOfStoredItems();

        System.out.println();

        System.out.println("TEST RUN:   Fill and purchase a shopping cart");
        ShoppingCart newCart = new ShoppingCart();
        newCart.addItem(storageItems[0]);
        newCart.addItem(storageItems[1]);
        newCart.addItem(storageItems[2]);
        newCart.addItem(storageItems[4]);
        newCart.addItem(storageItems[5]);
        try {
            purchaseShoppingCart(newCart, "Libuse Novakova","Kosmonautu 25, Praha 8");
        }catch (EmptyCartException e){
            throw new NoItemInStorage();
        }

        archive.printItemPurchaseStatistics();
        storage.printListOfStoredItems();


        System.out.println();


        System.out.println("TEST RUN:    Trying to purchase an empty shopping cart");

        ShoppingCart newEmptyCart = new ShoppingCart();
        try {
            purchaseShoppingCart(newEmptyCart, "Jarmila Novakova", "Spojovaci 23, Praha 3");
        }catch (EmptyCartException e){
            throw new EmptyCartException("Shopping cart is empty");
        }



    }



}
