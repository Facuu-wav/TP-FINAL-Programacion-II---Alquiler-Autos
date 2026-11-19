package sql;

import clases.Alquiler;
import clases.Cliente;
import clases.Reserva;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import excepciones.IdNoExistenteEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClienteDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String CLIENTE_POR_ID =
            "SELECT * FROM cliente WHERE id = ?";

    private static final String LISTA_CLIENTES =
            "SELECT * FROM cliente; ";

    private static final String INSERT_CLIENTE =
            "INSERT INTO cliente (nombre, apellido, dni, telefono, licencia_conducir, vencimiento_licencia) VALUES (?, ?, ?, ?, ?, ?)";
    //------------------------------------|Metodos|------------------------------------

    //||AGREGAR_CLIENTES||--> Agrega clientes a la base de datos y retorna el id generado.
    public static int agregarClienteBDD(String nombre, String apellido, String dni, String telefono, String licenciaConducir, LocalDate vencimientoLicencia) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_CLIENTE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido); // Utilizo name() en lugar de toString() porque devuelve excatamente el nombre declarado en el enum.
            ps.setString(3, dni);
            ps.setString(4, telefono);
            ps.setString(5, licenciaConducir);
            ps.setDate(6,  java.sql.Date.valueOf(vencimientoLicencia)); //usamos Date.valueof para pasar el tipo de dato LocalDate a su equivalente en la base de datos (DataTime).

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

                    String licenciaConducir = rs.getString("licencia_conducir");

                    LocalDate vencimientoLicencia = rs.getTimestamp("vencimiento_licencia").toLocalDateTime().toLocalDate();


                    cliente = new Cliente(id, nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia);

                } else {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }
            }
        }

        return cliente;
    }


    //|| OBTENER_LISTA_CLIENTES ||-->  Obtiene lista de clientes de la base de datos.

    public static ArrayList<Cliente> listaClientesBDD() throws SQLException, IdNoExistenteEx {
        ArrayList<Cliente> clientes = new ArrayList<>();

        try(Connection connection = DBHandler.getConnection();  PreparedStatement statement = connection.prepareStatement(LISTA_CLIENTES)) {

            try(ResultSet rs = statement.executeQuery()){

                while(rs.next()){
                    int id = rs.getInt("id");

                    String nombre = rs.getString("nombre");

                    String apellido = rs.getString("apellido");

                    String dni = rs.getString("dni");

                    String telefono = rs.getString("telefono");

                    String licenciaConducir = rs.getString("licencia_conducir");

                    LocalDate vencimientoLicencia = rs.getTimestamp("vencimiento_licencia").toLocalDateTime().toLocalDate();

                    listaClientesBDD().add(new Cliente(id, nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia));

                }
            }
        }

        return clientes;
    }






}
