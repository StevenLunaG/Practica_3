package controlador.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import modelo.Vivienda;

public class ViviendaArchivos extends DaoImplement<Vivienda>{
    private DynamicList<Vivienda> viviendas;
    private Vivienda vivienda;

    public ViviendaArchivos() {
        super(Vivienda.class);
    }

    public DynamicList<Vivienda> getViviendas() {
        viviendas = all();
        return viviendas;
    }

    public void setViviendas(DynamicList<Vivienda> viviendas) {
        this.viviendas = viviendas;
    }

    public Vivienda getVivienda() {
        if (vivienda == null) {
            vivienda = new Vivienda();
        }
        return vivienda;
    }

    public void setTipo(Vivienda tipo) {
        this.vivienda = tipo;
    }
    
    public Boolean persist(){
        vivienda.setId(all().getLength());
        return persist(vivienda);
    }
}
