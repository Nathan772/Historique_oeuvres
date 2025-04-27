package nate.company.history_work.siteTools.json;

import java.util.HashMap;

public class JsonTools {

    /**
     * made in chatgpt, enable to transform a string that represents a map into a map
     * @param input
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> parseKeyValueString(String input) throws Exception {
        // 1. Remove outer braces if present
        input = input.trim();
        if (input.startsWith("{") && input.endsWith("}")) {
            input = input.substring(1, input.length() - 1);
        }

        // 2. Split by commas to get key=value pairs
        String[] pairs = input.split(",\\s*");

        // 3. Build a map
        HashMap<String, String> map = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                // Optionally try to parse numbers
                String finalValue;
                /*
                try to identify the type
                of the value associated to the key
                 */
                /*if (value.matches("-?\\d+")) {
                    finalValue = Integer.parseInt(value);
                } else if (value.matches("-?\\d+\\.\\d+")) {
                    finalValue = Double.parseDouble(value);
                }*/
                finalValue = value;

                map.put(key, finalValue);
            }
        }

        return map;
    }
}
