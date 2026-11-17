package enums;

public enum Sector {
    VENTAS,
    MANTENIMIENTO,
    LIMPIEZA;

    // metodo para pasar de string a enum. si recibe un string vacio o nulo entonces retora null
    public Sector fromString(String strsector){
        if(strsector == null || strsector.isEmpty() )return null;

        String strsectortrim = strsector.trim();

        for (Sector s : values()){
            if (s.name().equalsIgnoreCase(strsectortrim)){
                return s;
            }
        }
        return null;
    }
}
