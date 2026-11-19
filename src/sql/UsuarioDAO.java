package sql;

import clases.empleados.Usuario;
import clases.empleados.*;
import enums.TipoEmpleado;
import excepciones.IdNoExistenteEx;

import java.math.BigDecimal;
import java.sql.*;

public class UsuarioDAO {

    //------------------------------------|Querys|------------------------------------

    private static final String USUARIO_POR_ID = "SELECT * FROM usuario WHERE id = ?";

    private static final String INSERT_USUARIO =
            "INSERT INTO usuario (nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_EMPLEADO =
            "INSERT INTO empleado (idUsuario, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas) VALUES (?, ?, ?, ?, ?)";

    private static final String BUSCAR_USUARIO_NOMBRE =
            """
            SELECT
                u.id,
                u.nombre,
                u.apellido,
                u.dni,
                u.nombreUsuario,
                u.email,
                u.contrasenia,
                u.tipoUsuario,
                e.tipoEmpleado,
                e.legajo,
                e.pagoPorHora,
                e.horasTrabajadas
            FROM usuario u
            LEFT JOIN empleado e ON u.id = e.idUsuario
            WHERE u.nombreUsuario = ?
            """;

    //------------------------------------|Metodos|------------------------------------


    /*
    //||OBTENER_USUARIO_ID||--> Metodo para buscar USUARIO_POR_ID retornando el objeto encontrado. //PENTDIENTE A REFACTORIZAR
    public static Usuario usuarioPorIdBDD(int idbuscado) throws SQLException, IdNoExistenteEx {
        Usuario usuario = null;

        try (Connection connection = DBHandler.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(USUARIO_POR_ID);

            ps.setInt(1, idbuscado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    String nombre = rs.getString("nombre");

                    String apellido = rs.getString("apellido");

                    String dni = rs.getString("dni");

                    String nombre_usuario = rs.getString("nombre_usuario");

                    String email = rs.getString("email");

                    String contrasenia = rs.getString("contrasenia");

                    TipoUsuario tipo_usuario = TipoUsuario.fromString
                            (rs.getString("tipo_usuario"));


                    switch (tipo_usuario) {
                        //->si el usuario es administrador instancia un administrador.
                        case ADMINISTRADOR -> usuario = new Administrador(
                                id,
                                nombre,
                                apellido,
                                dni,
                                nombre_usuario,
                                email,
                                contrasenia,
                                tipo_usuario);

                        //-> si tipo de usuario es algun tipo de empleado entonces instancia el tipo de empleado en particular.
                        case EMPLEADO_LIMPIEZA,
                             EMPLEADO_MANTENIMIENTO,
                             EMPLEADO_VENTAS -> usuario = obtenerEmpleado(
                                id,
                                nombre,
                                apellido,
                                dni,
                                nombre_usuario,
                                email,
                                contrasenia,
                                tipo_usuario,
                                rs.getInt("legajo"),
                                rs.getBigDecimal("pago_por_hora"),
                                rs.getInt("horas_trabajadas"));

                    }
                } else {
                    throw new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }

            }

            return usuario;
        }
    }
     */

    private static int insertarUsuario(Connection conn, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, String tipoUsuario) throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(INSERT_USUARIO)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, dni);
            ps.setString(4, nombreUsuario);
            ps.setString(5, email);
            ps.setString(6, contrasenia);
            ps.setString(7, tipoUsuario);

            if (ps.executeUpdate() != 1) {
                throw new SQLException("No se pudo registrar el usuario.");
            }
        }

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo recuperar el ID generado.");
    }

    public static int agregarAdministradorBDD(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) throws SQLException {

        try (Connection conn = DBHandler.getConnection()) {

            conn.setAutoCommit(false);

            try {
                int idGenerado = insertarUsuario(conn, nombre, apellido, dni, nombreUsuario, email, contrasenia, "ADMINISTRADOR");

                conn.commit();

                return idGenerado;

            } catch (SQLException | RuntimeException e) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }

                throw e;
            }
        }
    }

    public static int agregarEmpleadoBDD(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoEmpleado tipoEmpleado, int legajo, BigDecimal pagoPorHora, int horasTrabajadas) throws SQLException {

        try (Connection conn = DBHandler.getConnection()) {

            conn.setAutoCommit(false);

            try {

                int idGenerado = insertarUsuario(conn, nombre, apellido, dni, nombreUsuario, email, contrasenia, "EMPLEADO");

                try (PreparedStatement ps = conn.prepareStatement(INSERT_EMPLEADO)) {

                    ps.setInt(1, idGenerado);
                    ps.setString(2, tipoEmpleado.name());
                    ps.setInt(3, legajo);
                    ps.setBigDecimal(4, pagoPorHora);
                    ps.setInt(5, horasTrabajadas);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("No se pudo registrar el empleado.");
                    }
                }

                conn.commit();

                return idGenerado;

            } catch (SQLException | RuntimeException e) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }

                throw e;
            }
        }
    }

    /*
    //->dependencia del metodo usuarioPorId para instanciar los diferentes tipos de empleados.
    private static Empleado obtenerEmpleado(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoUsuario tipoUsuario, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        Empleado empleado = null;

        switch (tipoUsuario) {
            case EMPLEADO_LIMPIEZA ->
                    empleado = new EmpleadoLimpieza(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario, legajo, pagoporhora, horastrabajadas);

            case EMPLEADO_MANTENIMIENTO ->
                    empleado = new EmpleadoMantenimiento(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario, legajo, pagoporhora, horastrabajadas);

            case EMPLEADO_VENTAS ->
                    empleado = new EmpleadoVentas(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario, legajo, pagoporhora, horastrabajadas);

        }
        return empleado;
    }
    */

    public static Usuario buscarUsuario(String nombreUsuario) throws SQLException {

        try (Connection conn = DBHandler.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUSCAR_USUARIO_NOMBRE)) {

            ps.setString(1, nombreUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                String usuario = rs.getString("nombreUsuario");
                String email = rs.getString("email");
                String contrasenia = rs.getString("contrasenia");

                String tipoUsuario = rs.getString("tipoUsuario");

                switch (tipoUsuario) {

                    case "ADMINISTRADOR":

                        return new Administrador(
                                id,
                                nombre,
                                apellido,
                                dni,
                                usuario,
                                email,
                                contrasenia
                        );

                    case "EMPLEADO":

                        String tipoEmpleadoTexto = rs.getString("tipoEmpleado");

                        if (tipoEmpleadoTexto == null) {
                            throw new SQLException(
                                    "El usuario está registrado como empleado, pero no tiene datos de empleado."
                            );
                        }

                        TipoEmpleado tipoEmpleado = TipoEmpleado.valueOf(tipoEmpleadoTexto);

                        int legajo = rs.getInt("legajo");
                        BigDecimal pagoPorHora = rs.getBigDecimal("pagoPorHora");
                        int horasTrabajadas = rs.getInt("horasTrabajadas");

                        return new Empleado(id, nombre, apellido, dni, usuario, email, contrasenia, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas);

                    default:

                        throw new SQLException(
                                "Tipo de usuario desconocido: " + tipoUsuario
                        );
                }
            }
        }
    }

}

