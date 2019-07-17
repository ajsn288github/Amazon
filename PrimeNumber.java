package com.Amazon;

public class PrimeNumber {

    public static void main(String[] args) {

        Double prime = Double.MAX_VALUE;
        PrimeNumber primeNumber = new PrimeNumber();
        long startTime = System.currentTimeMillis();
        System.out.println("IS Prime:  " + primeNumber.isPrime(prime));
        System.out.println("Time Taken:  " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        System.out.println("IS Prime With Legacy:  " + primeNumber.isPrime(prime));
        System.out.println("Time Taken:  " + (System.currentTimeMillis() - startTime));
    }

    private boolean isPrime(Double prime){

        if(prime % 2 == 0 || prime % 3 == 0 || prime % 5 == 0 || prime % 7 == 0){
            return false;
        }

        return !isPerfectSquare(prime);
    }

    private boolean isPrimeLegacy(Long prime){

        for(int i = 2; i < prime; i++){
            if(prime % i == 0){
                return false;
            }
        }

        return true;
    }

    private boolean isPerfectSquare(Double n) {
        int sqrt = (int) Math.sqrt(n);
        return (sqrt * sqrt == n);
    }
}
