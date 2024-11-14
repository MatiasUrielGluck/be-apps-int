package com.uade.beappsint.utils;

import java.util.Random;

public class CommonUtilities {
    public static double formatAmount(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    public static String extractImageFormat(String base64) {
        String format = null;
        if (base64 != null && base64.startsWith("data:image/")) {
            int start = base64.indexOf("/") + 1;
            int end = base64.indexOf(";");
            format = base64.substring(start, end);
        }
        return format;
    }

    public static Boolean isValidImageFormat(String format) {
        if (format == null) return false;
        return (format.equals("png") || format.equals("jpg") || format.equals("jpeg"));
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10); // Genera un número entre 0 y 9
            result.append(randomNumber);
        }

        return result.toString();
    }
}
