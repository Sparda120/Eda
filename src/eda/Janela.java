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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Janela extends JFrame {

    private Sugestao motor;
    private JTextField visor;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaSugestoes;

    public Janela(Sugestao motor) {
        this.motor = motor;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // 1. CORREÇÃO DE TAMANHO: Aumentei a largura para 600px para caberem as teclas todas
        setTitle("Teclado Inteligente");
        setSize(600, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Centralizar a janela no ecrã
        setLocationRelativeTo(null); 

        // --- PARTE DE CIMA: Visor e Sugestões ---
        JPanel painelCima = new JPanel(new BorderLayout(5, 5));
        painelCima.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Visor
        visor = new JTextField();
        visor.setFont(new Font("Arial", Font.PLAIN, 28));
        visor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarSugestoes();
            }
        });
        painelCima.add(visor, BorderLayout.NORTH);

        // Lista de Sugestões
        modeloLista = new DefaultListModel<>();
        listaSugestoes = new JList<>(modeloLista);
        listaSugestoes.setFont(new Font("Arial", Font.BOLD, 18));
        listaSugestoes.setVisibleRowCount(5);
        
        // Evento de clique na sugestão
        listaSugestoes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { 
                    completarPalavra();
                }
            }
        });

        painelCima.add(new JScrollPane(listaSugestoes), BorderLayout.CENTER);
        add(painelCima, BorderLayout.CENTER);

        // --- PARTE DE BAIXO: Teclado Virtual ---
        // GridLayout para organizar as 4 linhas
        JPanel painelTeclado = new JPanel(new GridLayout(4, 1, 5, 5));
        painelTeclado.setBorder(new EmptyBorder(10, 10, 20, 10));

        String[] linha1 = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
        String[] linha2 = {"a", "s", "d", "f", "g", "h", "j", "k", "l"};
        String[] linha3 = {"z", "x", "c", "v", "b", "n", "m"};
        String[] linha4 = {"SPACE", "DEL"}; 

        painelTeclado.add(criarLinha(linha1));
        painelTeclado.add(criarLinha(linha2));
        painelTeclado.add(criarLinha(linha3));
        painelTeclado.add(criarLinha(linha4));

        add(painelTeclado, BorderLayout.SOUTH);
    }

    private JPanel criarLinha(String[] teclas) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        for (String letra : teclas) {
            JButton botao = new JButton(letra.toUpperCase());
            
            // CORREÇÃO: Botões ligeiramente mais pequenos para garantir que cabem
            if (letra.length() > 1) {
                botao.setPreferredSize(new Dimension(100, 45)); // Espaço e Del maiores
            } else {
                botao.setPreferredSize(new Dimension(50, 45)); // Letras quadradas
            }
            
            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.setFocusable(false); // Importante: não rouba o cursor do texto

            botao.addActionListener(e -> {
                processarBotao(letra);
                visor.requestFocusInWindow(); // Mantém o cursor a piscar no texto
            });
            painel.add(botao);
        }
        return painel;
    }

    private void processarBotao(String letra) {
        String texto = visor.getText();
        if (letra.equals("DEL")) {
            if (texto.length() > 0) visor.setText(texto.substring(0, texto.length() - 1));
        } else if (letra.equals("SPACE")) {
            visor.setText(texto + " ");
        } else {
            visor.setText(texto + letra);
        }
        atualizarSugestoes();
    }

    private void atualizarSugestoes() {
        String texto = visor.getText();
        
        // Se o texto estiver vazio ou terminar com espaço, não sugerimos nada
        if (texto.isEmpty() || texto.endsWith(" ")) {
            modeloLista.clear();
            return;
        }

        // Pega na última palavra
        String[] partes = texto.split(" ");
        String ultimaPalavra = "";
        if (partes.length > 0) {
            ultimaPalavra = partes[partes.length - 1];
        }

        // Se a última palavra for vazia, sai
        if (ultimaPalavra.isEmpty()) return;

        // Debug para veres na consola se está a chamar o motor
        System.out.println("A procurar sugestoes para: '" + ultimaPalavra + "'");

        List<Palavra> sugestoes = motor.sugerir(ultimaPalavra);
        
        modeloLista.clear();
        for (Palavra p : sugestoes) {
            modeloLista.addElement(p.getTermo());
        }
    }

    private void completarPalavra() {
        String escolhida = listaSugestoes.getSelectedValue();
        if (escolhida == null) return;

        String textoAtual = visor.getText();
        int ultimoEspaco = textoAtual.lastIndexOf(" ");
        
        String novoTexto;
        if (ultimoEspaco == -1) {
            novoTexto = escolhida + " ";
        } else {
            String inicio = textoAtual.substring(0, ultimoEspaco + 1);
            novoTexto = inicio + escolhida + " ";
        }

        visor.setText(novoTexto);
        modeloLista.clear();
        visor.requestFocusInWindow();
    }
}