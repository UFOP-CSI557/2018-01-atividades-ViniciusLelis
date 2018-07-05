package esagschwefel;

import java.util.Collections;
import java.util.Random;

public class ESReal implements Metodo {

    // Parametros - problema - Schwefel
    private final Double minimo;
    private final Double maximo;
    private final Integer nVariaveis;
    private final Problema problema;

    // Parametros - ES
    private final Integer mu; // Tamanho da populacao
    private final Integer lambda; // numero de descendentes
    private final Integer geracoes; // criterio de parada
    private final Double pMutacao; // mutacao - aplicacao ao descendente - variacao/perturbacao
    
    // Parametros - AG
    private final Double pCrossover;
    
    private Individuo melhorIndividuo;
    private Individuo piorIndividuo;

    public ESReal(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao, Double pCrossover) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.problema = problema;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
        this.pCrossover = pCrossover;
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
            
            // Fazer crossover entre os indivíduos
            /************ AG ************/
            Random rnd = new Random();
            int ind1, ind2;
            for (int i = 0; i < populacao.getIndividuos().size(); i++) {
                if (rnd.nextDouble() <= this.pCrossover) {
                    // Realizar a operação
                    ind1 = rnd.nextInt(this.mu);
                    do {
                        ind2 = rnd.nextInt(this.mu);
                    } while (ind1 == ind2);
                    Individuo desc1 = new IndividuoDouble(minimo, maximo, nVariaveis);
                    Individuo desc2 = new IndividuoDouble(minimo, maximo, nVariaveis);
                    // Progenitores
                    Individuo p1 = populacao.getIndividuos().get(ind1);
                    Individuo p2 = populacao.getIndividuos().get(ind2);
                    // Ponto de corte
                    int corte = rnd.nextInt(p1.getCromossomos().size());
                    // Descendente 1 -> Ind1_1 + Ind2_2;
                    crossoverUmPonto(p1, p2, desc1, corte);
                    // Descendente 2 -> Ind2_1 + Ind1_2;
                    crossoverUmPonto(p2, p1, desc2, corte);
                    // Avaliar as novas soluções
                    problema.calcularFuncaoObjetivo(desc1);
                    problema.calcularFuncaoObjetivo(desc2);
                    // Inserir na nova população
                    descendentes.getIndividuos().add(desc1);
                    descendentes.getIndividuos().add(desc2);
                }
            }
            populacao.getIndividuos().addAll(descendentes.getIndividuos());
            //Collections.sort(populacao.getIndividuos());
            //populacao.getIndividuos().subList(this.mu, populacao.getIndividuos().size()).clear();
            descendentes.getIndividuos().clear();
            
            // Para cada individuo, gerar lambda/mu descendentes
            /************ ES ************/
            for (int i = 0; i < populacao.getIndividuos().size(); i++) {
                // Gerar lambda/mu descendentes
                for (int d = 0; d < lambda/mu; d++) {
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
            populacao.getIndividuos().addAll(descendentes.getIndividuos());
            Collections.sort(populacao.getIndividuos());
            populacao.getIndividuos().subList(this.mu, populacao.getIndividuos().size()).clear();
            descendentes.getIndividuos().clear();
            
            System.out.println("Gen = " +g+ "\tCusto = " + populacao.getIndividuos().get(0).getFuncaoObjetivo());
        }
        melhorIndividuo = populacao.getMelhorIndividuo();
        piorIndividuo = populacao.getPiorIndividuo();
        return populacao.getMelhorIndividuo();
    }

    private void crossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte) {
        // Crossover aritmetico - 1 ponto de corte
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();
        // Ind1_1
        // alpha * P1
        for (int i = 0; i < corte; i++) {
            Double valor = alpha * ((Double)ind1.getCromossomos().get(i));
            descendente.getCromossomos().add(valor);
        }
        // Ind2_2
        // (1 - alpha) * P2
        for (int i = corte; i < this.nVariaveis; i++) {
            Double valor = (1.0 - alpha) * ((Double)ind2.getCromossomos().get(i));
            descendente.getCromossomos().add(valor);
        }
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
                if (valor >= this.minimo && valor <= this.maximo) {
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
