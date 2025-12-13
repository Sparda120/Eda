/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eda;

import estruturas.Sugestao;
import estruturas.Palavra;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Janela extends JFrame {

    private Sugestao motor;
    private JTextField visor; // O ecrã onde aparece o texto
    private DefaultListModel<String> modeloLista;
    private JList<String> listaSugestoes;

    public Janela(Sugestao motor) {
        this.motor = motor;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Smartphone EDA - Simulador");
        setSize(400, 650); // Formato mais alto, tipo telemóvel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(50, 50, 50)); // Fundo cinza escuro

        // --- PAINEL SUPERIOR (Visor + Sugestões) ---
        JPanel painelSuperior = new JPanel(new BorderLayout(5, 5));
        painelSuperior.setOpaque(false);
        painelSuperior.setBorder(new EmptyBorder(20, 15, 10, 15));

        // 1. Visor de Texto
        visor = new JTextField();
        visor.setFont(new Font("SansSerif", Font.PLAIN, 28));
        visor.setHorizontalAlignment(JTextField.LEFT);
        visor.setBackground(new Color(230, 230, 230));
        visor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // Permite escrever com o teclado físico também
        visor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarSugestoes();
            }
        });

        painelSuperior.add(visor, BorderLayout.NORTH);

        // 2. Lista de Sugestões (Estilo "Barra de Sugestões")
        modeloLista = new DefaultListModel<>();
        listaSugestoes = new JList<>(modeloLista);
        listaSugestoes.setFont(new Font("SansSerif", Font.BOLD, 16));
        listaSugestoes.setBackground(new Color(220, 240, 255)); // Azul clarinho
        listaSugestoes.setVisibleRowCount(4); // Mostra 4 linhas de sugestões
        
        // Colocar num ScrollPane
        JScrollPane scrollSugestoes = new JScrollPane(listaSugestoes);
        scrollSugestoes.setPreferredSize(new Dimension(300, 100));
        painelSuperior.add(scrollSugestoes, BorderLayout.CENTER);

        add(painelSuperior, BorderLayout.NORTH);

        // --- PAINEL DO TECLADO VIRTUAL ---
        JPanel painelTeclado = new JPanel();
        painelTeclado.setLayout(new GridLayout(4, 1, 5, 5)); // 4 linhas de teclas
        painelTeclado.setOpaque(false);
        painelTeclado.setBorder(new EmptyBorder(10, 15, 20, 15));

        // Definição das linhas do teclado QWERTY
        String[] linha1 = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
        String[] linha2 = {"a", "s", "d", "f", "g", "h", "j", "k", "l"};
        String[] linha3 = {"z", "x", "c", "v", "b", "n", "m"};
        String[] linha4 = {"SPACE", "DEL"}; // Teclas especiais

        painelTeclado.add(criarLinhaTeclas(linha1));
        painelTeclado.add(criarLinhaTeclas(linha2));
        painelTeclado.add(criarLinhaTeclas(linha3));
        painelTeclado.add(criarLinhaTeclas(linha4));

        add(painelTeclado, BorderLayout.CENTER);
    }

    // Método auxiliar para criar uma linha de botões
    private JPanel criarLinhaTeclas(String[] teclas) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        painel.setOpaque(false);

        for (String letra : teclas) {
            JButton botao = new JButton(letra.toUpperCase());
            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.setFocusable(false); // Para não roubar o foco do visor
            
            // Estilo do botão
            botao.setBackground(new Color(245, 245, 245));
            botao.setPreferredSize(new Dimension(letra.length() > 1 ? 80 : 45, 40)); // Botões maiores para SPACE/DEL

            // Ação ao clicar
            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    processarClique(letra);
                }
            });
            painel.add(botao);
        }
        return painel;
    }

    // Lógica quando clicas num botão do ecrã
    private void processarClique(String letra) {
        String textoAtual = visor.getText();

        if (letra.equals("DEL")) {
            if (textoAtual.length() > 0) {
                visor.setText(textoAtual.substring(0, textoAtual.length() - 1));
            }
        } else if (letra.equals("SPACE")) {
            visor.setText(textoAtual + " ");
        } else {
            visor.setText(textoAtual + letra);
        }
        atualizarSugestoes();
    }

    // O teu motor de sugestão entra aqui
    private void atualizarSugestoes() {
        String texto = visor.getText();
        
        // Pega na última palavra que está a ser escrita (depois do último espaço)
        String[] palavras = texto.split(" ");
        String ultimaPalavra = "";
        if (texto.length() > 0 && !texto.endsWith(" ")) {
            ultimaPalavra = palavras[palavras.length - 1];
        }

        modeloLista.clear();

        if (ultimaPalavra.isEmpty()) return;

        // Chama o motor inteligente
        List<Palavra> sugestoes = motor.sugerir(ultimaPalavra);

        for (Palavra p : sugestoes) {
            modeloLista.addElement(p.toString());
        }
    }
}