package enums;

public enum TipoUsuario {
    ADMINISTRADOR,
    EMPLEADO,
    EMPLEADO_LIMPIEZA,
    EMPLEADO_MANTENIMIENTO,
    EMPLEADO_VENTAS;

    // metodo para pasar de string a enum. si recibe un string vacio o nulo entonces retora null
    public static TipoUsuario fromString(String strsector){
        if(strsector == null || strsector.isEmpty() )return null;

        String strsectortrim = strsector.trim();

        for (TipoUsuario s : values()){
            if (s.name().equalsIgnoreCase(strsectortrim)){
                return s;
            }
        }
        return null;
    }
}
