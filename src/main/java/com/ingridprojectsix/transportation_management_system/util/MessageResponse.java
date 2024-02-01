package com.ingridprojectsix.transportation_management_system.util;

import java.util.Map;

public class MessageResponse {

    public static Map<String, String> displayMessage(String message) {
        return Map.of("message", message);
    }
}
