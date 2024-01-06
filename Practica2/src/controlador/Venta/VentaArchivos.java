package controlador.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import modelo.Venta;

public class VentaArchivos extends DaoImplement<Venta> {

    private DynamicList<Venta> ventas;
    private Venta venta;

    public VentaArchivos() {
        super(Venta.class);
    }

    public DynamicList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Boolean persist() {
        venta.setId(all().getLength());
        return persist(venta);
    }

    //Metodo Shellsort 
    public DynamicList<Venta> ordenarShell(DynamicList<Venta> lista, String criterio, Integer tipo) throws EmptyException, Exception {
        Venta[] ventas = lista.toArray();

        int brecha = ventas.length / 2;
        int totalIteraciones = 0;

        while (brecha > 0) {
            for (int recorrido = brecha; recorrido < ventas.length; recorrido++) {
                Venta buffer = ventas[recorrido];
                int indice = recorrido;

                while (indice >= brecha && buffer.compare(ventas[indice - brecha], criterio, tipo)) {
                    ventas[indice] = ventas[indice - brecha];
                    indice -= brecha;
                    totalIteraciones++;
                }
                ventas[indice] = buffer;
            }

            brecha = brecha / 2;
        }
        System.out.println("Shell | Total de iteraciones: " + totalIteraciones);
        return lista.toList(ventas);
    }

    //Metodo QuickSort
    public DynamicList<Venta> ordenarQuick(DynamicList<Venta> lista, String criterio, Integer tipo) throws EmptyException {
        Venta[] array = lista.toArray();
        Integer[] iteracionesQuick = {0}; 
        ordenarRecursivo(array, 0, array.length - 1, criterio, tipo, iteracionesQuick);
        System.out.println("Quick | Total de iteraciones:: " + iteracionesQuick[0]); 
        return lista.toList(array);
    }

    private static Integer ordenarSeccion(Venta[] array, Integer indiceIzq, Integer indiceDer, String criterio, Integer tipo, Integer[] iteraciones) {
        Venta pivote = array[indiceDer];
        int elemento = indiceIzq - 1;

        for (int indice = indiceIzq; indice < indiceDer; indice++) {
            iteraciones[0]++; 
            if (array[indice].compare(pivote, criterio, tipo)) {
                elemento++;
                Venta bufferI = array[elemento];
                array[elemento] = array[indice];
                array[indice] = bufferI;
            }
        }
        elemento++;
        Venta bufferPivote = array[elemento];
        array[elemento] = array[indiceDer];
        array[indiceDer] = bufferPivote;

        return elemento;
    }

    private static void ordenarRecursivo(Venta[] array, Integer indiceIzq, Integer indiceDer, String criterio, Integer tipo, Integer[] iteraciones) {
        if (indiceIzq < indiceDer) {
            Integer inicio = ordenarSeccion(array, indiceIzq, indiceDer, criterio, tipo, iteraciones);
            ordenarRecursivo(array, indiceIzq, inicio - 1, criterio, tipo, iteraciones);
            ordenarRecursivo(array, inicio + 1, indiceDer, criterio, tipo, iteraciones);
        }
    }

}
