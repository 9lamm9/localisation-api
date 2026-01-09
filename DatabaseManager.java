package db;

import java.sql.*;

public class DatabaseManager {

    private Connection conn;

    public DatabaseManager() throws Exception {
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sae302",
                "root",
                "password"
        );
    }

    public void log(String from, String to, String mode) throws Exception {
        String sql = "INSERT INTO requests(from_city,to_city,mode,timestamp) VALUES (?,?,?,NOW())";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, from);
        ps.setString(2, to);
        ps.setString(3, mode);
        ps.executeUpdate();
    }
}
