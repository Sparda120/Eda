/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eda;

import estruturas.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe principal (Main) que inicializa a aplicação "Teclado Inteligente".
 * Esta classe é responsável por orquestrar o arranque do sistema em dois passos:
 * <ol>
 * <li><b>Backend:</b> Carrega o dicionário e prepara o motor de sugestões.</li>
 * <li><b>Frontend:</b> Lança a interface gráfica (GUI) de forma segura.</li>
 * </ol>
 *
 * @author spard
 * @version 1.0
 */
public class Main {

    /**
     * Ponto de entrada da aplicação (Entry Point).
     *
     * @param args Argumentos da linha de comandos (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        System.out.println("A carregar dicionario...");
        
        // --- 1. Preparar os dados (Backend) ---
        Dicionario dic = new Dicionario();
        
        // Carrega o ficheiro de texto principal
        dic.carregarDoFicheiro("lusiadas.txt"); 
        
        // (Opcional) Carrega um segundo ficheiro se necessário, ou ignora se vazio
        dic.carregarDoFicheiro("");
        
        // Inicializa o motor de sugestões com o dicionário carregado
        Sugestao motor = new Sugestao(dic);
        
        // --- 2. Lançar a Interface Gráfica (Frontend) ---
        
        /* * Usamos o 'invokeLater' que é a boa prática para interfaces Swing em Java.
         * Garante que a criação da janela ocorre na 'Event Dispatch Thread' (EDT),
         * prevenindo erros visuais ou de concorrência.
         */
        javax.swing.SwingUtilities.invokeLater(() -> {
            Janela minhaJanela = new Janela(motor);
            minhaJanela.setVisible(true);
        });
    }
}
