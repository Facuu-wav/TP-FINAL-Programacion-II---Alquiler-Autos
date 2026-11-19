package sql;

import clases.empleados.*;
import enums.TipoUsuario;

import java.sql.*;

public class DBHandler {

    /// WALLY CUANDO HAGAMOS LA BASE DE DATOS HAY QUE ACTUALIZAR ESTO!!!
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Consultas de usuarios

    /// INCOMPLETA!!!!!!!
    public static Usuario buscarUsuario(String nombreUsuario) throws SQLException {

        String sqlquery = "SELECT * FROM usuario WHERE nombreUsuario = ?";

        try (Connection connection = DriverManager.getConnection(URL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {

                preparedStatement.setString(1, nombreUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        // getters acá ?
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre");
                        String apellido = resultSet.getString("apellido");
                        String dni = resultSet.getString("dni");
                        String usuario = resultSet.getString("nombreUsuario");
                        String email = resultSet.getString("email");
                        String contrasenia = resultSet.getString("contrasenia");

                        TipoUsuario tipo = TipoUsuario.valueOf(resultSet.getString("tipoUsuario"));

                        // según el tipo de usuario se instancia el usuario correspondiente.
                        switch (tipo) {

                            case ADMINISTRADOR:
                                return new Administrador();

                            case EMPLEADO:
                                return  new Empleado();

                            case EMPLEADO_LIMPIEZA:
                                return new EmpleadoLimpieza();

                            case EMPLEADO_MANTENIMIENTO:
                                return new EmpleadoMantenimiento();

                            case EMPLEADO_VENTAS:
                                return new EmpleadoVentas();

                            default:
                                return null;
                        }
                    }
                }
            }

        }
        return null;
    }
}
