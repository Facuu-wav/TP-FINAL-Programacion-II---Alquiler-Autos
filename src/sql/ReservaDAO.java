package sql;

import clases.Cliente;
import clases.Reserva;
import clases.Vehiculo;
import clases.empleados.Usuario;
import enums.EstadoReserva;
import excepciones.IdNoExistenteEx;

import java.sql.*;
import java.time.LocalDateTime;

public class ReservaDAO {

    private static final String RESERVA_POR_ID = "SELECT * FROM reserva WHERE id = ?";

    //--> Metodo para buscar RESERVA_POR_ID RETORNANDO EL OBJETO ENCONTRADO.
    public Reserva reservaPorId(int idbuscado) throws SQLException, IdNoExistenteEx{
        Reserva reserva = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(RESERVA_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    Cliente cliente = rs.getString("cliente");

                    //-> obtenemos el vehiculo en particular de la reserva mediante su clave foranea
                    Vehiculo vehiculo = VehiculoDAO.vehiculoPorId(rs.getInt("vehiculo_id"));

                    //-> Mediante la clave foranea accedemos al usuario exacto que hizo la reserva
                    Usuario usuario = UsuarioDAO.usuarioPorId(rs.getInt("id_usuario"));

                    LocalDateTime fechareserva = rs.getTimestamp("fecha_reserva").toLocalDateTime();

                    LocalDateTime fechaInicio = rs.getTimestamp("fecha_inicio").toLocalDateTime();

                    LocalDateTime fechafin = rs.getTimestamp("fecha_fin").toLocalDateTime();

                    EstadoReserva estadoreserva = EstadoReserva.fromString
                            (rs.getString("estado_reserva"));

                    reserva = new Reserva(id, cliente, vehiculo, usuario, fechareserva, fechaInicio, fechafin, estadoreserva);

                }

                if (!rs.next()){
                    trow new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }

            }
        }

        return reserva;
    }
}
