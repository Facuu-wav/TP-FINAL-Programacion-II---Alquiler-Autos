package sql;

import clases.Alquiler;
import clases.Reserva;
import clases.Vehiculo;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import enums.EstadoVehiculo;
import enums.Marca;
import excepciones.IdNoExistenteEx;
import excepciones.NoExistenElementosCargadosEx;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VehiculoDAO {
    //------------------------------------|Querys|------------------------------------

    private static final String INSERT_VEHICULO =
            "INSERT INTO vehiculo (patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String LISTAR_VEHICULOS =
            "SELECT * FROM vehiculo";

    private static final String VEHICULO_POR_ID =
            "SELECT * FROM vehiculo WHERE id = ?";

    private static final String UPDATE_VEHICULO =
            "UPDATE vehiculo SET estadoVehiculo = ? WHERE id = ?";

    private static final String PATENTE_EXISTE =
            "SELECT 1 FROM vehiculo WHERE patente = ? LIMIT 1";

    //------------------------------------|Metodos|------------------------------------

    //||OBTENER_VEHICULO_POR_ID||--> Metodo para buscar VEHICULO_POR_ID y retornarlo como objeto..
    public static Vehiculo vehiculoPorIdBDD(int idBuscado) throws SQLException, IdNoExistenteEx {

        try (Connection connection = DBHandler.getConnection(); PreparedStatement ps = connection.prepareStatement(VEHICULO_POR_ID)) {

            ps.setInt(1, idBuscado);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    throw new IdNoExistenteEx(
                            "ERROR: Ingresar un ID existente."
                    );
                }

                int id = rs.getInt("id");
                String patente = rs.getString("patente");

                Marca marca = Marca.fromString(rs.getString("marca"));

                String modelo = rs.getString("modelo");
                int anio = rs.getInt("anio");
                int kilometraje = rs.getInt("kilometraje");

                EstadoVehiculo estadoVehiculo =
                        EstadoVehiculo.fromString(rs.getString("estadoVehiculo"));

                BigDecimal precioPorDia = rs.getBigDecimal("precioPorDia");

                return new Vehiculo(
                        id, patente, marca, modelo, anio,
                        kilometraje, estadoVehiculo, precioPorDia
                );
            }
        }
    }

    //||AGREGAR_VEHICULO||--> Retorna el id generado del objeto agregado a la base de datos.
    public static int agregarVehiculoBDD(String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) throws SQLException {

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_VEHICULO, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patente);
            ps.setString(2, marca.name());
            ps.setString(3, modelo);
            ps.setInt(4, anio);
            ps.setInt(5, kilometraje);
            ps.setString(6, estadoVehiculo.name());
            ps.setBigDecimal(7, precioPorDia);

            if (ps.executeUpdate() != 1) {
                throw new SQLException("No se pudo registrar el vehículo.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("No se pudo recuperar el ID generado.");
        }
    }

    /// Revisar luego
    //||OBTENER_LISTA_DE_VEHICULOS||--> retorna una lista de los clientes existentes en la base de datos.
    public static ArrayList<Vehiculo> listaVehiculosBDD() throws SQLException, NoExistenElementosCargadosEx, IdNoExistenteEx {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();

        try(Connection connection = DBHandler.getConnection();  PreparedStatement statement = connection.prepareStatement(LISTAR_VEHICULOS)) {

            try(ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt("id");

                    String patente = rs.getString("patente");

                    Marca marca = Marca.fromString
                            (rs.getString("marca"));

                    String modelo = rs.getString("modelo");

                    int anio = rs.getInt("anio");

                    int kilometraje = rs.getInt("kilometraje");

                    EstadoVehiculo estadoVehiculo = EstadoVehiculo.fromString
                            (rs.getString("estado_vehiculo"));

                    BigDecimal precioPorDia = rs.getBigDecimal("precio_por_dia");

                    vehiculos.add( new Vehiculo(id, patente, marca, modelo, anio, kilometraje,  estadoVehiculo, precioPorDia));


                }

                if(vehiculos.isEmpty())throw new NoExistenElementosCargadosEx("ERROR: no hay datos cargados en el sistema.");

            }
        }

        return vehiculos;
    }

    public static boolean actualizarEstadoVehiculoBDD(int idVehiculo, EstadoVehiculo estadoVehiculo) throws SQLException {

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_VEHICULO)) {

            ps.setString(1, estadoVehiculo.name());
            ps.setInt(2, idVehiculo);

            return ps.executeUpdate() == 1;
        }
    }

    public static boolean existePatenteBDD(String patente) throws SQLException {

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(PATENTE_EXISTE)) {

            ps.setString(1, patente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


}
