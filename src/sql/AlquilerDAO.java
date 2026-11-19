package sql;

import clases.Alquiler;
import clases.Reserva;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import enums.EstadoReserva;
import enums.EstadoVehiculo;
import excepciones.IdNoExistenteEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AlquilerDAO {

    private final String ALQUILERES = "SELECT * FROM alquiler; ";

    //----------------------------------------------------------|GENERAR LISTA DE ALQUILERES|----------------------------------------------------------
    public ArrayList<Alquiler> listaAlquileres() throws SQLException, IdNoExistenteEx {
       ArrayList<Alquiler> Alquileres = new ArrayList<>();

       try(Connection connection = DBHandler.getConnection() ){
           PreparedStatement statement = connection.prepareStatement(ALQUILERES);

           try(ResultSet rs = statement.executeQuery()){

               while(rs.next()){
                   int id = rs.getInt("id");

                   //-> obtenemos los datos de la RESERVA en particular mediante su clave foranea.
                   Reserva reserva = ReservaDAO.reservaPorId(rs.getInt("reserva_id")); //FALTA METODO PARA INSTANCIAR EL OBJETO CON SUS DATOS!!!

                   //-> obtenemos los datos del USUARIO en particular mediante su clave foranea.
                   Usuario usuario = UsuarioDAO.usuarioPorId(rs.getInt("id_usuario"));

                   LocalDateTime fechaentrega = rs.getTimestamp("fecha_entrega").toLocalDateTime();

                   LocalDateTime fechadevolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime();

                   int kilometrajeinicial = rs.getInt("kilometraje_inicial");

                   int kilometrajefinal = rs.getInt("kilometraje_final");

                   EstadoAlquiler estadoalquiler = EstadoAlquiler.fromString
                           (rs.getString("estado_alquiler"));

                   Alquileres.add(new A(id, reserva, usuario, fechaentrega, fechadevolucion, kilometrajeinicial, kilometrajefinal, estadoalquiler));

               }


           }
       }

       return Alquileres;
    }
}
