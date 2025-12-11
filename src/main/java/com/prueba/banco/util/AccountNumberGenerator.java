package com.prueba.banco.util;

import java.util.UUID;

public class AccountNumberGenerator {
    public static String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}