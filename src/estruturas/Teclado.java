/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa a disposição física de um teclado QWERTY.
 * <p>
 * Esta classe é utilizada para simular "erros de digitação" ou encontrar
 * teclas próximas. É fundamental para o algoritmo de sugestão, permitindo
 * identificar que se o utilizador digitou 's', poderia estar a tentar digitar
 * 'a', 'w', 'e', 'd', 'z' ou 'x'.
 * </p>
 *
 * @author Jose Lopes
 * @version 1.0
 */
public class Teclado {
    
    /** * Mapeamento estático de cada tecla para a sua lista de vizinhos.
     * A chave é o caracter central e o valor é um array com os adjacentes.
     */
    private static final Map<Character, char[]> vizinhos = new HashMap<>();

    /*
     * Bloco estático de inicialização.
     * Executado apenas uma vez quando a classe é carregada em memória.
     * Define as relações de vizinhança para letras minúsculas.
     */
    static {
        // Linha de cima
        vizinhos.put('q', new char[]{'q', 'w', 'a'});
        vizinhos.put('w', new char[]{'w', 'q', 'e', 'a', 's'});
        vizinhos.put('e', new char[]{'e', 'w', 'r', 's', 'd'});
        vizinhos.put('r', new char[]{'r', 'e', 't', 'd', 'f'});
        vizinhos.put('t', new char[]{'t', 'r', 'y', 'f', 'g'});
        vizinhos.put('y', new char[]{'y', 't', 'u', 'g', 'h'});
        vizinhos.put('u', new char[]{'u', 'y', 'i', 'h', 'j'});
        vizinhos.put('i', new char[]{'i', 'u', 'o', 'j', 'k'});
        vizinhos.put('o', new char[]{'o', 'i', 'p', 'k', 'l'});
        vizinhos.put('p', new char[]{'p', 'o', 'l'});

        // Linha do meio
        vizinhos.put('a', new char[]{'a', 'q', 'w', 's', 'z'});
        vizinhos.put('s', new char[]{'s', 'a', 'w', 'e', 'd', 'z', 'x'});
        vizinhos.put('d', new char[]{'d', 's', 'e', 'r', 'f', 'x', 'c'});
        vizinhos.put('f', new char[]{'f', 'd', 'r', 't', 'g', 'c', 'v'});
        vizinhos.put('g', new char[]{'g', 'f', 't', 'y', 'h', 'v', 'b'});
        vizinhos.put('h', new char[]{'h', 'g', 'y', 'u', 'j', 'b', 'n'});
        vizinhos.put('j', new char[]{'j', 'h', 'u', 'i', 'k', 'n', 'm'});
        vizinhos.put('k', new char[]{'k', 'j', 'i', 'o', 'l', 'm'});
        vizinhos.put('l', new char[]{'l', 'k', 'o', 'p'});

        // Linha de baixo
        vizinhos.put('z', new char[]{'z', 'a', 's', 'x'});
        vizinhos.put('x', new char[]{'x', 'z', 's', 'd', 'c'});
        vizinhos.put('c', new char[]{'c', 'x', 'd', 'f', 'v'});
        vizinhos.put('v', new char[]{'v', 'c', 'f', 'g', 'b'});
        vizinhos.put('b', new char[]{'b', 'v', 'g', 'h', 'n'});
        vizinhos.put('n', new char[]{'n', 'b', 'h', 'j', 'm'});
        vizinhos.put('m', new char[]{'m', 'n', 'j', 'k'});
    }

    /**
     * Obtém a lista de teclas vizinhas a um determinado caracter.
     * <p>
     * Nota: O array retornado inclui o próprio caracter passado como argumento,
     * para garantir que a letra original também é considerada nas sugestões.
     * </p>
     *
     * @param c O caracter para o qual queremos encontrar os vizinhos.
     * @return Um array de char contendo a própria letra e as suas adjacentes no teclado.
     * Se a letra não estiver mapeada (ex: 'ç', '1' ou símbolos), retorna um array
     * apenas com o próprio caracter.
     */
    public static char[] getVizinhos(char c) {
        // Se a letra não estiver no mapa (ex: 'ç' ou '1'), devolve ela própria
        if (!vizinhos.containsKey(c)) {
            return new char[]{c};
        }
        return vizinhos.get(c);
    }
}