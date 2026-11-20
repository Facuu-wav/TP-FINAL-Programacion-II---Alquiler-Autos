package sql;

import clases.Alquiler;
import clases.Reserva;
import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import enums.EstadoVehiculo;
import excepciones.IdNoExistenteEx;
import excepciones.NoExistenElementosCargadosEx;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AlquilerDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String LISTAR_ALQUILERES =
            "SELECT * FROM alquiler";

    private static final String ALQUILER_POR_ID =
            "SELECT * FROM cliente WHERE id = ?";

    private static final String INSERT_ALQUILER =
            "INSERT INTO alquiler (reserva_id, usuario_id, fecha_entrega, fechade_volucion, kilometraje_inicial, kilometraje_final, estado_alquiler) VALUES (?, ?, ?, ?, ? ,? ,?)";


    //------------------------------------|Metodos|------------------------------------

    //||AGREGAR_ALQUILERES||--> Agrega alquileres a la base de datos y retorna el id generado.
    public static int agregarAlquilerBDD(int reservaid, int usuarioid, LocalDateTime fechaEntrega, LocalDateTime fechaDevolucion, int kilometrajeInicial, int kilometrajeFinal, EstadoAlquiler estadoAlquiler) throws SQLException {

        int idGenerada = -1;

        try (Connection conn = DBHandler.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ALQUILER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reservaid);
            ps.setInt(2, usuarioid);
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(fechaEntrega));
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(fechaDevolucion));
            ps.setInt(5, kilometrajeInicial);
            ps.setInt(6, kilometrajeFinal);
            ps.setString(7, estadoAlquiler.name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerada = rs.getInt(1);
                }
            }
        }
        return idGenerada;
    }

    public static boolean finalizarAlquilerBDD(
            int idAlquiler,
            int idVehiculo,
            int kilometrajeFinal,
            EstadoVehiculo estadoVehiculo
    ) throws SQLException {

        String sqlAlquiler = """
        UPDATE alquiler
        SET kilometrajeFinal = ?, estadoAlquiler = ?
        WHERE id = ? AND estadoAlquiler = ?
        """;

        String sqlVehiculo = """
        UPDATE vehiculo
        SET kilometraje = ?, estadoVehiculo = ?
        WHERE id = ?
        """;

        try (Connection conn = DBHandler.getConnection()) {

            conn.setAutoCommit(false);

            try {
                // Actualizar alquiler
                try (PreparedStatement ps = conn.prepareStatement(sqlAlquiler)) {

                    ps.setInt(1, kilometrajeFinal);
                    ps.setString(2, EstadoAlquiler.FINALIZADO.name());
                    ps.setInt(3, idAlquiler);
                    ps.setString(4, EstadoAlquiler.ACTIVO.name());

                    if (ps.executeUpdate() != 1) {
                        conn.rollback();
                        return false;
                    }
                }

                // Actualizar vehículo
                try (PreparedStatement ps = conn.prepareStatement(sqlVehiculo)) {

                    ps.setInt(1, kilometrajeFinal);
                    ps.setString(2, estadoVehiculo.name());
                    ps.setInt(3, idVehiculo);

                    if (ps.executeUpdate() != 1) {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    //|| OBTENER_ALQUILER_POR_ID  ||-->  Metodo para buscar RESERVA_POR_ID retornando el objeto encontrado.

    public static Alquiler alquilerPorIdBDD(int idbuscado) throws SQLException, IdNoExistenteEx {
        Alquiler alquiler = null;

        try(Connection connection = DBHandler.getConnection()){
            PreparedStatement ps = connection.prepareStatement(ALQUILER_POR_ID);

            ps.setInt(1, idbuscado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    //-> obtenemos los datos de la RESERVA en particular mediante su clave foranea.
                    Reserva reserva = ReservaDAO.reservaPorIdBDD(rs.getInt("reserva_id")); //FALTA METODO PARA INSTANCIAR EL OBJETO CON SUS DATOS!!!

                    //-> obtenemos los datos del USUARIO en particular mediante su clave foranea.
                    Usuario usuario = UsuarioDAO.usuarioPorIdBDD(rs.getInt("id_usuario"));

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

    public static ArrayList<Alquiler> listaAlquileresBDD() throws SQLException, NoExistenElementosCargadosEx, IdNoExistenteEx {
       ArrayList<Alquiler> Alquileres = new ArrayList<>();

       try(Connection connection = DBHandler.getConnection();  PreparedStatement statement = connection.prepareStatement(LISTAR_ALQUILERES)) {

           try(ResultSet rs = statement.executeQuery()){

               while(rs.next()){
                   int id = rs.getInt("id");

                   //-> obtenemos los datos de la RESERVA en particular mediante su clave foranea.
                   Reserva reserva = ReservaDAO.reservaPorIdBDD(rs.getInt("reserva_id")); //FALTA METODO PARA INSTANCIAR EL OBJETO CON SUS DATOS!!!

                   //-> obtenemos los datos del USUARIO en particular mediante su clave foranea.
                   Usuario usuario = UsuarioDAO.usuarioPorIdBDD(rs.getInt("id_usuario"));

                   LocalDateTime fechaentrega = rs.getTimestamp("fecha_entrega").toLocalDateTime();

                   LocalDateTime fechadevolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime();

                   int kilometrajeinicial = rs.getInt("kilometraje_inicial");

                   int kilometrajefinal = rs.getInt("kilometraje_final");

                   EstadoAlquiler estadoalquiler = EstadoAlquiler.fromString
                           (rs.getString("estado_alquiler"));

                   Alquileres.add(new Alquiler(id, reserva, usuario, fechaentrega, fechadevolucion, kilometrajeinicial, kilometrajefinal, estadoalquiler));

               }

               if(Alquileres.isEmpty())throw new NoExistenElementosCargadosEx("ERROR: no hay datos cargados en el sistema.");

           }
       }

       return Alquileres;
    }

}
