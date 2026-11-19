package sql;

import clases.Alquiler;
import clases.Reserva;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import enums.EstadoReserva;
import enums.EstadoVehiculo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AlquilerDAO {

    private final String ALQUILERES = "SELECT * FROM Vehiculo; ";

    //----------------------------------------------------------|GENERAR LISTA DE ALQUILERES|----------------------------------------------------------
    public ArrayList<Alquiler> listaAlquileres(){
       ArrayList<Alquiler> Alquileres = new ArrayList<>();

       try(Connection connection = BDHandler.getConnection() ){
           PreparedStatement statement = connection.prepareStatement(ALQUILERES);

           try(ResultSet rs = statement.executeQuery()){
               while(rs.next()){
                   int id = rs.getInt("id");

                   Reserva reserva = rs.getString("reserva"); //FALTA METODO PARA INSTANCIAR EL OBJETO CON SUS DATOS!!!

                   Usuario usuario = rs.getString("usuario"); //FALTA METODO PARA INSTANCIAR EL OBJETO CON SUS DATOS!!!

                   LocalDateTime fechaentrega = rs.getTimestamp("fecha_entrega");

                   LocalDateTime fechadevolucion = rs.getTimestamp("fecha_devolucion");

                   int kilometrajeinicial = rs.getInt("kilometraje_inicial");

                   int kilometrajefinal = rs.getInt("kilometraje_final");

                   EstadoAlquiler estadoalquiler = EstadoAlquiler.fromString
                           (rs.getString("estado_alquiler"));

                   Alquileres.add(new Alquiler(id, reserva, usuario, fechaentrega, fechadevolucion, kilometrajeinicial, kilometrajefinal, estadoalquiler))

               }
           }


       }catch (SQLException e){
           e.printStackTrace();
       }


       return Alquileres;
    }
}
