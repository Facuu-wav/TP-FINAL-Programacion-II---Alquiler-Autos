package excepciones;

public class ObjetoNoExisteEx extends RuntimeException {
    public ObjetoNoExisteEx(String message) {
        super(message);
    }
}
