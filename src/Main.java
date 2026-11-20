import clases.Sistema;
import clases.empleados.Usuario;
import enums.Marca;
import sql.DBHandler;
import sql.UsuarioDAO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    static void main(String[] args) {

        Sistema sistema = new Sistema();

        try {
            //UsuarioDAO.agregarAdministradorBDD("Facundo", "Giménez", "46112651", "ADMIN", "facundogimenez2k20@gmail.com", "1234");
            sistema.iniciarSesion("ADMIN", "1234");

            /// Agrega un vehículo
            //System.out.println(sistema.agregarVehiculo("DEF-456", Marca.CHEVROLET, "1", 2004, 1, BigDecimal.valueOf(15000)));

            /// Agrega un cliente
            //System.out.println(sistema.agregarCliente("john", "doe", "12345678", "223 692-1036", "1234", LocalDate.of(2030,1,1)));

            /// Trae los clientes de la base de datos.
            //sistema.cargarClientesBDD();
            //System.out.println(sistema.listarClientesActivos());

            /// Trae los vehiculos de la base de datos.
            //sistema.cargarVehiculosBDD();
            //System.out.println(sistema.listarVehiculosActivos());

            /// Da de baja un vehiculo por su id
            //sistema.darDeBajaVehiculo(2);

            /// Borra todos los datos guardados de la base, conserva las tablas
            /// BORRAR ESTE MÉTODO DE DBHandler cuando ya no lo necesitemos.
            //DBHandler.autodestruirse();


        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }
}
