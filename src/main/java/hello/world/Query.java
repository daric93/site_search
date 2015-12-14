package hello.world;

import java.io.IOException;
import java.sql.*;

/**
 * Created by darya on 08.12.15.
 */
public class Query {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    static void fetchData(ResultSetConsumer consumer) throws ClassNotFoundException, SQLException, IOException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        try (Connection conn = DriverManager.getConnection(DB_URL, "darya", "zeytin2015");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT LastName FROM Persons WHERE City = 'Kharkiv'")) {
            consumer.accept(rs);
        }
    }

    public interface ResultSetConsumer {
        void accept(ResultSet t) throws SQLException, IOException;
    }
}

