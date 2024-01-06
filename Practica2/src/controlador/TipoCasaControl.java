package controlador;

import controlador.TDA.listas.DynamicList;
import modelo.TipoCasa;

public class TipoCasaControl {

    private DynamicList<TipoCasa> tipos;

    public TipoCasaControl() {
        tipos = new DynamicList<>();
        tipos.add(new TipoCasa(1, "Casa", 2, "Casa completa de dos pisos"));
        tipos.add(new TipoCasa(2, "Departamento", 1, "Departamento simple"));
        tipos.add(new TipoCasa(3, "Penthouse", 3, "Penthouse de tres pisos"));
    }

    public DynamicList<TipoCasa> getTipos() {
        return tipos;
    }

    public void setTipos(DynamicList<TipoCasa> tipos) {
        this.tipos = tipos;
    }
}
