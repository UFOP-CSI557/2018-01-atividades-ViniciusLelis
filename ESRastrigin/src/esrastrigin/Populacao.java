package esrastrigin;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Populacao<T> {

    ArrayList<Individuo<T>> individuos = new ArrayList<>();
    int tamanho;
    Problema problema;

    public ArrayList<Individuo<T>> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo<T>> individuos) {
        this.individuos = individuos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }
    
    public void avaliar() {
        for(Individuo individuo : this.individuos) {
            this.problema.calcularFuncaoObjetivo(individuo);
        }
    }
    
    public Individuo getMelhorIndividuo() {
        return Collections.min(this.individuos);
    }    

    abstract void criar();
    
}
