package sql;

import clases.empleados.Usuario;

import java.sql.*;

public class BDHandler {

    private static final String url = "jdbc:sqlite:database.db";

    // Consultas de usuarios
    public static Usuario buscarUsuario(String nombreUsuario) {

        String sqlquery = "SELECT * FROM usuario WHERE nombreUsuario = ?";

        try (Connection connection = DriverManager.getConnection(url)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlquery)) {

                preparedStatement.setString(1, nombreUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        // getters acá

                        return new Usuario();


                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void verificarContrasenia() {

    }
}
