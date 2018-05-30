package esrastrigin;

import java.util.ArrayList;

public class PopulacaoBinario extends Populacao<Integer> {

    public PopulacaoBinario(int tamanho, Problema problema) {
        this.tamanho = tamanho;
        this.problema = problema;
    }

    @Override
    public void criar() {
        this.individuos = new ArrayList<>();

        for (int i = 1; i <= this.tamanho; i++) {
            Individuo individuo = new IndividuoBinario(this.problema.getDimensao());
            individuo.criar();
            this.individuos.add(individuo);
        }

    }

    public void avaliar(double min, double max, int precisao) {
        for (Individuo ind : this.getIndividuos()) {
            IndividuoDouble indDbl = ((IndividuoBinario) ind).decodificar(min, max, precisao);
            this.getProblema().calcularFuncaoObjetivo(indDbl);
            ind.setFuncaoObjetivo(indDbl.getFuncaoObjetivo());
        }
    }

}
