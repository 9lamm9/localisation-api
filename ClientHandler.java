package server;

import java.io.*;
import java.net.*;
import api.*;
import com.google.gson.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String req = in.readLine();
            String path = req.split(" ")[1];

            String from = param(path, "from");
            String to = param(path, "to");
            String mode = param(path, "mode");
            String dep = param(path, "departure_time");

            long depUnix = dep.equals("now") ? System.currentTimeMillis()/1000 : Long.parseLong(dep);

            DistanceMatrixClient dm = new DistanceMatrixClient();
            GeocodingClient geo = new GeocodingClient();
            OpenMeteoClient meteo = new OpenMeteoClient();

            JsonObject coord = geo.geocode(to);
            double lat = geo.lat(coord);
            double lon = geo.lon(coord);

            JsonObject dmData = dm.getRoute(from, to, mode, depUnix);

            JsonObject el = dmData.get("rows").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("elements").getAsJsonArray()
                    .get(0).getAsJsonObject();

            long duration = el.get("duration").getAsJsonObject().get("value").getAsLong();
            String distTxt = el.get("distance").getAsJsonObject().get("text").getAsString();

            long arrival = depUnix + duration;

            JsonObject met = meteo.getWeather(lat, lon, arrival);
            double temp = met.get("hourly").getAsJsonObject()
                    .get("temperature_2m").getAsJsonArray().get(0).getAsDouble();

            HttpServer.db.log(from, to, mode);

            JsonObject resp = new JsonObject();
            resp.addProperty("from", from);
            resp.addProperty("to", to);
            resp.addProperty("distance", distTxt);
            resp.addProperty("temperature", temp);

            out.write("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n");
            out.write(resp.toString());
            out.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String param(String path, String key) {
        String[] p = path.split("\\?");
        if (p.length < 2) return null;
        for (String s : p[1].split("&")) {
            if (s.startsWith(key + "=")) return s.split("=")[1];
        }
        return null;
    }
}
