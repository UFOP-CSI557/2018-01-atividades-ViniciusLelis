package esrastrigin;

import java.util.ArrayList;
import java.util.Collections;

public class IndividuoInteger implements Individuo<Integer> {

    // Gen�tipo+Fenotipo
    // Representa��o - ordem de visita��o das cidades
    private ArrayList<Integer> cromossomos;
    // Custo da fun��o objetivo
    Double funcaoObjetivo;

    // Dimens�o do problema
    Integer dimensao;

    public IndividuoInteger(Integer dimensao) {
        this.dimensao = dimensao;
        this.cromossomos = new ArrayList<>();
    }

    @Override
    public ArrayList<Integer> getCromossomos() {
        return cromossomos;
    }

    @Override
    public void setCromossomos(ArrayList<Integer> cromossomos) {
        this.cromossomos = cromossomos;
    }

    @Override
    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    @Override
    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public Integer getDimensao() {
        return dimensao;
    }

    public void setDimensao(Integer dimensao) {
        this.dimensao = dimensao;
    }

    // Gerar o gen�tipo
    @Override
    public void criar() {

        this.cromossomos = new ArrayList<>();

        for (int i = 1; i <= this.getDimensao(); i++) {
            this.cromossomos.add(i);
        }
        Collections.shuffle(this.cromossomos);
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo()
                .compareTo(o.getFuncaoObjetivo());
    }

    @Override
    public Individuo<Integer> clone() {
        Individuo individuo = new IndividuoInteger(dimensao);
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());

        return individuo;
    }

    @Override
    public String toString() {
        return "Individuo{" + "cromossomos=" + cromossomos + ", funcaoObjetivo=" + funcaoObjetivo + ", dimensao=" + dimensao + '}';
    }

}
