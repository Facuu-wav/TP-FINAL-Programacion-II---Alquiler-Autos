package sql;

import clases.Alquiler;
import clases.Cliente;
import clases.Reserva;
import clases.Vehiculo;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import enums.EstadoReserva;
import excepciones.IdNoExistenteEx;
import excepciones.NoExistenElementosCargadosEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ReservaDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String RESERVA_POR_ID =
            "SELECT * FROM reserva WHERE id = ?";

    private static final String LISTAR_RESERVAS =
            "SELECT * FROM reserva";

    private static final String INSERT_RESERVA =
            "INSERT INTO reserva (cliente_id, vehiculo_id, usuario_id, fecha_reserva, fecha_inicio, fecha_fin, estado_reserva) VALUES (? ,? ,? ,? ,? ,? ,?)";

    //------------------------------------|Metodos|------------------------------------

    //||AGREGAR_RESERVA||--> Agrega reserva a la base de datos y retorna el id generado.
    public static int agregarReservaBDD(int clienteid, int vehiculoid, int usuarioid, LocalDateTime fechaReserva, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RESERVA, Statement.RETURN_GENERATED_KEYS)) {

            //les pasamos la clave foranea de cada cliente,vehiculo y usuario relacionados con la reserva.
            ps.setInt(1, clienteid);
            ps.setInt(2, vehiculoid);
            ps.setInt(3, usuarioid);

            ps.setString(4, fechaReserva.toString());
            ps.setString(5, fechaInicio.toString());
            ps.setString(6, fechaFin.toString());

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


    //||OBTENER_RESERVA_POR_ID||--> Metodo para buscar RESERVA_POR_ID RETORNANDO EL OBJETO ENCONTRADO.
    public static Reserva reservaPorIdBDD(int idbuscado) throws SQLException, IdNoExistenteEx{
        Reserva reserva = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(RESERVA_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    Cliente cliente = ClienteDAO.clientePorIdBDD(rs.getInt("cliente_id"));

                    //-> obtenemos el vehiculo en particular de la reserva mediante su clave foranea
                    Vehiculo vehiculo = VehiculoDAO.vehiculoPorIdBDD(rs.getInt("vehiculo_id"));

                    //-> Mediante la clave foranea accedemos al usuario exacto que hizo la reserva
                    Usuario usuario = UsuarioDAO.usuarioPorIdBDD(rs.getInt("usuario_id"));

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

    //||OBTENER_LISTA_DE_RESERVAS||--> retorna una lista de las reservas existentes en la base de datos.
    public static LinkedHashSet<Reserva> listarReservasBDD()
            throws SQLException, IdNoExistenteEx, NoExistenElementosCargadosEx {

        LinkedHashSet<Reserva> reservas = new LinkedHashSet<>();

        try (Connection conn = DBHandler.getConnection();
             PreparedStatement ps = conn.prepareStatement(LISTAR_RESERVAS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");

                Cliente cliente = ClienteDAO.clientePorIdBDD(
                        rs.getInt("cliente_id")
                );

                Vehiculo vehiculo = VehiculoDAO.vehiculoPorIdBDD(
                        rs.getInt("vehiculo_id")
                );

                Usuario usuario = UsuarioDAO.usuarioPorIdBDD(
                        rs.getInt("usuario_id")
                );

                LocalDateTime fechaReserva = rs.getTimestamp("fecha_reserva").toLocalDateTime();

                LocalDate fechaInicio = rs.getTimestamp("fecha_inicio").toLocalDateTime().toLocalDate();

                LocalDate fechaFin = rs.getTimestamp("fecha_fin").toLocalDateTime().toLocalDate();

                EstadoReserva estadoReserva =
                        EstadoReserva.fromString(rs.getString("estado_reserva"));

                Reserva reserva = new Reserva(
                        id,
                        cliente,
                        vehiculo,
                        usuario,
                        fechaReserva,
                        fechaInicio,
                        fechaFin,
                        estadoReserva
                );

                reservas.add(reserva);
            }
        }

        if (reservas.isEmpty()) {
            throw new NoExistenElementosCargadosEx(
                    "No hay reservas cargadas en la base de datos."
            );
        }

        return reservas;
    }



}
