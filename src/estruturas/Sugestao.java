/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo motor de sugestões (Autocomplete).
 * <p>
 * Esta classe utiliza o Dicionário e a lógica de proximidade do teclado
 * para sugerir palavras mesmo que o utilizador tenha cometido erros
 * de digitação (ex: trocou 'a' por 's').
 * </p>
 *
 * @author Jose Lopes
 * @version 1.0
 */
public class Sugestao {
    
    /** Referência para o dicionário carregado onde faremos as pesquisas. */
    private Dicionario dicionario;

    /**
     * Construtor da classe Sugestao.
     *
     * @param dicionario O objeto Dicionario já inicializado e com dados carregados.
     */
    public Sugestao(Dicionario dicionario) {
        this.dicionario = dicionario;
    }

    /**
     * Gera uma lista de sugestões baseada no input do utilizador.
     * O algoritmo segue 3 passos principais:
     * <ol>
     * <li><b>Expansão:</b> Gera variações da palavra digitada considerando teclas vizinhas 
     * (ex: se digitou "o", tenta também "i", "p", etc.).</li>
     * <li><b>Pesquisa:</b> Procura no dicionário palavras que comecem por esses prefixos.</li>
     * <li><b>Ranking:</b> Ordena os resultados pela frequência de uso e limita ao Top 10.</li>
     * </ol>
     *
     * @param inputUsuario A string parcial que o utilizador digitou na interface.
     * @return Uma lista de objetos {@code Palavra} (máximo 10), ordenada por relevância.
     */
    public List<Palavra> sugerir(String inputUsuario) {
        List<String> prefixosAtivos = new ArrayList<>();
        prefixosAtivos.add(""); // Começamos com uma string vazia

        // 1. Gerar todos os prefixos possíveis com base nos vizinhos
        // Exemplo: se input é "o", gera ["o", "i", "p", "k", "l"]
        for (char letraDigitada : inputUsuario.toCharArray()) {
            List<String> novosPrefixos = new ArrayList<>();
            char[] vizinhos = Teclado.getVizinhos(letraDigitada);

            for (String prefixoAntigo : prefixosAtivos) {
                for (char vizinho : vizinhos) {
                    if (novosPrefixos.size() > 1000) { 
                        break;
                    }
                    novosPrefixos.add(prefixoAntigo + vizinho);
                }
            }
            // Atualizamos a lista (cuidado: isto cresce rápido, depois limitamos)
            prefixosAtivos = novosPrefixos;
        }

        // 2. Procurar palavras no dicionário para cada prefixo gerado
        List<Palavra> candidatas = new ArrayList<>();
        
        for (String prefixo : prefixosAtivos) {
            // Usa o método que já criaste no Dicionario!
            int index = dicionario.encontrarInicio(prefixo);
            
            if (index != -1) {
                // Vai buscar palavras que comecem por este prefixo
                ArrayList<Palavra> listaDic = dicionario.getLista();
                for (int i = index; i < listaDic.size(); i++) {
                    Palavra p = listaDic.get(i);
                    if (!p.getTermo().startsWith(prefixo)) break;
                    
                    // Adiciona à lista de sugestões (evitando duplicados se quiseres refinar depois)
                    if (!candidatas.contains(p)) {
                        candidatas.add(p);
                    }
                    
                    // Otimização: Não buscar demasiadas palavras por prefixo
                    if (i > index + 200) break; 
                }
            }
        }
        
        // 3. Ordenar as candidatas pela frequência (as mais comuns primeiro)
        candidatas.sort((p1, p2) -> Integer.compare(p2.getFrequencia(), p1.getFrequencia()));

        // Retorna apenas as Top 10
        if (candidatas.size() > 10) {
            return candidatas.subList(0, 10);
        }
        return candidatas;
    }
}