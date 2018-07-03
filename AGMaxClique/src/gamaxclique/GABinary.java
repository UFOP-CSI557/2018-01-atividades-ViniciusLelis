package gamaxclique;

import java.util.Collections;
import java.util.Random;

public class GABinary {

    private int nGenerations;
    private int nIndividuals;
    private int nChromosomes;
    private double pCrossover;
    private double pMutation;
    private int repetitionTolerance;

    private Individual bestSolution;

    public GABinary(int nGenerations, int nIndividuals, int nChromosomes, double pCrossover, double pMutation, int repetitionTolerance) {
        this.nGenerations = nGenerations;
        this.nIndividuals = nIndividuals;
        this.nChromosomes = nChromosomes;
        this.pCrossover = pCrossover;
        this.pMutation = pMutation;
        this.repetitionTolerance = repetitionTolerance;
    }

    public Individual getBestSolution() {
        return bestSolution;
    }

    public Double executar() {
        Population currentPopulation = new Population(nIndividuals, nChromosomes);
        Population nextPopulation = new Population(nIndividuals, nChromosomes);
        currentPopulation.generatePopulation();
        currentPopulation.evaluate();

        double currentBestSolution = currentPopulation.getMelhorIndividuo().getFitnessFunction();
        int repetitionCount = 0;

        Random rnd = new Random();
        int firstParentIndex, secondParentIndex;

        for (int g = 1; (g <= nGenerations) && (repetitionCount < repetitionTolerance); g++) {
            for (int i = 0; i < this.nIndividuals; i++) {
                if (rnd.nextDouble() <= this.pCrossover) {
                    firstParentIndex = rnd.nextInt(this.nIndividuals);

                    do {
                        secondParentIndex = rnd.nextInt(this.nIndividuals);
                    } while (firstParentIndex == secondParentIndex);

                    Individual firstDescendant  = new Individual(nChromosomes);
                    Individual secondDescendant  = new Individual(nChromosomes);

                    Individual firstParent = currentPopulation.getIndividual(firstParentIndex);
                    Individual secondParent = currentPopulation.getIndividual(secondParentIndex);

                    int cut = rnd.nextInt(this.nChromosomes);

                    onePointCrossover(firstParent, secondParent, firstDescendant, cut);
                    onePointCrossover(secondParent, firstParent, secondDescendant, cut);

                    bitMutation(firstDescendant);
                    bitMutation(secondDescendant);

                    Graph graph = Graph.getInstance();

                    Problem.calculateFitnessFunction(firstDescendant, graph, 50);
                    Problem.calculateFitnessFunction(secondDescendant, graph, 50
                    );

                    nextPopulation.addIndividual(firstDescendant);
                    nextPopulation.addIndividual(secondDescendant);
                }
            }

            currentPopulation.getIndividualList().addAll(nextPopulation.getIndividualList());
            Collections.sort(currentPopulation.getIndividualList());
            currentPopulation.getIndividualList().subList(this.nIndividuals, currentPopulation.getIndividualList().size()).clear();
            nextPopulation.getIndividualList().clear();

            if (currentPopulation.getMelhorIndividuo().getFitnessFunction() == currentBestSolution)
                repetitionCount++;
            else {
                currentBestSolution = currentPopulation.getMelhorIndividuo().getFitnessFunction();
                repetitionCount = 0;
            }

            System.out.println("G: " + g + "\tFitness value: " + currentPopulation.getMelhorIndividuo().getFitnessFunction());
        }

        this.bestSolution = currentPopulation.getMelhorIndividuo();

        return currentPopulation.getMelhorIndividuo().getFitnessFunction();
    }

    private void onePointCrossover(Individual firstParent, Individual secondParent, Individual descendant, int cut) {
        descendant.getChromosomes().addAll(firstParent.getChromosomes().subList(0, cut));
        descendant.getChromosomes().addAll(secondParent.getChromosomes().subList(cut, secondParent.getChromosomes().size()));
    }

    private void bitMutation(Individual individual) {
        Random rnd = new Random();

        for (int i = 0; i < individual.getnChromosomes(); i++) {
            if (rnd.nextDouble() <= this.pMutation) {
                int bit = individual.getChromosome(i);
                if (bit == 0)
                    bit = 1;
                else
                    bit = 0;

                individual.setChromosome(i, bit);
            }
        }

    }

}