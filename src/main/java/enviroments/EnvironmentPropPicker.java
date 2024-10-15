package enviroments;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentPropPicker {
private final EnvironmentsLoader environmentsLoader;

public EnvironmentPropPicker() {environmentsLoader = EnvironmentsLoader.getInstance();}


public String getRootURL(){return environmentsLoader.getEnvPropValue("ROOT_URL");}
public String getRootURLBeta(){return environmentsLoader.getEnvPropValue("Beta_ROOT_URL");}
public String getLoginURI(){return environmentsLoader.getEnvPropValue("LOGIN_URI");}


    public HashMap<String, String> getAuthenticationProperties() {
        HashMap<String, String> authenticationProperties = new HashMap<>();
        String clientId = environmentsLoader.getEnvPropValue("CLIENT_ID");
        String grantType = environmentsLoader.getEnvPropValue("grant_type");
        String scope = environmentsLoader.getEnvPropValue("SCOPE");

        authenticationProperties.put("client_id", clientId);
        authenticationProperties.put("scope", scope);
        authenticationProperties.put("grant_type", grantType);

        return authenticationProperties;
    }

    // Get Organization users email & password  per environment
    public JSONObject getUserCredentials(String orgType, String orgNumber) {

        JSONObject organizationCredentials = new JSONObject();
        String TaxRegNo = environmentsLoader.getUserPropValue(orgType + orgNumber + "TaxRegNumber");
        String password = environmentsLoader.getUserPropValue(orgType + orgNumber + "Password");
        organizationCredentials.put("TaxRegNo", TaxRegNo);
        organizationCredentials.put("Password", password);
        return organizationCredentials;
    }

    // DB Credentials
    public Map<String, String> getDatabaseCredentials() {
        HashMap<String, String> databaseCredentials = new HashMap<>();
        databaseCredentials.put("DB_HOST", environmentsLoader.getEnvPropValue("DB_URL"));
        databaseCredentials.put("DB_USER_NAME", environmentsLoader.getEnvPropValue("DB_USER_NAME"));
        databaseCredentials.put("DB_PASSWORD", environmentsLoader.getEnvPropValue("DB_PASSWORD"));
        return databaseCredentials;
    }
}
