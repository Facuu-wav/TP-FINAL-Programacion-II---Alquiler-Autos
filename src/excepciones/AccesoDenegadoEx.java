package excepciones;

public class AccesoDenegadoEx extends RuntimeException {
    public AccesoDenegadoEx(String message) {
        super(message);
    }
}
