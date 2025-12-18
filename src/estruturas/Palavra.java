/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

/**
 * Representa uma entidade de "Palavra" para o dicionário.
 * <p>
 * Esta classe armazena não só a string do termo em si, mas também a sua
 * frequência (quantas vezes apareceu no texto).
 * Implementa a interface {@code Comparable} para permitir a ordenação alfabética
 * automática na Árvore Binária e nas Listas.
 * </p>
 *
 * @author Jose Lopes
 * @version 1.0
 */
public class Palavra implements Comparable<Palavra> {
    
    /** O termo textual (a palavra em si). */
    private String termo;
    
    /** Contador de quantas vezes a palavra apareceu no texto. */
    private int frequencia;
    
    /**
     * Construtor da classe Palavra.
     * Inicializa o termo e define a frequência inicial como 1.
     *
     * @param termo A string que representa a palavra.
     */
    public Palavra(String termo) {
        this.termo = termo;
        this.frequencia = 1;
    }
    
    /**
     * Incrementa o contador de frequência da palavra em uma unidade.
     * Deve ser chamado quando o dicionário encontra uma palavra repetida.
     */
    public void incrementarFreq(){
        this.frequencia++;
    }

    /**
     * Obtém o termo textual.
     *
     * @return A string da palavra.
     */
    public String getTermo() {
        return termo;
    }

    /**
     * Obtém a frequência atual da palavra.
     *
     * @return O número de vezes que a palavra ocorreu.
     */
    public int getFrequencia(){
        return frequencia;
    }

    /**
     * Retorna uma representação textual da palavra e da sua frequência.
     * Exemplo: "casa (5)"
     *
     * @return String formatada com termo e frequência.
     */
    @Override
    public String toString(){
        return termo + " (" + frequencia + ")";
    }
    
    /**
     * Compara esta palavra com outra para efeitos de ordenação.
     * A comparação é feita alfabeticamente baseada no atributo {@code termo}.
     *
     * @param outra O objeto Palavra a ser comparado.
     * @return Um inteiro negativo, zero ou positivo, conforme esta palavra seja
     * alfabeticamente menor, igual ou maior que a outra.
     */
    @Override
    public int compareTo(Palavra outra){
        return this.termo.compareTo(outra.termo);
    }
}
