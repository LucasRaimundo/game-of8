package models;

import java.util.*;

public class Tabuleiro {
    private final Nodo estadoInicial;
    private final Nodo estadoFinal;
    private final ArrayList<Nodo> estadosVizitados;
    private final ArrayList<Caminho> fronteira;
    private  Nodo estadoAtual;
    private int maiorTamanhoDaFronteira;

    public Tabuleiro(Nodo estadoInicial, Nodo estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoAtual = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.estadosVizitados = new ArrayList<>();
        this.fronteira = new ArrayList<>();
        this.maiorTamanhoDaFronteira = 0;
    }

    public ResultadoBusca acharCaminho() {
        
        Caminho caminhoAtual = new Caminho(this.estadoAtual);

        this.fronteira.add(caminhoAtual);
        this.maiorTamanhoDaFronteira = this.fronteira.size();
        adicionaNosEstadosVisitados(this.estadoAtual);

        
        return buscaObjetivo();
    }

    private ResultadoBusca buscaObjetivo() {
        while (!this.estadoAtual.equals(this.estadoFinal) && (this.fronteira.size() > 0)) {

            if (this.fronteira.size() > this.maiorTamanhoDaFronteira) {
                this.maiorTamanhoDaFronteira = this.fronteira.size();
            }

            
            adicionaNosEstadosVisitados(this.estadoAtual);

            
            Caminho menorCaminho = this.fronteira.get(0);

            
            ArrayList<Nodo> filhos = this.estadoAtual.expandeNodo(menorCaminho.getNodos());

            if (filhos.size() > 0) {
                ArrayList<Caminho> fronteirasExpandidas = new ArrayList<>();

                for (Nodo filho: filhos) {
                    ArrayList<Nodo> nodosEspandidos = new ArrayList<>();
                    nodosEspandidos.addAll(menorCaminho.getNodos());
                    nodosEspandidos.add(filho);

                    Caminho caminhoExpandido = new Caminho(nodosEspandidos);

                    fronteirasExpandidas.add(caminhoExpandido);
                }

                this.fronteira.remove(0);
                this.fronteira.addAll(fronteirasExpandidas);
                Collections.sort(this.fronteira);

                Caminho novoMenorCaminho = this.fronteira.get(0);
                ArrayList<Nodo> nodosNovoMenorCaminho = novoMenorCaminho.getNodos();

                this.estadoAtual = nodosNovoMenorCaminho.get(nodosNovoMenorCaminho.size() - 1);
            } else {
                this.fronteira.remove(0);
                Caminho novoMenorCaminho = this.fronteira.get(0);
                ArrayList<Nodo> nodosNovoMenorCaminho = novoMenorCaminho.getNodos();

                this.estadoAtual = nodosNovoMenorCaminho.get(nodosNovoMenorCaminho.size() - 1);
                System.out.println("Nao foi possivel encontrar o nodo objetivo a partir do estado inicial informado!" +
                        " O resultado retornado eh invalido!");
                ResultadoBusca resultado = new ResultadoBusca(fronteira.get(0).getNodos(), this.estadosVizitados, this.maiorTamanhoDaFronteira);

                
                return resultado;
            }
        }

       
        ResultadoBusca resultado = new ResultadoBusca(fronteira.get(0).getNodos(), this.estadosVizitados, this.maiorTamanhoDaFronteira);

        
        return resultado;
    }

    private void adicionaNosEstadosVisitados(Nodo estado) {
        if (!this.estadosVizitados.contains(estado)) {
            this.estadosVizitados.add(estado);
        }
    }
}