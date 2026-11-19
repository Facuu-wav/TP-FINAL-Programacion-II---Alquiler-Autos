package sql;

import java.sql.*;

public class DBHandler {

    private static final String URL = "jdbc:sqlite:alquiler_autos.db";

    public static Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(URL);

        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }

        return connection;
    }

}
