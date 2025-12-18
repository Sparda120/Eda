/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eda;
import estruturas.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author spard
 */

public class Eda {
    public static void main(String[] args) {
        System.out.println("A carregar dicionario...");
        
        // 1. Preparar os dados (Backend)
        Dicionario dic = new Dicionario();
        dic.carregarDoFicheiro("lusiadas.txt"); // Ou o teu ficheiro
        
        Sugestao motor = new Sugestao(dic);
        
        // 2. Lançar a Interface Gráfica (Frontend)
        // Usamos o 'invokeLater' que é a boa prática para interfaces em Java
        javax.swing.SwingUtilities.invokeLater(() -> {
            Janela minhaJanela = new Janela(motor);
            minhaJanela.setVisible(true);
        });
    }
}
