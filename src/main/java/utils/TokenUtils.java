package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    public TokenUtils() {
}

    public static String getItemAsString(String token, String itemKey) {
        //Decode
        Map<String, Claim> tokenClaims = decode(token);

        //Return the Item
        return tokenClaims.get(itemKey).asString();
    }

    public static Integer getItemAsInteger(String token, String itemKey) {
        //Decode
        Map<String, Claim> tokenClaims = decode(token);

        //Return the Item
        return tokenClaims.get(itemKey).asInt();
    }

    public static Map<String, Object> getItemAsMap(String token, String itemKey) {
        //Decode
        Map<String, Claim> tokenClaims = decode(token);

        //Return The item
        return tokenClaims.get(itemKey).asMap();
    }

    private static Map<String, Claim> decode(String token) {
        Map<String, Claim> tokenClaims = new HashMap<>();
        if (!token.isEmpty()) {
            DecodedJWT jwt = JWT.decode(token);
            tokenClaims = jwt.getClaims();
        }
        return tokenClaims;
    }
}
