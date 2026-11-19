package interfaces;

public interface IGestionarEstados<E extends Enum<E>> {

    void cambiarEstado(E nuevoEstado);
}
