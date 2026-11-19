import clases.Sistema;

public class Main {
    static void main(String[] args) {

        Sistema sistema = new Sistema();

        try {
            sistema.iniciarSesion("ADMIN", "1234");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
