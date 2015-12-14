package hello.world;

import java.io.IOException;
import java.sql.*;

/**
 * Created by darya on 09.12.15.
 */
public class ImageQuery {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    static void fetchData(int id,ResultSetConsumer consumer) throws ClassNotFoundException, SQLException, IOException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        try (Connection conn = DriverManager.getConnection(DB_URL, "darya", "zeytin2015");
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT Image FROM Image WHERE ID = ?")) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            consumer.accept(resultSet);
        }
    }
    public interface ResultSetConsumer {
        void accept(ResultSet t) throws SQLException, IOException;
    }
}
