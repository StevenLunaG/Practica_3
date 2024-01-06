package controlador.DAO;

import controlador.TDA.listas.DynamicList;

public interface DaoInterface<T> {
    public Boolean persist(T data);
    public Boolean merge(T data, Integer index);
    public DynamicList<T> all();
    public T get(Integer id);

}