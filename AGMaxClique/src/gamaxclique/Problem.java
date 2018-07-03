package gamaxclique;

public class Problem {

    public static void calculateFitnessFunction(Individual individual, Graph graph, double weightNonExistentEdges) {
        int sumEdges = 0;
        int sumNonExistentEdges = 0;

        for(int i=0; i<graph.getEdgeList().size();i++) {
            Edge edge = graph.getEdge(i);
            int firstVertex = edge.getFirstVertex();
            int secondVertex = edge.getSecondVertex();
            sumEdges += individual.getChromosome(firstVertex)*individual.getChromosome(secondVertex);
        }

        for(int i=0; i<graph.getNonExistentEdgeList().size();i++) {
            Edge edge = graph.getNonExistentEdge(i);
            int firstVertex = edge.getFirstVertex();
            int secondVertex = edge.getSecondVertex();
            sumNonExistentEdges += individual.getChromosome(firstVertex)*individual.getChromosome(secondVertex);
        }

        Double fitnessFunctionTotal = sumEdges - (weightNonExistentEdges*sumNonExistentEdges);
        individual.setFitnessFunction(fitnessFunctionTotal);
    }

}
