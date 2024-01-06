package controlador;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.TDA.listas.DynamicList;
import modelo.Vivienda;


public class ViviendaControl {
    
    private Vivienda vivienda = new Vivienda();
    private DynamicList<Vivienda> viviendas;

    public ViviendaControl(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    public ViviendaControl() {
        this.viviendas = new DynamicList<>();
        
    }
    

    //Metodo que permite guardar
    public Boolean guardar() {
        
        try {
            getVivienda().setId(getViviendas().getLenght());
            getViviendas().add(getVivienda());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

/*    public Integer posVerificar() throws EmptyException {
        
        Integer bandera = 0;

        for (Integer i = 0; i <= this.viviendas.getLenght(); i++) {
            
            if (this.getViviendas().getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    public void imprimir() throws EmptyException {
        for (int i = 0; i < this.getViviendas().getLenght(); i++) {
            System.out.println(getViviendas().getInfo(i));
        }
    }*/

    /**
     * @return the vivienda
     */
    public Vivienda getVivienda() {
        if (vivienda == null) {
            vivienda = new Vivienda();
        }
        return vivienda;
    }

    /**
     * @param vivienda the vivienda to set
     */
    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }
    public DynamicList<Vivienda> getViviendas() {
        return viviendas;
    }

    public void setViviendas(DynamicList<Vivienda> viviendas) {
        this.viviendas = viviendas;
    }
}
