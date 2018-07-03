package gamaxclique;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Individual implements Comparable<Individual> {

    private int nChromosomes;
    private List<Integer> chromosomes;
    private Double fitnessFunction;

    public Individual(int nChromosomes) {
        this.nChromosomes = nChromosomes;
        this.fitnessFunction = .0;
        this.chromosomes = new LinkedList<>();
    }

    public void generateChromosomes() {
        this.chromosomes.clear();
        Random rnd = new Random();

        for(int i = 0; i < this.nChromosomes; i++) {
            int bit = rnd.nextInt(2);
            this.chromosomes.add(bit);
        }
    }

    public Double getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(Double fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public int getnChromosomes() {
        return nChromosomes;
    }

    public void setnChromosomes(int nChromosomes) {
        this.nChromosomes = nChromosomes;
    }

    public List<Integer> getChromosomes() {
        return this.chromosomes;
    }

    public int getChromosome(int index) {
        return this.chromosomes.get(index);
    }

    public void setChromosome(int index, int bit) {
        this.chromosomes.set(index, bit);
    }

    public int getSetBitCount() {
        int count = 0;
        for (Integer chromosome : chromosomes) {
            if (chromosome == 1)
                count++;
        }

        return count;
    }

    @Override
    public int compareTo(Individual o) {
        return o.getFitnessFunction().compareTo(this.getFitnessFunction());
    }

    @Override
    public String toString() {
        String output = "";

        for (int i =0; i< this.nChromosomes; i++) {
            output += this.chromosomes.get(i) + " ";
        }

        return output;
    }
}
