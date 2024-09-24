package com.uade.beappsint.utils;

import java.util.Random;

public class TransactionUtilities {
    public static double getConversionRate() {
        // TODO: Emulamos el valor del dolar. Debería realizarse un llamado a un servicio externo para obtener el valor real.
        Random random = new Random();
        int min = 1200;
        int max = 1500;
        return random.nextInt((max - min) + 1) + min;
    }

    public static double calculateAmountUsdFromArs(double amountARS, double conversionRate) {
        return CommonUtilities.formatAmount(amountARS / conversionRate);
    }
}
