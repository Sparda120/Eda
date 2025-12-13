/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturas;

/**
 *
 * @author spard
 */
public class Palavra implements Comparable<Palavra>{
    private String termo;
    private int frequencia;
    
    public Palavra(String termo) {
        this.termo = termo;
        this.frequencia = 1;
    }
    
    public void incrementarFreq(){
        this.frequencia++;
    }
    public String getTermo() {
        return termo;
    }
    public int getFrequencia(){
        return frequencia;
    }
    @Override
    public String toString(){
        return termo + " (" + frequencia + ")";
    }
    
    @Override
    public int compareTo (Palavra outra){
        return this.termo.compareTo(outra.termo);
    }
}
