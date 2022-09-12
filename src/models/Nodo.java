package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import enums.Algoritmo;

import static application.Program.*;

public class Nodo implements Comparable<Nodo> {
    private final ArrayList<Integer> quadrados;
    private final int custo;
    private  ArrayList<Nodo> filhos;

    public Nodo(ArrayList<Integer> quadrados, int custo) {
        this.quadrados = quadrados;
        this.custo = custo;
    }

    public ArrayList<Integer> getQuadrados() {
        return this.quadrados;
    }

    
    public Integer getPosicaoVazia() {
        for (int i = 0; i < quadrados.size(); i++) {
            if (quadrados.get(i) == 0) return i;
        }
        
        return -1;
    }

    
    public int getCusto() {
        int custoTotal = this.custo;

        if (AlGORITMO.equals(Algoritmo.A_ESTRELA_SIMPLES)) {
            ArrayList<Integer> quadradosObjetivo = OBJETIVO.getQuadrados();
            for(int i = 0; i < this.quadrados.size(); i++){
                Integer quadrado = this.quadrados.get(i);
                Integer quadradoObjetivo = quadradosObjetivo.get(i);

                if (quadrado != quadradoObjetivo){
                    custoTotal += 1;
                }
            }
        }

        if (AlGORITMO.equals(Algoritmo.A_ESTRELA_MELHORADO)) {
            ArrayList<Integer> quadradosObjetivo = OBJETIVO.getQuadrados();
            for(int i = 0; i < this.quadrados.size(); i++){
                Integer quadrado = this.quadrados.get(i);
                Integer quadradoObjetivo = quadradosObjetivo.get(i);

                if (quadrado != quadradoObjetivo){
                    Integer posicaoAlvo = MAP_OBJETIVO_POSICAO.get(quadrado);

                    int distanciaHorizontal = Math.abs( (posicaoAlvo % 3) - (i % 3) );
                    int distanciaVertical =  Math.abs( (int)Math.floor(posicaoAlvo / 3) - (int)Math.floor(i / 3) );

                    custoTotal += (distanciaHorizontal + distanciaVertical);
                }
            }
        }

        return custoTotal;
    }

    public ArrayList<Nodo> expandeNodo(ArrayList<Nodo> caminhoDeOrigem) {
        if (this.filhos != null) {
            return this.filhos;
        }

        ArrayList<Nodo> filhos = new ArrayList<>();

        int posicaovazia = this.getPosicaoVazia();

        ArrayList<Integer> posicoesAdjacentes = new ArrayList<>();

        switch(posicaovazia){
            case 0:
                posicoesAdjacentes.addAll(Arrays.asList(1, 3));
                break;
            case 1:
                posicoesAdjacentes.addAll(Arrays.asList(0, 2, 4));
                break;
            case 2:
                posicoesAdjacentes.addAll(Arrays.asList(1, 5));
                break;
            case 3:
                posicoesAdjacentes.addAll(Arrays.asList(0, 4, 6));
                break;
            case 4:
                posicoesAdjacentes.addAll(Arrays.asList(1, 3, 5, 7));
                break;
            case 5:
                posicoesAdjacentes.addAll(Arrays.asList(2, 4, 8));
                break;
            case 6:
                posicoesAdjacentes.addAll(Arrays.asList(3, 7));
                break;
            case 7:
                posicoesAdjacentes.addAll(Arrays.asList(4, 6, 8));
                break;
            case 8:
                posicoesAdjacentes.addAll(Arrays.asList(5, 7));
                break;
        }

        for (Integer posicao: posicoesAdjacentes) {
           
        	ArrayList<Integer> quadradosCopiado = new ArrayList<>();
            quadradosCopiado.addAll(this.quadrados);

            
            Integer valorSalvo = quadradosCopiado.get(posicao);

            
            quadradosCopiado.set(posicao, 0);

           
            quadradosCopiado.set(posicaovazia, valorSalvo);

             Nodo filho = new Nodo(quadradosCopiado, (getCusto() + 1));

            if (!caminhoDeOrigem.contains(filho)) {
                filhos.add(filho);
            }
        }

        
        Collections.sort(filhos);

        this.filhos = filhos;

        return filhos;
    }

    @Override
    public int compareTo(Nodo nodo) {
        int custo = this.getCusto();
        int custoOutroNodo = nodo.getCusto();

        if (custo == custoOutroNodo) return 0;
        if (custo < custoOutroNodo) return -1;
        if (custo > custoOutroNodo) return 1;

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        
        if (!Nodo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        
        final Nodo nodoComparado = (Nodo) obj;

       
        final ArrayList<Integer> quadradosComparados = nodoComparado.getQuadrados();

        
        if (this.quadrados.size() != quadradosComparados.size()) {
            return false;
        }

        
        for (int i = 0; i < this.quadrados.size(); i++) {
            if (quadrados.get(i) != quadradosComparados.get(i)) return false;
        }

        
        return true;
    }

    @Override
    public String toString() {
        return this.quadrados.toString();
    }
}
