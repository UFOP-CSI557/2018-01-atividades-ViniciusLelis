package esrastrigin;

import java.util.Collections;
import java.util.Random;

public class ESReal implements Metodo {

    // Parametros - problema - DeJong
    private Double minimo;
    private Double maximo;
    private Integer nVariaveis;
    private Problema problema;

    // Parametros - ES
    private Integer mu; // Tamanho da populacao
    private Integer lambda; // numero de descendentes
    private Integer geracoes; // criterio de parada
    private Double pMutacao; // mutacao - aplicacao ao descendente - variacao/perturbacao
    
    private Individuo melhorIndividuo;
    private Individuo piorIndividuo;

    public ESReal(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.problema = problema;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
    }

    @Override
    public Individuo executar() {

        // Geracao da populacao inicial - aleatoria - tamanho mu
        PopulacaoDouble populacao = new PopulacaoDouble(problema, minimo, maximo, nVariaveis, mu);
        populacao.criar();

        // Avaliar
        populacao.avaliar();

        // Populacao - descendentes
        PopulacaoDouble descendentes = new PopulacaoDouble();

        // Criterio de parada - numero de geracoes
        for (int g = 1; g <= this.geracoes; g++) {

            // Para cada individuo, gerar lambda/mu descendentes
            for (int i = 0; i < populacao.getIndividuos().size(); i++) {

                // Gerar lambda/mu descendentes
                for (int d = 0; d < lambda / mu; d++) {

                    // Clonar individuo
                    IndividuoDouble filho = (IndividuoDouble) populacao.getIndividuos().get(i).clone();

                    // Aplicar mutacao
                    mutacaoPorVariavel(filho);

                    // Avaliar
                    problema.calcularFuncaoObjetivo(filho);

                    // Inserir na populacao de descendentes
                    descendentes.getIndividuos().add(filho);

                }

            }

            // ES(mu, lambda)?
            // Eliminar a populacao corrente
            //populacao.getIndividuos().clear();
            // ES(mu + lambda)?
            // Mu + Lambda
            populacao.getIndividuos().addAll(descendentes.getIndividuos());
            // Ordenar Mu+Lambda
            Collections.sort(populacao.getIndividuos());
            // Definir sobreviventes
            populacao.getIndividuos()
                    .subList(this.mu, populacao.getIndividuos().size()).clear();
            // Limpar descendentes
            descendentes.getIndividuos().clear();

            System.out.println("G = " + g
                    + "\t"
                    + populacao.getMelhorIndividuo().getFuncaoObjetivo());
        }
        // Retornar o melhor individuo
        melhorIndividuo = populacao.getMelhorIndividuo();
        piorIndividuo = populacao.getPiorIndividuo();
        
        return populacao.getMelhorIndividuo();
    }

    private void mutacaoPorVariavel(Individuo individuo) {

        Random rnd = new Random();

        for (int i = 0; i < individuo.getCromossomos().size(); i++) {
            if (rnd.nextDouble() <= this.pMutacao) {

                // Mutacao aritmetica
                // Multiplicar rnd e inverter ou nao o sinal
                Double valor = (Double) individuo.getCromossomos().get(i);
                // Multiplica por rnd
                valor *= rnd.nextDouble();

                // Inverter o sinal
                if (!rnd.nextBoolean()) {
                    valor = -valor;
                }

                if (valor >= this.minimo
                        && valor <= this.maximo) {
                    individuo.getCromossomos().set(i, valor);

                }

            }
        }

    }

    public Individuo getMelhorIndividuo() {
        return melhorIndividuo;
    }

    public void setMelhorIndividuo(Individuo melhorIndividuo) {
        this.melhorIndividuo = melhorIndividuo;
    }

    public Individuo getPiorIndividuo() {
        return piorIndividuo;
    }

    public void setPiorIndividuo(Individuo piorIndividuo) {
        this.piorIndividuo = piorIndividuo;
    }

}
