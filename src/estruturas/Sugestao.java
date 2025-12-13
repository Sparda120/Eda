/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.ArrayList;
import java.util.List;

public class Sugestao {
    
    private Dicionario dicionario;

    public Sugestao(Dicionario dicionario) {
        this.dicionario = dicionario;
    }

    // Este é o método principal que vamos chamar na interface gráfica
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
                    if (i > index + 5) break; 
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
