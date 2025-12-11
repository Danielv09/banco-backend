package com.prueba.banco.util;

import java.math.BigDecimal;

public class ValidationUtils {

    private ValidationUtils() {}

    public static boolean isPositiveOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }
}