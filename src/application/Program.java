package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import enums.Algoritmo;
import models.Nodo;
import models.ResultadoBusca;
import models.Tabuleiro;

public class Program {
	
	public static Map<Integer, Integer> MAP_OBJETIVO_POSICAO = new HashMap<>();
    public static Nodo OBJETIVO;
    public static Algoritmo AlGORITMO;

	public static void main(String[] args) {

		System.out.println("Bem vindo ao jogo dos 8!");

        defineAlgoritmo();
        Nodo estadoFinal = defineEstadoFinal();
        Nodo estadoInicial = geraEstadoInicial();

        System.out.println("Algoritmo definido: " + AlGORITMO.getDescricao());
        System.out.println("Estado inicial definido:");
        imprimeNodo(estadoInicial);

        Tabuleiro tabuleiro = new Tabuleiro(estadoInicial, estadoFinal);

        try {
            ResultadoBusca resultadoBusca = tabuleiro.acharCaminho();
            resultadoBusca.imprimeTela();
        } catch (Exception erro) {
            System.out.println("Algum erro aconteceu!" + erro);
        }
    }

    private static Nodo defineEstadoFinal() {
        
        ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));

        
        int custo = 0;

        
        Nodo estadoFinal = new Nodo(quadrados, custo);

        OBJETIVO = estadoFinal;

        for (int i = 0; i < quadrados.size(); i++) {
            MAP_OBJETIVO_POSICAO.put(quadrados.get(i), i);
        }

        
        return estadoFinal;
    }

    private static void defineAlgoritmo() {
        System.out.println("Defina qual sera o algoritmo utilizado. (Digite o numero correspondente)");

       
        Algoritmo[] algoritmos = Algoritmo.values();
        
        int contador = 1;

     
        for (Algoritmo algoritmo : algoritmos) {
            System.out.println((contador++) + " - " + algoritmo.getDescricao());
        }

        
        Scanner scaner = new Scanner(System.in);

        
        int escolha = scaner.nextInt();

        if ( (escolha < 1) || (escolha > (algoritmos.length)) ) {
            escolha = 0;
        } else {
            escolha = escolha - 1;
        }

        
        Algoritmo algoritmoEscolhido = algoritmos[escolha];

        
        AlGORITMO = algoritmoEscolhido;
    }

    private static Nodo geraEstadoInicial() {
        
        Scanner scaner = new Scanner(System.in);

        
        ArrayList<Integer> valoresEstadoInicial = new ArrayList<>();

        int posicaoAtual = 1;

        System.out.println("Defina os valores do estado inical do jogo.");
        System.out.println("Digite 9 numeros inteiros que representam os valores das posicoes do tabuleiro do jogo, " +
                "(de cima pra baixo, da esquerda para a direita (zero representa a posicao vazia).");

        while (valoresEstadoInicial.size() < 9) {
            System.out.println();
            System.out.println("Valores ja informados: " + valoresEstadoInicial.toString());
            System.out.println("Entre com um valor para a posicao " + posicaoAtual +  " do tabuleiro:");

            
            int valor = scaner.nextInt();

            
            if ( (valor < 0) || (valor > 8)) {
                System.out.println("Valor " + valor +  " digitado invalido! o valor deve ser maior ou igual a zero, " +
                    "e menor que nove!");
            } else if (valoresEstadoInicial.contains(valor)) {
                System.out.println("Valor " + valor +  " digitado invalido! esse valor ja foi adicionado! " +
                        "Favor informar um valor diferente.");
            } else {
               
                valoresEstadoInicial.add(valor);
            }
        }

        
        int custo = 0;

        
        Nodo estadoInicial = new Nodo(valoresEstadoInicial, custo);

     
        return estadoInicial;
        
    }

    public static void imprimeCaminho(List<Nodo> caminho) {
        for(Nodo nodo : caminho){
            imprimeNodo(nodo);
        }
    }

    private static void imprimeNodo(Nodo nodo) {
        int posicaoLinha = 1;

        for(int posicao : nodo.getQuadrados()) {
           
            if (posicaoLinha == 1) System.out.println("+---+---+---+");

            
            System.out.print("| " + (posicao != 0 ? posicao : " ") + " ");

            if (posicaoLinha++ == 3) {
                
                posicaoLinha = 1;
                
                System.out.println("|");
            }
        }
        
        System.out.println("+---+---+---+\n");
    }

		
	}	


