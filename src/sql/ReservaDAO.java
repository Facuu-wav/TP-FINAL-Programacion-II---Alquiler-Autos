package sql;

import clases.Cliente;
import clases.Reserva;
import clases.Vehiculo;
import clases.empleados.Usuario;
import enums.EstadoReserva;
import excepciones.IdNoExistenteEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String RESERVA_POR_ID = "SELECT * FROM reserva WHERE id = ?";

    private static final String INSERT_RESERVA =
            "INSERT INTO reserva (cliente_id, vehiculo_id, usuario_id, fecha_reserva, fecha_inicio, fecha_fin, estado_reserva)(? ,? ,? ,? ,? ,? ,?)";

    //------------------------------------|Metodos|------------------------------------

    //||OBTENER_RESERVA_POR_ID||--> Metodo para buscar RESERVA_POR_ID RETORNANDO EL OBJETO ENCONTRADO.
    public static Reserva reservaPorId(int idbuscado) throws SQLException, IdNoExistenteEx{
        Reserva reserva = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(RESERVA_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    Cliente cliente = ClienteDAO.clientePorId(rs.getInt("cliente_id"));

                    //-> obtenemos el vehiculo en particular de la reserva mediante su clave foranea
                    Vehiculo vehiculo = VehiculoDAO.vehiculoPorId(rs.getInt("vehiculo_id"));

                    //-> Mediante la clave foranea accedemos al usuario exacto que hizo la reserva
                    Usuario usuario = UsuarioDAO.usuarioPorId(rs.getInt("usuario_id"));

                    LocalDateTime fechareserva = rs.getTimestamp("fecha_reserva").toLocalDateTime();

                    LocalDate fechaInicio = rs.getTimestamp("fecha_inicio").toLocalDateTime().toLocalDate();

                    LocalDate fechafin = rs.getTimestamp("fecha_fin").toLocalDateTime().toLocalDate();

                    EstadoReserva estadoreserva = EstadoReserva.fromString
                            (rs.getString("estado_reserva"));

                    reserva = new Reserva(id, cliente, vehiculo, usuario, fechareserva, fechaInicio, fechafin, estadoreserva);

                } else {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }

            }
        }

        return reserva;
    }

    //||AGREGAR_RESERVA||--> Agrega reserva a la base de datos y retorna el id generado.

    public static int agregarReservaBDD(int clienteid, int vehiculoid, int usuarioid, LocalDateTime fechaReserva, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RESERVA, Statement.RETURN_GENERATED_KEYS)) {

            //les pasamos la clave foranea de cada cliente,vehiculo y usuario relacionados con la reserva.
            ps.setInt(1, clienteid);
            ps.setInt(2, vehiculoid);
            ps.setInt(3, usuarioid);

            ps.setTimestamp(4, java.sql.Timestamp.valueOf(fechaReserva));
            ps.setDate(5, java.sql.Date.valueOf(fechaInicio));
            ps.setDate(6,  java.sql.Date.valueOf(fechaFin));

            ps.setString(7, estadoReserva.name());

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
