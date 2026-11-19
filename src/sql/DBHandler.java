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

    public static void autodestruirse() throws SQLException {

        String[] tablas = {
                "empleado",
                "cliente",
                "vehiculo",
                "usuario"
        };

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            conn.setAutoCommit(false);

            try {
                for (String tabla : tablas) {
                    st.executeUpdate("DELETE FROM " + tabla);
                }

                // Reinicia los contadores AUTOINCREMENT
                st.executeUpdate(
                        "DELETE FROM sqlite_sequence"
                );

                conn.commit();

            } catch (SQLException | RuntimeException e) {

                conn.rollback();
                throw e;
            }
        }
    }

}
