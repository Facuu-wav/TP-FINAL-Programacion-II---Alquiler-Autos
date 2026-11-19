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

public class AlquilerDAO {

    //------------------------------------|Querys|------------------------------------

    private final String ALQUILERES =
            "SELECT * FROM alquiler";

    private static final String ALQUILER_POR_ID =
            "SELECT * FROM cliente WHERE id = ?";

    //------------------------------------|Metodos|------------------------------------

    //|| OBTENER_RESERVA_POR_ID  ||-->  Metodo para buscar RESERVA_POR_ID retornando el objeto encontrado.
    public static Alquiler clientePorId(int idbuscado) throws SQLException, IdNoExistenteEx {
        Alquiler alquiler = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(ALQUILER_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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

                    new Alquiler(id, reserva, usuario, fechaentrega, fechadevolucion, kilometrajeinicial, kilometrajefinal, estadoalquiler);

                } else {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }
            }
        }

        return alquiler;
    }


    //||OBTENER_LISTA_DE_ALQUILERES||--> retorna una lista de los clientes existentes en la base de datos.
    public ArrayList<Alquiler> listaAlquileres() throws SQLException, IdNoExistenteEx {
       ArrayList<Alquiler> Alquileres = new ArrayList<>();

       try(Connection connection = DBHandler.getConnection();  PreparedStatement statement = connection.prepareStatement(ALQUILERES)) {

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

                   Alquileres.add(new Alquiler(id, reserva, usuario, fechaentrega, fechadevolucion, kilometrajeinicial, kilometrajefinal, estadoalquiler));

               }

               if(Alquileres.isEmpty())throw new ;
           }
       }

       return Alquileres;
    }
}
