/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;

public class BST {
    
    // Classe interna para o Nó da árvore
    private class No {
        Palavra dado;
        No esquerda, direita;

        public No(String termo) {
            this.dado = new Palavra(termo);
        }
    }

    private No raiz;

    // Método principal de inserção
    public void inserir(String termo) {
        raiz = inserirRec(raiz, termo);
    }

    private No inserirRec(No atual, String termo) {
        if (atual == null) {
            return new No(termo);
        }

        int comparacao = termo.compareTo(atual.dado.getTermo());

        if (comparacao < 0) {
            atual.esquerda = inserirRec(atual.esquerda, termo);
        } else if (comparacao > 0) {
            atual.direita = inserirRec(atual.direita, termo);
        } else {
            // Palavra já existe, aumentamos a frequência
            atual.dado.incrementarFreq();
        }
        return atual;
    }

    // Passar os dados da Árvore para uma Lista (In-Order = Já ordenado!)
    public void preencherLista(ArrayList<Palavra> lista) {
        inOrder(raiz, lista);
    }

    private void inOrder(No no, ArrayList<Palavra> lista) {
        if (no != null) {
            inOrder(no.esquerda, lista);
            lista.add(no.dado);
            inOrder(no.direita, lista);
        }
    }
}