package vista.listas.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import modelo.Venta;

public class VentaTabla extends AbstractTableModel {
    
    private DynamicList<Venta> ventas;

    @Override
    public int getRowCount() {
        return ventas.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Venta v = ventas.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (v != null) ? v.getFecha() : " ";
                case 1:
                    return (v != null) ? v.getVendedor().getApellido() + " " + v.getVendedor().getNombre() : "";
                case 2:
                    return (v != null) ? v.getCliente().getApellido() + " " + v.getCliente().getNombre() : "";
                case 3:
                    return (v != null) ? v.getVivienda().getDescripcion() : "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "FECHA";
            case 1:
                return "VENDEDOR";
            case 2:
                return "CLIENTE";
            case 3:
                return "VIVIENDA";
            default:
                return null;
        }
    }

    public DynamicList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }
    
    

}
