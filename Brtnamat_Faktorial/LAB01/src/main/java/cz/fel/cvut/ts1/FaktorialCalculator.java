package cz.fel.cvut.ts1;

public class FaktorialCalculator {

    public long factorial(int n){
        if(n<0){
            throw new IllegalArgumentException("Faktorial pro tohle cislo nejde");
        }
        if (n<2){
            return 1;
        }
        return n*factorial(n-1);
    }
}
