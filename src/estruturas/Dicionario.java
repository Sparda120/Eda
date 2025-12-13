/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Dicionario {
    private ArrayList<Palavra> listaOrdenada;

    public Dicionario() {
        this.listaOrdenada = new ArrayList<>();
    }

    // 1. Carregar texto para BST e depois converter para ArrayList
    public void carregarDoFicheiro(String caminho) {
        BST arvoreTemp = new BST();
        System.out.println("A carregar dicionario...");
        
        try {
            Scanner scanner = new Scanner(new File(caminho));
            while (scanner.hasNext()) {
                // Limpeza básica: minúsculas e remove pontuação que não seja letras
                String token = scanner.next().toLowerCase().replaceAll("[^a-zà-ú]", "");
                if (token.length() > 0) {
                    arvoreTemp.inserir(token);
                }
            }
            scanner.close();
            
            // Passar da Árvore para a Lista
            arvoreTemp.preencherLista(listaOrdenada);
            System.out.println("Sucesso! Dicionario carregado com " + listaOrdenada.size() + " palavras unicas.");
            
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Nao encontrei o ficheiro: " + e.getMessage());
        }
    }

    // 2. Pesquisa Binária para encontrar o início de um prefixo
    public int encontrarInicio(String prefixo) {
        int inicio = 0;
        int fim = listaOrdenada.size() - 1;
        int resultado = -1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String palavraMeio = listaOrdenada.get(meio).getTermo();

            if (palavraMeio.startsWith(prefixo)) {
                resultado = meio; 
                fim = meio - 1; // Continua para a esquerda para achar o PRIMEIRO
            } else if (palavraMeio.compareTo(prefixo) < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return resultado;
    }
    
    // Método auxiliar para obteres a lista (vamos precisar na interface)
    public ArrayList<Palavra> getLista() {
        return listaOrdenada;
    }
}