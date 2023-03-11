package cz.fel.cvut.ts1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class FaktorialTest1 {

    @Test
    void factorial() {
        FaktorialCalculator fc=new FaktorialCalculator();
        long factorial=fc.factorial(5);

        Assertions.assertEquals(120,factorial);
    }
    @Test
    void factorial_0() {
        FaktorialCalculator fc=new FaktorialCalculator();
        long factorial=fc.factorial(0);

        Assertions.assertEquals(1,factorial);
    }
    @Test
    void factorial_negativ() {
        FaktorialCalculator fc=new FaktorialCalculator();
        try {
            long factorial = fc.factorial(-1);

        } catch (Exception ex){
            Assertions.assertNotNull(ex);
        }
    }
}
