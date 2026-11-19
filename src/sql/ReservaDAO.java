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
    private final String RESERVA_POR_ID = "SELECT * FROM reserva WHILE id = ?";

    //--> Metodo para buscar RESERVA_POR_ID RETORNANDO EL OBJETO ENCONTRADO.
    public Reserva reservaPorId(int idbuscado) throws SQLException, IdNoExistenteEx{
        Reserva reserva = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(RESERVA_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("id") == idbuscado) {
                        int id = rs.getInt("id");

                        Cliente cliente = rs.getString("cliente");

                        Vehiculo vehiculo = rs.getString("vehiculo");

                        Usuario usuario = rs.getString("usuario");

                        LocalDateTime fechareserva = rs.getTimestamp("fecha_reserva");

                        LocalDateTime fechaInicio = rs.getTimestamp("fecha_inicio").toLocalDateTime();

                        LocalDateTime fechafin = rs.getTimestamp("fecha_fin").toLocalDateTime();

                        EstadoReserva estadoreserva = EstadoReserva.fromString
                                (rs.getString("estado_reserva"));

                        reserva = new Reserva(id, cliente, vehiculo, usuario, fechareserva, fechaInicio, fechafin, estadoreserva);
                    }
                }

                if (reserva == null) trow new IdNoExistenteEx("ERROR: Ingresar un id existente.");

            }
        }catch (SQLException | IdNoExistenteEx e){
            e.printStackTrace();
        }

        return reserva;
    }
}
