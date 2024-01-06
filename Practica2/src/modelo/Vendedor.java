package modelo;

import controlador.TDA.listas.DynamicList;

public class Vendedor extends Persona{
    
    private String agencia;
    private Double porcentajeComision;
    private DynamicList<Vivienda> viviendaList;

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public DynamicList<Vivienda> getViviendaList() {
        return viviendaList;
    }

    public void setViviendaList(DynamicList<Vivienda> viviendaList) {
        this.viviendaList = viviendaList;
    }
    
}
