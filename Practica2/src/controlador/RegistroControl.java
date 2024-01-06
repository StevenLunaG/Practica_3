package controlador;

import controlador.TDA.listas.DynamicList;
import modelo.Registro;

public class RegistroControl {

    private Registro registro = new Registro();
    private DynamicList<Registro> registros;

    public RegistroControl(Registro registro) {
        this.registro = registro;
    }

    public RegistroControl() {
        this.registros = new DynamicList<>();
        
    }
    
    public Boolean guardar() {
        
        try {
            getRegistro().setId(getRegistros().getLenght());
            getRegistros().add(getRegistro());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Registro getRegistro() {
        if (registro == null) {
            registro = new Registro();
        }
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public DynamicList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(DynamicList<Registro> registros) {
        this.registros = registros;
    }

    @Override
    public String toString() {
        return "Id: " + getRegistro().getId();
    }
}
