package com.example.mersenne;

import java.math.BigInteger;

public class CheckUtils {
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        int sqrtN = (int) Math.sqrt(number) + 1;
        for (int i = 3; i < sqrtN; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean lucasLehmerTest(int p) {
        if (p == 2) {
            return true;
        }
        BigInteger M_p = BigInteger.valueOf(2).pow(p).subtract(BigInteger.ONE);
        BigInteger s = BigInteger.valueOf(4);

        for (int i = 0; i < p - 2; i++) {
            s = s.multiply(s).subtract(BigInteger.TWO).mod(M_p);
        }
        return s.equals(BigInteger.ZERO);
    }
}
