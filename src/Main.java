import clases.Sistema;
import clases.empleados.Usuario;
import enums.Marca;
import sql.DBHandler;
import sql.UsuarioDAO;

import java.math.BigDecimal;

public class Main {
    static void main(String[] args) {

        Sistema sistema = new Sistema();

        try {
            //UsuarioDAO.agregarAdministradorBDD("Facundo", "Giménez", "46112651", "ADMIN", "facundogimenez2k20@gmail.com", "1234");
            sistema.iniciarSesion("ADMIN", "1234");

            /// Agrega un vehículo
            //System.out.println(sistema.agregarVehiculo("DEF-456", Marca.CHEVROLET, "1", 2004, 1, BigDecimal.valueOf(15000)));

            /// Trae los vehiculos de la base de datos.
            sistema.cargarVehiculosBDD();
            System.out.println(sistema.listarVehiculosActivos());

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
