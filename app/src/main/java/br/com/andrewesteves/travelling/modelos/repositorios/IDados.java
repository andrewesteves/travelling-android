package br.com.andrewesteves.travelling.modelos.repositorios;

import java.util.ArrayList;

public interface IDados<T> {
    public ArrayList<T> listar();
    public T unico(int id);
    public boolean inserir(T type);
    public boolean atualizar(T type);
    public boolean remover(T type);
}
