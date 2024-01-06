package vista.listas.util;

import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.JComboBox;
import modelo.TipoCasa;
import controlador.TipoCasaControl;
import modelo.Vivienda;
import controlador.ViviendaControl;

public class Utilvista {
       
    public static void cargarComboTipos(JComboBox cbx) throws EmptyException{
        TipoCasaControl tc = new TipoCasaControl();
        cbx.removeAllItems();
        for (Integer i = 0; i < tc.getTipos().getLenght(); i++) {
            cbx.addItem(tc.getTipos().getInfo(i));
        }
    }
    
    public static TipoCasa obtenerTipoControl(JComboBox cbx){
        return (TipoCasa) cbx.getSelectedItem();
    }
       
    public static void cargarComboViviendas(JComboBox cbx) throws EmptyException{
        ViviendaControl vc = new ViviendaControl();
        controlador.Venta.ViviendaArchivos vv = new controlador.Venta.ViviendaArchivos();
        vc.setViviendas(vv.all());
        cbx.removeAllItems();
        for (Integer i = 0; i < vc.getViviendas().getLenght(); i++) {
            if (vc.getViviendas().getInfo(i).getDisponible()) {
                cbx.addItem(vc.getViviendas().getInfo(i));
            } else {
                cbx.addItem(vc.getViviendas().getInfo(i) + " [No Disponible]");
            }
        }
    }

    public static Vivienda obtenerViviendaControl(JComboBox cbx){
        return (Vivienda) cbx.getSelectedItem();
    }
}
