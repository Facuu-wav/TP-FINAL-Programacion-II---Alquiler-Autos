package sql;

import clases.empleados.Usuario;
import clases.empleados.*;
import enums.TipoUsuario;

import java.sql.*;

public class DBHandler {

    /// WALLY CUANDO HAGAMOS LA BASE DE DATOS HAY QUE ACTUALIZAR ESTO!!!
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        return connection;
    }

}
