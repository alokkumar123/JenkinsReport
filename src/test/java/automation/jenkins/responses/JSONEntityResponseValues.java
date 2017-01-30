package automation.jenkins.responses;

import automation.jenkins.utils.HttpClient;
import automation.jenkins.utils.JSONParser;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONEntityResponseValues {

    static String entity = null;

    public void initiliazeEntity(String multiphaseJobAPIURL) {

        HttpClient httpclient = new HttpClient();
        ClientResponse response = httpclient.getHttpResponse(multiphaseJobAPIURL);
        entity = response.getEntity(String.class);
    }

   static public String locallyIntialiseEntity(String URI) {
        HttpClient httpclient = new HttpClient();
        ClientResponse response = httpclient.getHttpResponse(URI);
        return response.getEntity(String.class);
    }

    public static JSONArray getJSONResponse(String jsonValue) {
        try {
            return new JSONParser().getJsonValue(entity, jsonValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public static JSONArray getJSONResponse(String local,String jsonValue) {
        try {
            return new JSONParser().getJsonValue(local, jsonValue);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getJSONResponseForNode(String jsonValue) {
        try {
            return new JSONObject(entity).getJSONObject(jsonValue);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getJSONResponseForNode(String local,String jsonValue) {
        try {
            return new JSONObject(local).getJSONObject(jsonValue);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String getJSONResponseOnlyForKey(String jsonValue) {
        try {
            return new JSONObject(entity).getString(jsonValue);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String getJSONResponseOnlyForKey(String local, String jsonValue) {
        try {
            return new JSONObject(local).getString(jsonValue);
        } catch (JSONException e) {
            return null;
        }
    }
}
