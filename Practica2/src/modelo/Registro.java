package modelo;

import controlador.TDA.listas.DynamicList;

public class Registro {
    private Integer id;
    private DynamicList<Venta> ventaList;

    public Registro() {
    }

    public Registro(Integer id, DynamicList<Venta> ventaList) {
        this.id = id;
        this.ventaList = ventaList;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DynamicList<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(DynamicList<Venta> ventaList) {
        this.ventaList = ventaList;
    }
    
    
            
            
}
