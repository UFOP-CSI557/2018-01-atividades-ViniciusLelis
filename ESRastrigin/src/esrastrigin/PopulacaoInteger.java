package esrastrigin;

import java.util.ArrayList;

public class PopulacaoInteger extends Populacao<Integer> {

    public PopulacaoInteger(int tamanho, Problema problema) {
        this.tamanho = tamanho;
        this.problema = problema;
    }

    @Override
    public void criar() {
        this.individuos = new ArrayList<>();

        for (int i = 1; i <= this.tamanho; i++) {
            Individuo individuo = new IndividuoInteger(this.problema.getDimensao());
            individuo.criar();
            this.individuos.add(individuo);
        }
    }

}
