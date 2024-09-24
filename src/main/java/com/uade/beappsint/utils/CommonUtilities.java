package com.uade.beappsint.utils;

public class CommonUtilities {
    public static double formatAmount(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
