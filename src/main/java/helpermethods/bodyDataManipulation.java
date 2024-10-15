package helpermethods;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.security.SecureRandom;
import java.util.Random;

public class bodyDataManipulation {

    // Random generator for alphanumeric and numeric strings
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Single method to modify the JSON and return the updated object
    public static JSONObject modifyJson(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof String) {
                // Process the string value and replace if it matches the patterns
                jsonObject.put(key, processValue((String) value));
            } else if (value instanceof JSONObject) {
                // Recursively modify nested JSONObject
                modifyJson((JSONObject) value); // Modified to avoid unnecessary put()
            } else if (value instanceof JSONArray) {
                // Recursively modify JSONArray
                JSONArray jsonArray = (JSONArray) value;
                for (int i = 0; i < jsonArray.size(); i++) {
                    Object item = jsonArray.get(i);
                    if (item instanceof JSONObject) {
                        modifyJson((JSONObject) item); // Modified to avoid unnecessary add()
                    } else if (item instanceof String) {
                        jsonArray.set(i, processValue((String) item)); // Use set() instead of add() to avoid increasing size
                    }
                }
            }
        }
        return jsonObject;
    }

    // Helper method to process the value and replace it based on the pattern
    private static String processValue(String value) {
        if (value.startsWith("String ")) {
            int length = Integer.parseInt(value.split(" ")[1]);
            return generateRandomString(length);
        } else if (value.startsWith("Number ")) {
            int length = Integer.parseInt(value.split(" ")[1]);
            return generateRandomNumber(length);
        } else if (value.startsWith("AlphaNumeric ")) {
            int length = Integer.parseInt(value.split(" ")[1]);
            return generateRandomString(length); // Alphanumeric strings use the same function
        }
        return value; // Return the original value if no match
    }

    // Generate a random alphanumeric string of the specified length
    private static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }

    // Generate a random numeric string of the specified length
    private static String generateRandomNumber(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(RANDOM.nextInt(10)); // Digits are between 0 and 9
        }
        return builder.toString();
    }

    public static void replaceValues(JSONObject target, JSONObject replacements) {
        for (Object key : target.keySet()) {
            Object targetValue = target.get(key);
            if (replacements.containsKey(key)) {
                // Replace the value directly if it exists in the replacements JSON
                target.put(key, replacements.get(key));
            } else if (targetValue instanceof JSONObject) {
                // Recursively process nested JSONObject
                replaceValues((JSONObject) targetValue, replacements);
            } else if (targetValue instanceof JSONArray) {
                // Process each JSONObject within JSONArray
                JSONArray targetArray = (JSONArray) targetValue;
                for (Object arrayElement : targetArray) {
                    if (arrayElement instanceof JSONObject) {
                        replaceValues((JSONObject) arrayElement, replacements);
                    }
                }
            }
        }
    }

}
