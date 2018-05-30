package esrastrigin;

import java.util.Random;

public class IndividuoBinario extends IndividuoInteger {

    public IndividuoBinario(Integer dimensao) {
        super(dimensao);
    }

    @Override
    public void criar() {

        Random rnd = new Random();

        for (int i = 0; i < this.getDimensao(); i++) {
            if (rnd.nextDouble() <= 0.5) {
                this.getCromossomos().add(0);
            } else {
                this.getCromossomos().add(1);
            }
        }
    }

    public IndividuoDouble decodificar(Double minimo, Double maximo, Integer precisao) {

        int nvar = this.getDimensao() / precisao;
        IndividuoDouble ind = new IndividuoDouble(minimo, maximo, nvar);
        int valor;
        double real;

        for (int i = 0; i < nvar; i++) {
            valor = 0;
            for (int j = 0; j < precisao; j++) {
                valor += Math.pow(2, precisao - j - 1)
                        * this.getCromossomos().get(i
                                * precisao + j);
            }

            real = valor;
            real = real * ((maximo - minimo)
                    / (Math.pow(2, precisao) - 1.0))
                    + minimo;
            ind.getCromossomos().add(real);

        }

        return ind;
    }
}
