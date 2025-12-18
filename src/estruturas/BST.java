/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;

/**
 * Árvore Binária de Pesquisa (Binary Search Tree - BST).
 * Estrutura de dados utilizada para guardar as palavras do dicionário.
 * <p>
 * A grande vantagem desta implementação é que, ao inserirmos, os dados ficam 
 * automaticamente organizados, eliminando a necessidade de usar algoritmos 
 * de ordenação lentos posteriormente.
 * </p>
 * * @author Jose Lopes
 * @version 1.0
 */
public class BST {
    
    /**
     * Classe interna que representa cada "nó" ou ponto da árvore.
     * É privada (encapsulada) pois quem usa a BST não precisa de conhecer a sua estrutura interna.
     */
    private class No {
        /** A informação guardada (objeto Palavra contendo termo + frequência). */
        Palavra dado;       
        /** Referências para os filhos menores (esquerda) e maiores (direita). */
        No esquerda, direita; 

        /**
         * Construtor do nó.
         * @param termo A String que dará origem ao objeto Palavra.
         */
        public No(String termo) {
            this.dado = new Palavra(termo);
        }
    }

    /** O nó raiz (topo) da árvore. */
    private No raiz; 

    // --- MÉTODOS PÚBLICOS ---

    /**
     * Método principal para inserir uma nova palavra na árvore.
     * O processo inicia-se sempre pela raiz.
     * * @param termo A String a ser inserida ou contabilizada (caso já exista).
     */
    public void inserir(String termo) {
        raiz = inserirRec(raiz, termo);
    }

    /**
     * Transfere os dados da Árvore para uma Lista (ArrayList).
     * <p>
     * Esta conversão é útil porque a pesquisa binária (em array/lista) pode ser 
     * mais vantajosa para as operações seguintes do que a estrutura em árvore.
     * </p>
     * * @param lista A lista de destino onde as palavras serão guardadas.
     */
    public void preencherLista(ArrayList<Palavra> lista) {
        inOrder(raiz, lista);
    }

    // --- MÉTODOS PRIVADOS (Lógica Recursiva) ---

    /**
     * Lógica recursiva de inserção.
     * Percorre a árvore até encontrar o local correto ou a palavra existente.
     * * @param atual O nó que está a ser analisado na recursão atual.
     * @param termo A palavra a inserir.
     * @return O nó atualizado (necessário para reconstruir as referências na recursão).
     */
    private No inserirRec(No atual, String termo) {
        // 1. Caso base: chegámos a um espaço vazio, criamos o novo nó aqui.
        if (atual == null) {
            return new No(termo);
        }

        // 2. Comparamos alfabeticamente para saber para onde ir
        int comparacao = termo.compareTo(atual.dado.getTermo());

        if (comparacao < 0) {
            // Se for menor (ex: "amor" < "casa"), vai para a esquerda
            atual.esquerda = inserirRec(atual.esquerda, termo);
        } else if (comparacao > 0) {
            // Se for maior (ex: "zebra" > "casa"), vai para a direita
            atual.direita = inserirRec(atual.direita, termo);
        } else {
            // 3. Se for igual, a palavra JÁ EXISTE!
            // Em vez de criar duplicado, apenas aumentamos o contador de frequência.
            atual.dado.incrementarFreq();
        }
        return atual;
    }

    /**
     * Percorre a árvore em ordem "In-Order" (Esquerda -> Raiz -> Direita).
     * <p>
     * O resultado prático é que as palavras são inseridas na lista JÁ ORDENADAS 
     * alfabeticamente (A-Z).
     * </p>
     * * @param no O nó atual da travessia.
     * @param lista A lista onde os dados são acumulados.
     */
    private void inOrder(No no, ArrayList<Palavra> lista) {
        if (no != null) {
            inOrder(no.esquerda, lista); // Visita os menores
            lista.add(no.dado);          // Guarda o atual
            inOrder(no.direita, lista);  // Visita os maiores
        }
    }
}