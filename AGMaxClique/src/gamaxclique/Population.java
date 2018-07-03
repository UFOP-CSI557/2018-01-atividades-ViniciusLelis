package gamaxclique;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Population {

    private int nIndividuals;
    private int nChromosomes;
    private List<Individual> individualList;

    public Population(int nIndividuals, int nChromosomes) {
        this.nIndividuals = nIndividuals;
        this.nChromosomes = nChromosomes;
        this.individualList = new LinkedList<>();
    }

    public void generatePopulation() {
        this.individualList.clear();

        for(int i=0; i<nIndividuals; i++) {
            Individual ind = new Individual(nChromosomes);
            ind.generateChromosomes();
            this.individualList.add(ind);
        }
    }

    public void evaluate() {
        Graph graph = Graph.getInstance();
        for(Individual ind : individualList)
            Problem.calculateFitnessFunction(ind, graph, 50);
    }

    public void addIndividual(Individual individual) {
        this.individualList.add(individual);
    }

    public Individual getIndividual(int index) {
        return this.individualList.get(index);
    }

    public List<Individual> getIndividualList() {
        return this.individualList;
    }

    public Individual getMelhorIndividuo() {
        return this.individualList.get(0);
    }

}
