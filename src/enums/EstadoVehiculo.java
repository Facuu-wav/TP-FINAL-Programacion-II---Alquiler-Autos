package enums;

public enum EstadoVehiculo {
    DISPONIBLE,
    RESERVADO,
    ALQUILADO,
    EN_MANTENIMIENTO,
    EN_LIMPIEZA,
    FUERA_DE_SERVICIO;

    // metodo para pasar de string a enum. si recibe un string vacio o nulo entonces retora null
    public EstadoVehiculo fromString(String strestadovehiculo){
        if(strestadovehiculo == null || strestadovehiculo.isEmpty() )return null;

        String strestadovehiculotrim = strestadovehiculo.trim();

        for (EstadoVehiculo ev : values()){
            if (ev.name().equalsIgnoreCase(strestadovehiculotrim)){
                return ev;
            }
        }
        return null;
    }
}