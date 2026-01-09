package utils;

import java.io.*;
import java.net.*;

public class HttpUtils {

    public static String sendGET(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = in.readLine()) != null) {
            sb.append(line);
        }

        in.close();
        conn.disconnect();
        return sb.toString();
    }
}
