package controlador;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import modelo.Venta;

public class VentaControl {

    private Venta venta = new Venta();
    private DynamicList<Venta> ventas;

    public VentaControl(Venta venta) {
        this.venta = venta;
    }

    public VentaControl() {
        this.ventas = new DynamicList<>();
        
    }
    
    public Boolean guardar() {
        
        try {
            getVenta().setId(getVentas().getLenght());
            getVentas().add(getVenta());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public Integer posVerificar() throws EmptyException {
//        
//        Integer bandera = 0;
//
//        for (Integer i = 0; i <= this.ventas.getLenght(); i++) {
//            
//            if (this.getVentas().getInfo(i) == null) {
//                bandera = i;
//                break;
//            }
//        }
//        return bandera;
//    }
//
//    public void imprimir() throws EmptyException {
//        for (int i = 0; i < this.getVentas().getLenght(); i++) {
//            System.out.println(getVentas().getInfo(i));
//        }
//    }

    public Venta getVenta() {
        if (venta == null) {
            venta = new Venta();
        }
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public DynamicList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Fecha: " + getVenta().getFecha() + " Vendedor: " + getVenta().getVendedor() + " Cliente: " + getVenta().getCliente() + " Vivienda: " + getVenta().getVivienda();
    }
}
