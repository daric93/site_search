package rgz;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by darya on 11.12.15.
 */
public class QueryToDatabase {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void fetchData(Map<String, String[]> map, ResultConsumer consumer) throws SQLException, IOException {
        List<String> values = new ArrayList<>();
        String query = createQuery(map, values);
        try (Connection connection = DriverManager.getConnection(DB_URL, "darya", "zeytin2015");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            consumer.accept(resultSet);
        }
    }

    static void fetchImage(int id, ResultConsumer consumer) throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "darya", "zeytin2015");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT Image FROM Phones WHERE ID = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            consumer.accept(resultSet);
        }
    }

    private static String createQuery(Map<String, String[]> map, List<String> values) {
        StringBuilder query = new StringBuilder("SELECT ID,Producer FROM Phones WHERE ");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            query.append("( ");
            for (int i = 0; i < entry.getValue().length; i++) {
                if (Objects.equals(entry.getKey(), "Price")) {
                    query.append(entry.getKey());
                    query.append(" <= ? or ");
                } else {
                    query.append(entry.getKey());
                    query.append(" = ? or ");
                }
                values.add(entry.getValue()[i]);
            }
            query.delete(query.length() - 4, query.length());
            query.append(") and ");
        }
        query.delete(query.length() - 5, query.length());
        return query.toString();
    }
}
