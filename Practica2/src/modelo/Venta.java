package modelo;

import controlador.TDA.listas.Exception.EmptyException;
import controlador.Venta.ViviendaArchivos;

import java.time.LocalDate;

public class Venta {
    private Integer id;
    private Vendedor vendedor;
    private Cliente cliente;
    private LocalDate fecha;
    private Double monto;
    private Vivienda vivienda;

    public Venta() {
        this.vendedor = new Vendedor();
        this.cliente = new Cliente();
    }

    public Venta(Integer id, Vendedor vendedor, Cliente cliente, LocalDate fecha, Double monto, Vivienda idVivienda) {
        this.id = id;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.fecha = fecha;
        this.monto = monto;
        this.vivienda = idVivienda;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Integer index) throws EmptyException{
        ViviendaArchivos vControl = new ViviendaArchivos();
        this.vivienda = vControl.all().getInfo(index);
    }

    @Override
    public String toString() {
        return getFecha() + getVendedor().getApellido() + getVendedor().getNombre() + getCliente().getApellido() + getCliente().getNombre() + getVivienda();
    }
    
    
}
