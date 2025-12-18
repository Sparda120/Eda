/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Classe responsável por gerir o dicionário de palavras.
 * <p>
 * Esta classe encarrega-se de carregar dados de ficheiros de texto,
 * processá-los (limpeza e ordenação via BST) e fornecer mecanismos de 
 * pesquisa rápida (Pesquisa Binária) para a funcionalidade de autocompletar.
 * </p>
 * * @author Jose Lopes
 * @version 1.0
 */
public class Dicionario {
    
    /** Lista final onde as palavras ficam guardadas de forma ordenada para acesso rápido. */
    private ArrayList<Palavra> listaOrdenada;

    /**
     * Construtor padrão da classe Dicionario.
     * Inicializa a lista vazia.
     */
    public Dicionario() {
        this.listaOrdenada = new ArrayList<>();
    }

    // 1. Carregar texto para BST e depois converter para ArrayList

    /**
     * Carrega o texto de um ficheiro, processa as palavras e armazena-as.
     * <p>
     * O processo ocorre em duas fases:
     * 1. As palavras são inseridas numa <b>BST (Árvore Binária de Pesquisa)</b> temporária.
     * Isto garante automaticamente que não há duplicados e que ficam ordenadas.
     * 2. Os dados da árvore são transferidos para um <code>ArrayList</code>.
     * </p>
     * Este método também realiza uma limpeza básica: converte para minúsculas e 
     * remove pontuação que não seja letras (a-z ou caracteres acentuados).
     * * @param caminho O caminho (path) do ficheiro de texto a ser lido.
     */
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

    /**
     * Executa uma Pesquisa Binária para encontrar o índice inicial de um prefixo.
     * <p>
     * Ao contrário de uma pesquisa binária normal que para assim que encontra
     * uma correspondência, esta versão continua a tentar ir para a "esquerda" (início da lista)
     * mesmo depois de encontrar um match. Isto serve para garantir que encontramos 
     * a <b>primeira</b> palavra que começa com aquele prefixo, e não uma palavra a meio.
     * </p>
     * * @param prefixo As primeiras letras da palavra (o que o utilizador escreveu até agora).
     * @return O índice (int) da primeira palavra na lista que começa com o prefixo, 
     * ou -1 se nenhuma palavra for encontrada.
     */
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
    
    /**
     * Obtém a lista completa de palavras ordenadas.
     * Método auxiliar necessário para apresentar os dados na interface gráfica ou consola.
     * * @return O ArrayList contendo objetos do tipo Palavra.
     */
    public ArrayList<Palavra> getLista() {
        return listaOrdenada;
    }
}