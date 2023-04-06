import cz.cvut.fel.ts1.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
        Calculator cal = new Calculator();
        @Test
        public void Test_Add_4(){
                int result=cal.add(2,2);
                Assertions.assertEquals(result,4);
        }
        @Test
        public void Test_Subtract_6 (){
                int result = cal.subtract(8, 2);
                Assertions.assertEquals(result, 6);
        }
        @Test
        public void Test_multiply_8 (){
                int result = cal.multiply(4, 2);
                Assertions.assertEquals(result, 8);
        }

        @Test
        public void Test_divide_2 (){
                int result = cal.divide(4, 2);
                Assertions.assertEquals(result, 2);
        }

        @Test
        public void Test_divide_null_exception (){
                assertThrows(ArithmeticException.class,
                        () -> {
                                Calculator cal = new Calculator();
                                long div = cal.divide(4, 0);
                        });
        }


}
