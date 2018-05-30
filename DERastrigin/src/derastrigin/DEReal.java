package derastrigin;

import java.util.Random;

public class DEReal implements Metodo {

    private Double minimo;
    private Double maximo;
    private Problema problema;

    // Criterio de parada
    private Integer gmax;
    // Numero de variaveis
    private Integer D;
    // Tamanho da populacao
    private Integer Np;
    // Coeficiente de mutacao
    private Double F;
    // Coeficiente de Crossover
    private Double Cr;

    public DEReal(Double minimo, Double maximo, Problema problema, Integer gmax, Integer D, Integer Np, Double F, Double Cr) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.problema = problema;
        this.gmax = gmax;
        this.D = D;
        this.Np = Np;
        this.F = F;
        this.Cr = Cr;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public Integer getGmax() {
        return gmax;
    }

    public void setGmax(Integer gmax) {
        this.gmax = gmax;
    }

    public Integer getD() {
        return D;
    }

    public void setD(Integer D) {
        this.D = D;
    }

    public Integer getNp() {
        return Np;
    }

    public void setNp(Integer Np) {
        this.Np = Np;
    }

    public Double getF() {
        return F;
    }

    public void setF(Double F) {
        this.F = F;
    }

    public Double getCr() {
        return Cr;
    }

    public void setCr(Double Cr) {
        this.Cr = Cr;
    }

    @Override
    public Individuo executar() {

        // Criacao da populacao inicial - X
        PopulacaoDouble populacao = new PopulacaoDouble(this.problema, this.minimo, this.maximo, this.D, this.Np);
        populacao.criar();

        // Avaliar a populacao inicial
        populacao.avaliar();

        // Nova populacao
        PopulacaoDouble novaPopulacao = new PopulacaoDouble();

        IndividuoDouble melhorSolucao = (IndividuoDouble) ((IndividuoDouble) populacao.getMelhorIndividuo()).clone();

        // Enquanto o criterio de parada nao for atingido
        for (int g = 1; g <= this.gmax; g++) {

            // Para cada vetor da populacao
            for (int i = 0; i < this.Np; i++) {

                // Selecionar r0, r1, r2
                Random rnd = new Random();
                int r0, r1, r2;

                do {
                    r0 = rnd.nextInt(this.Np);
                } while (r0 == i);

                do {
                    r1 = rnd.nextInt(this.Np);
                } while (r1 == r0);

                do {
                    r2 = rnd.nextInt(this.Np);
                } while (r2 == r1 || r2 == r0);

                IndividuoDouble trial = new IndividuoDouble(minimo, maximo, this.D);

                IndividuoDouble xr0 = (IndividuoDouble) populacao.getIndividuos().get(r0);
                IndividuoDouble xr1 = (IndividuoDouble) populacao.getIndividuos().get(r1);
                IndividuoDouble xr2 = (IndividuoDouble) populacao.getIndividuos().get(r2);

                // Gerar perturbacao - diferenca
                gerarPerturbacao(trial, xr1, xr2);
                // Mutacao - r0 + F * perturbacao
                mutacao(trial, xr0);

                // Target
                // DE/rand/1/bin
                IndividuoDouble target = (IndividuoDouble) populacao.getIndividuos().get(i);
                // Crossover
                crossover(trial, target);

                // Selecao
                problema.calcularFuncaoObjetivo(trial);

                if (trial.getFuncaoObjetivo() <= target.getFuncaoObjetivo()) {
                    novaPopulacao.getIndividuos().add(trial);
                } else {
                    novaPopulacao.getIndividuos().add(target.clone());
                }

            }

            // Populacao para a geracao seguinte
            populacao.getIndividuos().clear();
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            IndividuoDouble melhorDaPopulacao = (IndividuoDouble) populacao.getMelhorIndividuo();

            if (melhorDaPopulacao.getFuncaoObjetivo() <= melhorSolucao.getFuncaoObjetivo()) {
                melhorSolucao = (IndividuoDouble) melhorDaPopulacao.clone();
            }

            System.out.println("G = " + g + "\t"
                    + melhorSolucao.getFuncaoObjetivo());

        }

        return melhorSolucao;

    }

    private void gerarPerturbacao(IndividuoDouble trial, IndividuoDouble xr1, IndividuoDouble xr2) {

        // trial <- Diferenca entre r1 e r2
        for (int i = 0; i < this.D; i++) {
            Double diferenca = xr1.getCromossomos().get(i)
                    - xr2.getCromossomos().get(i);

            trial.getCromossomos().add(reparaValor(diferenca));
        }

    }

    private void mutacao(IndividuoDouble trial, IndividuoDouble xr0) {

        // trial <- r0 + F * perturbacao (trial)
        for (int i = 0; i < this.D; i++) {

            Double valor = this.F * xr0.getCromossomos().get(i)
                    + this.F * (trial.getCromossomos().get(i));

            trial.getCromossomos().set(i, reparaValor(valor));
        }

    }

    private void crossover(IndividuoDouble trial, IndividuoDouble target) {

        Random rnd = new Random();
        int j = rnd.nextInt(this.D);

        for (int i = 0; i < this.D; i++) {

            if (!(rnd.nextDouble() <= this.Cr || i == j)) {
                // Target
                trial.getCromossomos().set(i, target.getCromossomos().get(i));
            }
        }
    }

    private Double reparaValor(Double valor) {
        if (valor < this.minimo) {
            valor = this.minimo;
        } else if (valor > this.maximo) {
            valor = this.maximo;
        }
        return valor;
    }

}
