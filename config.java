package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    private static Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            System.err.println("Erreur chargement config.properties");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
