package controlador.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import modelo.Registro;

public class RegistroArchivos extends DaoImplement<Registro>{
    private DynamicList<Registro> registros;
    private Registro registro;

    public RegistroArchivos() {
        super(Registro.class);
    }

    public DynamicList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(DynamicList<Registro> registros) {
        this.registros = registros;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

        
    public Boolean persist(){
        registro.setId(all().getLenght() + 1);
        return persist(registro);
    }
}
