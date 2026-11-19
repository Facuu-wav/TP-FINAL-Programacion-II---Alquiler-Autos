package sql;

import java.sql.*;
import java.time.LocalDate;

public class ClienteDAO {

    private static final String INSERT_CLIENTE = "";

    public static int agregarClienteBDD(String nombre, String apellido, String dni, String telefono, String licenciaConducir, LocalDate vencimientoLicencia) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_CLIENTE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido); // Utilizo name() en lugar de toString() porque devuelve excatamente el nombre declarado en el enum.
            ps.setString(3, dni);
            ps.setString(4, telefono);
            ps.setString(5, licenciaConducir);
            ps.setDate(6, vencimientoLicencia); //???

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerada = rs.getInt(1);
                }
            }
        }
        return idGenerada;
    }

}
