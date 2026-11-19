package sql;

import enums.EstadoVehiculo;
import enums.Marca;

import java.math.BigDecimal;
import java.sql.*;

public class VehiculoDAO {

    private static final String INSERT_VEHICULO =
            "INSERT INTO vehiculo (patente, marca, modelo, anio, kilometraje, estado_vehiculo, precio_por_dia) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_VEHICULO =
            "DELETE FROM vehiculo WHERE id = ?";

    public static int agregarVehiculoBDD(String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_VEHICULO, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patente);
            ps.setString(2, marca.name()); // Utilizo name() en lugar de toString() porque devuelve excatamente el nombre declarado en el enum.
            ps.setString(3, modelo);
            ps.setInt(4, anio);
            ps.setInt(5, kilometraje);
            ps.setString(6, estadoVehiculo.name());
            ps.setBigDecimal(7, precioPorDia);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerada = rs.getInt(1);
                }
            }
        }
        return idGenerada;
    }

    public static boolean eliminarVehiculoBDD(int id) throws SQLException {

        boolean eliminado = false;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_VEHICULO)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 1) {
                eliminado = true;
            }
        }
        return eliminado;
    }

}
