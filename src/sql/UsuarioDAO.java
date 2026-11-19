package sql;

import clases.empleados.*;
import enums.TipoUsuario;
import excepciones.IdNoExistenteEx;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private static final String USUARIO_POR_ID = "SELECT * FROM usuario WHERE id = ?";

    //--> Metodo para buscar USUARIO_POR_ID retornando el objeto encontrado.
    public static Usuario usuarioPorId(int idbuscado) throws SQLException, IdNoExistenteEx {
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

                    TipoUsuario tipo_usuario = TipoUsuario.fromString(rs.getString("tipo_usuario"));


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
                }

                if (!rs.next()) {
                    trow new IdNoExistenteEx("ERROR: Ingresar un id existente.");
                }

            } catch (SQLException | IdNoExistenteEx e) {
                e.printStackTrace();
            }

            return usuario;
        }
    }


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
}

