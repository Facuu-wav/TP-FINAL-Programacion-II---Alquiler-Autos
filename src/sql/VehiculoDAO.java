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
            "INSERT INTO vehiculo (patente, marca, modelo, anio, kilometraje, estado_vehiculo, precio_por_dia) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_VEHICULO =
            "DELETE FROM vehiculo WHERE id = ?";

    private static final String LISTAR_VEHICULOS =
            "SELECT * FROM vehiculo";


    private static final String VEHICULO_POR_ID =
            "SELECT * FROM reserva WHERE id = ?";

    //------------------------------------|Metodos|------------------------------------

    //||OBTENER_VEHICULO_POR_ID||--> Metodo para buscar VEHICULO_POR_ID y retornarlo como objeto..
    public static Vehiculo vehiculoPorIdBDD(int idbuscado) throws SQLException, IdNoExistenteEx {
        Vehiculo vehiculo = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(VEHICULO_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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

                    vehiculo = new Vehiculo(id, patente, marca, modelo, anio, kilometraje,  estadoVehiculo, precioPorDia);
                }

                if (!rs.next()) {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }
            }
        }
        return vehiculo;
    }


    //||AGREGAR_VEHICULO||--> Retorna el id generado del objeto agregado a la base de datos.
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

    /*
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
    */

    public static void cambiarEstadoVehiculoBDD() {

    }



}
