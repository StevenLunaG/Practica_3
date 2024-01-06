package modelo;

public class Vivienda {
    private Integer id;
    private String descripcion;
    private Double ancho;
    private Double largo;
    private Integer numHabitaciones;
    private String direccion;
    private Integer idTipo;
    private boolean disponible;

    public Vivienda(Integer id, String descripcion, Double ancho, Double largo, Integer numHabitaciones, String direccion, Integer idTipo, boolean disponible) {
        this.id = id;
        this.descripcion = descripcion;
        this.ancho = ancho;
        this.largo = largo;
        this.numHabitaciones = numHabitaciones;
        this.direccion = direccion;
        this.idTipo = idTipo;
        this.disponible = true;
    }
    
    public Vivienda(){
        this.disponible = true;
    }
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Integer getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(Integer numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }

}
