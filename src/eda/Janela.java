/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eda;

import estruturas.Sugestao;
import estruturas.Palavra;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Janela extends JFrame {

    private Sugestao motor;
    private JTextField caixaTexto;
    private JList<String> listaSugestoes;
    private DefaultListModel<String> modeloLista;

    public Janela(Sugestao motor) {
        this.motor = motor;

        // 1. Configurações básicas da janela
        setTitle("Teclado Inteligente EDA");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Criar a caixa de texto (onde escreves)
        caixaTexto = new JTextField();
        caixaTexto.setFont(new Font("Arial", Font.PLAIN, 24));
        add(caixaTexto, BorderLayout.NORTH);

        // 3. Criar a lista de sugestões
        modeloLista = new DefaultListModel<>();
        listaSugestoes = new JList<>(modeloLista);
        listaSugestoes.setFont(new Font("Arial", Font.BOLD, 18));
        add(new JScrollPane(listaSugestoes), BorderLayout.CENTER);

        // 4. A Magia: Detetar cada tecla que escreves
        caixaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarSugestoes();
            }
        });
    }

    private void atualizarSugestoes() {
        String texto = caixaTexto.getText();
        
        // Limpa a lista atual
        modeloLista.clear();

        // Se não houver texto, não faz nada
        if (texto.isEmpty()) return;

        // Pede ao motor as sugestões (a lógica que já criaste!)
        List<Palavra> sugestoes = motor.sugerir(texto);

        // Adiciona as sugestões à lista visual
        for (Palavra p : sugestoes) {
            modeloLista.addElement(p.toString());
        }
    }
}
