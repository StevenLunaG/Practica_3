package modelo;

public class TipoCasa {
    private Integer id;
    private String nombre;
    private Integer numPisos;
    private String descripcion;

    public TipoCasa() {
    }

    public TipoCasa(Integer id, String nombre, Integer numPisos, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.numPisos = numPisos;
        this.descripcion = descripcion;
    }
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numPisos
     */
    public Integer getNumPisos() {
        return numPisos;
    }

    /**
     * @param numPisos the numPIsos to set
     */
    public void setNumPisos(Integer numPisos) {
        this.numPisos = numPisos;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
