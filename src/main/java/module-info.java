module cz.cvut.fel.ts1.ghj {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.cvut.fel.ts1.ghj to javafx.fxml;
    exports cz.cvut.fel.ts1.ghj;
}