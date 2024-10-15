package helpermethods;

import base.Base;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Properties;

public class ReadFromExt extends Base {

    public ReadFromExt(Base base) {
        super();
    }


    public String getText(String path) throws Exception {
        {
            StringBuilder fileContents = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContents.append(line).append("\n"); // Append line with newline
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }

            return fileContents.toString();
        }
    }


    public static String getProperty(String filePath, String key) {
        Properties properties = new Properties();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }

        return properties.getProperty(key);
    }
    public String getSqlQuery(String propertyKey,String oldValue, String replacementValue , String fileName ) {

        String query;
        query = ReadFromExt.getProperty(System.getProperty("user.dir") + "/src/main/resources/SQL/"+ fileName +".properties", propertyKey).replace(oldValue, replacementValue);
        return query;
    }

    public String readValueJsonFromFile(String filePath, String key) {
        JSONObject jsonObject = null;

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(reader);

        } catch (IOException | ParseException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return JsonPath.read(jsonObject, key).toString();
    }

    public JSONObject getJsonObjFile(String filePath) {
        JSONObject jsonObject = null;

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(reader);

        } catch (IOException | ParseException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return jsonObject;
    }



}
