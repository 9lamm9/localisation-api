package api;

import com.google.gson.*;
import utils.HttpUtils;

public class OpenMeteoClient {

    public JsonObject getWeather(double lat, double lon, long unix) throws Exception {

        String url = "https://api.open-meteo.com/v1/forecast?"
                + "latitude=" + lat
                + "&longitude=" + lon
                + "&hourly=temperature_2m"
                + "&start=" + unix
                + "&end=" + unix;

        String json = HttpUtils.sendGET(url);
        return JsonParser.parseString(json).getAsJsonObject();
    }
}
