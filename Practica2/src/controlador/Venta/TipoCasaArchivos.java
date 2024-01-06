package controlador.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import modelo.TipoCasa;

public class TipoCasaArchivos extends DaoImplement<TipoCasa>{
    private DynamicList<TipoCasa> tipos;
    private TipoCasa tipo;

    public TipoCasaArchivos() {
        super(TipoCasa.class);
    }

    public DynamicList<TipoCasa> getTipos() {
        tipos = all();
        return tipos;
    }

    public void setTipos(DynamicList<TipoCasa> tipos) {
        this.tipos = tipos;
    }

    public TipoCasa getTipo() {
        if (tipo == null) {
            tipo = new TipoCasa();
        }
        return tipo;
    }

    public void setTipo(TipoCasa tipo) {
        this.tipo = tipo;
    }
    
    public Boolean persist(){
        tipo.setId(all().getLength() + 1);
        return persist(tipo);
    }
    
//       public static void main(String[] args) {
//        TipoCasaArchivos rc = new TipoCasaArchivos();
//        System.out.println(rc.all().toString());
//        rc.getTipo().setDescripcion("Casa");
//        rc.getTipo().setNombre("casa");
//        rc.persist();
//        rc.setTipo(null);
//        rc.getTipo().setDescripcion("Departamento");
//        rc.getTipo().setNombre("departamento");
//        rc.persist();
//        rc.setTipo(null);
//        rc.getTipo().setDescripcion("Penthouse");
//        rc.getTipo().setNombre("penthouse");
//        rc.persist();
//        rc.setTipo(null);
//    }
}
