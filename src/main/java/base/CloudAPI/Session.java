package base.CloudAPI;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.ArrayList;


public class Session {
    private String url;
    private String accessKey;

    public Session(String url, String accessKey) {
        this.url = url;
        this.accessKey = accessKey;
    }

    public String getUrl() {
        return this.url;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        Session other = (Session) o;
        return this.url.equals(other.url) && this.accessKey.equals(other.accessKey);
    }

    @Override
    public String toString() {
        return "url: " + this.url + "\naccess key: " + this.accessKey;
    }

    public HttpResponse<JsonNode> getAllDevices() throws UnirestException {
        String url = this.url + "/api/v1/devices";
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("Authorization", "Bearer " + this.accessKey)
                .asJson();
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(200, response.getStatus());
        return response;
    }

    public HttpResponse<JsonNode> disableEnableUIAutomator(long deviceId, boolean disable) throws UnirestException {
        String url = this.url + String.format("/api/v1/devices/%d/UIAutomator", deviceId);
        HttpResponse<JsonNode> response = Unirest.post(url)
                .header("Authorization", "Bearer " + this.accessKey)
                .queryString("disable", disable)
                .asJson();
        System.out.println(response.getBody());
        return response;
    }

    public String getOs(String serialNumber) throws UnirestException {
        HttpResponse<JsonNode> allDevices = this.getAllDevices();
        JSONArray jsonArray = (JSONArray) allDevices.getBody().getObject().get("data");
        for (Object o: jsonArray) {
            JSONObject deviceJson = (JSONObject) o;
            if (deviceJson.getString("udid").equals(serialNumber)) {
                return deviceJson.getString("deviceOs");
            }
        }
        return null;
    }

    public ArrayList<Long> getAvailableAndroids() throws UnirestException {
        ArrayList<Long> deviceIDs = new ArrayList<>();
        Session session = new Session(this.url, this.accessKey);
        HttpResponse<JsonNode> res = session.getAllDevices();
        JSONArray jsonArray = (JSONArray) res.getBody().getObject().get("data");
        for (Object o: jsonArray) {
            JSONObject deviceJson = (JSONObject) o;
            if (deviceJson.getString("deviceOs").equalsIgnoreCase("Android") && deviceJson.getString("displayStatus").equalsIgnoreCase("Available")
                    && (deviceJson.getString("deviceCategory").equalsIgnoreCase("PHONE") || deviceJson.getString("deviceCategory").equalsIgnoreCase("TABLET"))) {
                deviceIDs.add(deviceJson.getLong("id"));
            }
        }
        return deviceIDs;
    }


    }











