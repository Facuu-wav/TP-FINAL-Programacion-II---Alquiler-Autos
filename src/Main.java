import clases.Sistema;
import clases.empleados.Administrador;
import clases.empleados.Empleado;
import clases.empleados.Usuario;
import enums.EstadoVehiculo;
import enums.Marca;
import enums.TipoEmpleado;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.NoExistenElementosCargadosEx;
import excepciones.ObjetoNoExisteEx;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    /// Atributos
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int MAX_INTENTOS = 3;

    private static final Sistema sistema = new Sistema();
    private static boolean datosCargados = false;

    public static void main(String[] args) {
        System.out.println("+================================+");
        System.out.println("|  SISTEMA DE ALQUILER DE AUTOS  |");
        System.out.println("+================================+");

        boolean salir = false;

        while (!salir) {

            ///||INICIO DE SESION||--> iniciamos sesion al inicio para saber que tipo de privilegios tenemos.
            Usuario usuario = pantallaLogin();

            if (usuario == null) {
                // Se superaron los intentos o el usuario eligió salir
                salir = true;

            } else {
                System.out.println("\nBienvenido/a " + usuario.getNombre() + " " + usuario.getApellido()
                        + " (" + describirRol(usuario) + ")");

                cargarDatosIniciales();

                ///
                salir = menuPrincipal();
            }
        }

        System.out.println("\nHasta luego!");
    }

    // ----------------------------------------------------------|Login|----------------------------------------------------------

    // Devuelve el usuario logueado, o null si se agotaron los intentos o se eligió salir.
    private static Usuario pantallaLogin() {

        System.out.println("+------------------+");
        System.out.println("| INICIO DE SESIÓN |");
        System.out.println("+------------------+");


        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {

            String nombreUsuario = leerTexto("Usuario: ");

            if (nombreUsuario.equals("0")) {
                return null;
            }

            String contrasenia = leerTexto("Contraseña: ");

            try {
                return sistema.iniciarSesion(nombreUsuario, contrasenia);

            } catch (ObjetoNoExisteEx | ContraseniaIncorrectaEx e) {
                System.out.println("ERROR: " + e.getMessage() + " (intento " + intento + " de " + MAX_INTENTOS + ")");
            } catch (SQLException e) {
                System.out.println("ERROR de base de datos: " + e.getMessage());
            }
        }

        System.out.println("Se superó el máximo de intentos.");
        return null;
    }

    private static String describirRol(Usuario usuario) {

        if (usuario instanceof Administrador) {
            return "Administrador";
        }

        if (usuario instanceof Empleado empleado) {
            return empleado.getTipoEmpleado().name();
        }

        return "Usuario";
    }

    // Carga una sola vez los clientes y vehículos guardados en la base de datos.
    private static void cargarDatosIniciales() {

        if (datosCargados) {
            return;
        }

        try {
            sistema.cargarClientesBDD();
        } catch (NoExistenElementosCargadosEx e) {
            System.out.println("Aviso: no hay clientes cargados en la base de datos.");
        } catch (SQLException e) {
            System.out.println("ERROR al cargar clientes: " + e.getMessage());
        }

        try {
            sistema.cargarVehiculosBDD();
        } catch (NoExistenElementosCargadosEx e) {
            System.out.println("Aviso: no hay vehículos cargados en la base de datos.");
        } catch (SQLException e) {
            System.out.println("ERROR al cargar vehículos: " + e.getMessage());
        }

        datosCargados = true;
    }

    // ----------------------------------------------------------|Menú|----------------------------------------------------------

    // Devuelve true si se quiere salir del programa, false si solo se cerró la sesión.
    private static boolean menuPrincipal() {

        while (true) {

            mostrarMenu();
            int opcion = leerEntero("Opción: ");

            if (opcion == 0) {
                sistema.cerrarSesion();
                return true;
            }

            if (opcion == 13) {
                sistema.cerrarSesion();
                System.out.println("Sesión cerrada.");
                return false;
            }

            ejecutarOpcion(opcion);
        }
    }

    private static void mostrarMenu() {

        System.out.println("""
                
                +===============================================================+
                |                        MENÚ PRINCIPAL                         |
                +===============================================================+
                | USUARIOS                                                      |
                |  1. Agregar administrador        [Administrador]              |
                |  2. Agregar empleado             [Administrador]              |
                | CLIENTES                                                      |
                |  3. Agregar cliente              [Administrador]              |
                |  4. Listar clientes activos                                   |
                | VEHÍCULOS                                                     |
                |  5. Agregar vehículo             [Administrador]              |
                |  6. Dar de baja vehículo         [Administrador]              |
                |  7. Listar vehículos activos                                  |
                |  8. Listar vehículos alquilados                               |
                |  9. Listar vehículos en preparación                           |
                | RESERVAS Y ALQUILERES                                         |
                | 10. Agregar reserva              [Administrador / Ventas]     |
                | 11. Registrar retiro (alquiler)  [Administrador / Ventas]     |
                | 12. Registrar devolución         [Administrador / Ventas]     |
                | SESIÓN                                                        |
                | 13. Cerrar sesión                                             |
                |  0. Salir                                                     |
                +===============================================================+""");
    }

    // Ejecuta la opción elegida. Si el usuario no tiene permisos, Sistema lanza AccesoDenegadoEx.
    private static void ejecutarOpcion(int opcion) {

        try {
            switch (opcion) {
                case 1 -> agregarAdministrador();
                case 2 -> agregarEmpleado();
                case 3 -> agregarCliente();
                case 4 -> System.out.println(vacioSiNoHay(sistema.listarClientesActivos()));
                case 5 -> agregarVehiculo();
                case 6 -> darDeBajaVehiculo();
                case 7 -> System.out.println(vacioSiNoHay(sistema.listarVehiculosActivos()));
                case 8 -> System.out.println(vacioSiNoHay(sistema.listarVehiculosAlquilados()));
                case 9 -> System.out.println(vacioSiNoHay(sistema.listarVehiculosEnPreparacion()));
                case 10 -> agregarReserva();
                case 11 -> agregarAlquiler();
                case 12 -> finalizarAlquiler();
                default -> System.out.println("Opción inválida.");
            }

        } catch (AccesoDenegadoEx e) {
            System.out.println("ACCESO DENEGADO: no tenés permisos para realizar esta acción.");
        } catch (SQLException e) {
            System.out.println("ERROR de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static String vacioSiNoHay(StringBuilder listado) {
        return listado.isEmpty() ? "No hay elementos para mostrar." : listado.toString();
    }

    // ----------------------------------------------------------|FUNCIONALIDADES|----------------------------------------------------------

    private static void agregarAdministrador() throws Exception {

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String email = leerTexto("Email: ");
        String contrasenia = leerTexto("Contraseña: ");

        System.out.println(sistema.agregarAdministrador(nombre, apellido, dni, nombreUsuario, email, contrasenia));
    }

    private static void agregarEmpleado() throws Exception {

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String nombreUsuario = leerTexto("Nombre de usuario: ");
        String email = leerTexto("Email: ");
        String contrasenia = leerTexto("Contraseña: ");
        TipoEmpleado tipo = leerTipoEmpleado();
        int legajo = leerEntero("Legajo: ");
        BigDecimal pagoPorHora = leerDecimal("Pago por hora: ");
        int horasTrabajadas = leerEntero("Horas trabajadas: ");

        System.out.println(sistema.agregarEmpleado(nombre, apellido, dni, nombreUsuario, email, contrasenia,
                tipo, legajo, pagoPorHora, horasTrabajadas));
    }

    private static void agregarCliente() throws Exception {

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String telefono = leerTexto("Teléfono: ");
        String licencia = leerTexto("Licencia de conducir: ");
        LocalDate vencimiento = leerFecha("Vencimiento de la licencia (dd/MM/yyyy): ");

        System.out.println(sistema.agregarCliente(nombre, apellido, dni, telefono, licencia, vencimiento));
    }

    private static void agregarVehiculo() throws Exception {

        String patente = leerTexto("Patente: ");
        Marca marca = leerMarca();
        String modelo = leerTexto("Modelo: ");
        int anio = leerEntero("Año: ");
        int kilometraje = leerEntero("Kilometraje: ");
        BigDecimal precioPorDia = leerDecimal("Precio por día: ");

        System.out.println(sistema.agregarVehiculo(patente, marca, modelo, anio, kilometraje, precioPorDia));
    }

    private static void darDeBajaVehiculo() throws Exception {

        System.out.println(vacioSiNoHay(sistema.listarVehiculosActivos()));
        int id = leerEntero("ID del vehículo a dar de baja: ");

        sistema.darDeBajaVehiculo(id);
        System.out.println("Vehículo dado de baja correctamente.");
    }

    //||AGREGAR_RESERVA||-->
    private static void agregarReserva() throws Exception {

        int idCliente = leerEntero("ID del cliente: ");
        int idVehiculo = leerEntero("ID del vehículo: ");
        LocalDate inicio = leerFecha("Fecha de inicio (dd/MM/yyyy): ");
        LocalDate fin = leerFecha("Fecha de fin (dd/MM/yyyy): ");

        System.out.println(sistema.agregarReserva(idCliente, idVehiculo, LocalDateTime.now(), inicio, fin));
    }

    //||AGREGAR_ALQUILER||--> Se registra cuando el cliente retira el vehículo.
    private static void agregarAlquiler() throws Exception {

        int idReserva = leerEntero("ID de la reserva: ");
        LocalDate devolucionPrevista = leerFecha("Fecha de devolución prevista (dd/MM/yyyy): ");
        int kilometraje = leerEntero("Kilometraje del vehículo: ");

        System.out.println(sistema.agregarAlquiler(idReserva, LocalDateTime.now(),
                devolucionPrevista.atTime(LocalTime.of(23, 59)), kilometraje));
    }

    //||FINALIZAR_ALQUILER||--> Se registra cuando el cliente devuelve el vehículo.
    private static void finalizarAlquiler() throws Exception {

        int idAlquiler = leerEntero("ID del alquiler: ");
        int kilometrajeFinal = leerEntero("Kilometraje final: ");
        EstadoVehiculo estado = leerEstadoDevolucion();

        System.out.println(sistema.finalizarAlquiler(idAlquiler, kilometrajeFinal, estado));
    }

    // ----------------------------------------------------------|HERRAMIENTAS_PARA_CARGA_DE_DATOS.|----------------------------------------------------------

    private static String leerTexto(String mensaje) {

        String texto;

        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("El campo no puede estar vacío.");
            }
        } while (texto.isEmpty());

        return texto;
    }

    private static int leerEntero(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    private static BigDecimal leerDecimal(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return new BigDecimal(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido (ej: 15000.50).");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(sc.nextLine().trim(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Usá el formato dd/MM/yyyy.");
            }
        }
    }

    private static Marca leerMarca() {

        while (true) {
            System.out.println("Marcas disponibles:");
            for (Marca m : Marca.values()) {
                System.out.println("  - " + m);
            }

            Marca marca = Marca.fromString(leerTexto("Marca: "));

            if (marca != null) {
                return marca;
            }
            System.out.println("Marca inválida.");
        }
    }

    private static TipoEmpleado leerTipoEmpleado() {

        while (true) {
            System.out.println("Tipos de empleado:");
            for (TipoEmpleado t : TipoEmpleado.values()) {
                System.out.println("  - " + t);
            }

            TipoEmpleado tipo = TipoEmpleado.fromString(leerTexto("Tipo: "));

            if (tipo != null) {
                return tipo;
            }
            System.out.println("Tipo inválido.");
        }
    }

    // Estados válidos para un vehículo que se devuelve (no puede quedar alquilado ni reservado).
    private static EstadoVehiculo leerEstadoDevolucion() {

        while (true) {
            System.out.println("Estado del vehículo al devolverlo:");
            System.out.println("  - DISPONIBLE");
            System.out.println("  - EN_LIMPIEZA");
            System.out.println("  - EN_MANTENIMIENTO");
            System.out.println("  - FUERA_DE_SERVICIO");

            EstadoVehiculo estado = EstadoVehiculo.fromString(leerTexto("Estado: "));

            if (estado != null && estado != EstadoVehiculo.ALQUILADO && estado != EstadoVehiculo.RESERVADO) {
                return estado;
            }
            System.out.println("Estado inválido.");
        }
    }
}