package models;

import java.util.ArrayList;

public class Caminho implements Comparable<Caminho> {
    private final ArrayList<Nodo> nodos;

    public Caminho(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public Caminho(Nodo nodo) {
        this.nodos = new ArrayList<>();
        this.nodos.add(nodo);
    }

    public int getCustoTotal() {
        if (nodos == null){
            return 0;
        }

        int custoTotal = 0;

        for (Nodo nodo: this.nodos) {
            custoTotal += nodo.getCusto();
        }

        return custoTotal;
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    @Override
    public int compareTo(Caminho caminho) {
        Integer custo = this.getCustoTotal();
        Integer custoOutroCaminho = caminho.getCustoTotal();

        return custo.compareTo(custoOutroCaminho);
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        
        if (!Caminho.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        
        final Caminho caminhoComparado = (Caminho) obj;

        
        final ArrayList<Nodo> nodosComparados = caminhoComparado.getNodos();

        
        if (this.nodos.size() != nodosComparados.size()) {
            return false;
        }

        
        for (int i = 0; i < this.nodos.size(); i++) {
            if (!nodos.get(i).equals(nodosComparados.get(i))) return false;
        }

        
        return true;
    }

    @Override
    public String toString() {
        return this.nodos.toString();
    }
}
