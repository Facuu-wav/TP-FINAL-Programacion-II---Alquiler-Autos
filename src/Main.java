import clases.Sistema;
import enums.Marca;
import sql.UsuarioDAO;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    static void main(String[] args) {

        Sistema sistema = new Sistema();

        try {
            sistema.iniciarSesion("ADMIN", "1234");

            //System.out.println(sistema.agregarVehiculo("ABC-123", Marca.CHEVROLET, "1", 2004, 1, BigDecimal.valueOf(15000)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
