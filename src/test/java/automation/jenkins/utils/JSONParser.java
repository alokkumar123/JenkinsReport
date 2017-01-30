package automation.jenkins.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONParser {
	
	public JSONArray getJsonValue(String jsonString, String jsonKey)
			throws JSONException {
		JSONObject obj = new JSONObject(jsonString);
		JSONArray issuesArray = obj.getJSONArray(jsonKey.split(":")[0]);
		return issuesArray;
	}

}
