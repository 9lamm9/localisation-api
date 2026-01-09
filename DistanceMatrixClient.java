package api;

import com.google.gson.*;
import utils.*;

public class DistanceMatrixClient {

    private final String apiKey;

    public DistanceMatrixClient() {
        this.apiKey = Config.get("DISTANCEMATRIX_API_KEY");
    }

    public JsonObject getRoute(String from, String to, String mode, long departureUnix) throws Exception {

        String url = "https://api.distancematrix.ai/maps/api/distancematrix/json?"
                + "origins=" + from
                + "&destinations=" + to
                + "&mode=" + mode
                + "&departure_time=" + departureUnix
                + "&key=" + apiKey;

        String json = HttpUtils.sendGET(url);
        return JsonParser.parseString(json).getAsJsonObject();
    }
}
