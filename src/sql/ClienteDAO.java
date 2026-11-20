package sql;

import clases.Alquiler;
import clases.Cliente;
import clases.Reserva;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import excepciones.IdNoExistenteEx;
import excepciones.NoExistenElementosCargadosEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ClienteDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String CLIENTE_POR_ID =
            "SELECT * FROM cliente WHERE id = ?";

    private static final String LISTAR_CLIENTES =
            "SELECT * FROM cliente; ";

    private static final String INSERT_CLIENTE =
            "INSERT INTO cliente (nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia) VALUES (?, ?, ?, ?, ?, ?)";
    //------------------------------------|Metodos|------------------------------------

    //||AGREGAR_CLIENTES||--> Agrega clientes a la base de datos y retorna el id generado.
    public static int agregarClienteBDD(String nombre, String apellido, String dni, String telefono, String licenciaConducir, LocalDate vencimientoLicencia) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_CLIENTE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, dni);
            ps.setString(4, telefono);
            ps.setString(5, licenciaConducir);

            //ps.setDate(6,  java.sql.Date.valueOf(vencimientoLicencia)); //usamos Date.valueof para pasar el tipo de dato LocalDate a su equivalente en la base de datos (DataTime).
            ps.setString(6, vencimientoLicencia.toString());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerada = rs.getInt(1);
                }
            }
        }
        return idGenerada;
    }

    //|| OBTENER_CLIENTE_POR_ID  ||-->  Metodo para buscar RESERVA_POR_ID retornando el objeto encontrado.
    public static Cliente clientePorIdBDD(int idbuscado) throws SQLException, IdNoExistenteEx {
        Cliente cliente = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(CLIENTE_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    String nombre = rs.getString("nombre");

                    String apellido = rs.getString("apellido");

                    String dni = rs.getString("dni");

                    String telefono = rs.getString("telefono");

                    String licenciaConducir = rs.getString("licenciaConducir");

                    LocalDate vencimientoLicencia = rs.getTimestamp("vencimientoLicencia").toLocalDateTime().toLocalDate();

                    cliente = new Cliente(id, nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia);

                } else {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }
            }
        }
        return cliente;
    }


    //|| OBTENER_LISTA_CLIENTES ||-->  Obtiene lista de clientes de la base de datos.
    public static LinkedHashSet<Cliente> listarClientesBDD() throws SQLException, NoExistenElementosCargadosEx {

        LinkedHashSet<Cliente> clientes = new LinkedHashSet<>();

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(LISTAR_CLIENTES); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String licenciaConducir = rs.getString("licenciaConducir");

                // LocalDate vencimientoLicencia = rs.getDate("vencimientoLicencia").toLocalDate();
                LocalDate vencimientoLicencia =
                        LocalDate.parse(rs.getString("vencimientoLicencia"));

                boolean activo = rs.getInt("activo") == 1;

                Cliente cliente = new Cliente(id, nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia, activo);

                clientes.add(cliente);
            }
        }
        if (clientes.isEmpty()) {
            throw new NoExistenElementosCargadosEx(
                    "No hay clientes cargados en la base de datos."
            );
        }

        return clientes;
    }






}
