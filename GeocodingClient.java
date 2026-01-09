package api;

import com.google.gson.*;
import utils.HttpUtils;

public class GeocodingClient {

    public JsonObject geocode(String city) throws Exception {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city;
        String json = HttpUtils.sendGET(url);
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public double lat(JsonObject o) {
        return o.get("results").getAsJsonArray().get(0)
                .getAsJsonObject().get("latitude").getAsDouble();
    }

    public double lon(JsonObject o) {
        return o.get("results").getAsJsonArray().get(0)
                .getAsJsonObject().get("longitude").getAsDouble();
    }
}
