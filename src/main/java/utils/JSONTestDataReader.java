package utils;

import java.nio.file.Paths;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONTestDataReader {

    public static Map<String, Object> readJson(String filePath, String nodeName) {
        try {
            ObjectNode node = new ObjectMapper().readValue(Paths.get(filePath).toFile(), ObjectNode.class);
            // convert Object to map
            Map<String, Object> map = new ObjectMapper().convertValue(node.get(nodeName), Map.class);
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getValueFromJsonFile(String filePath, String nodeName, String key) {
        try {
            return JSONTestDataReader.readJson(filePath, nodeName).get(key).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
