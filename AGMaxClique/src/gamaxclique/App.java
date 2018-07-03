package gamaxclique;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        if(args.length > 0)
            buildGraph(args[0]);
        else {
            Scanner input = new Scanner(System.in);
            System.out.print("Please input the graph file path: ");
            String fileName = input.nextLine();
            buildGraph(fileName);
        }

        Graph graph = Graph.getInstance();

        int nGenerations = (graph.getnVertices() + (graph.getnVertices()/2))*5;
        int nIndividuals = graph.getnVertices()*5;
        int repetitionTolerance = nGenerations/2;
        double pCrossover = 0.7;
        double pMutation = 0.4;

        System.out.print("\n\nSTART OF THE GENETIC ALGORITHM\n\n");

        GABinary gaBinary = new GABinary(nGenerations, nIndividuals, graph.getnVertices(), pCrossover, pMutation, repetitionTolerance);
        gaBinary.executar();

        Individual individual = gaBinary.getBestSolution();
        System.out.println("\nMax Clique bit representation: " +individual);
        System.out.println("Max Clique size: " +individual.getSetBitCount());
    }

    private static void buildGraph(String filePath) {
        int nVertices = 0;
        Graph graph = Graph.getInstance();
        List<Edge> edges = new LinkedList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Graph file reading: ");
            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                if (line.charAt(0) != 'c') {
                    String[] elements = line.split(" ");

                    if (elements[0].equals("e") && elements.length == 3)
                        edges.add(new Edge(Integer.parseInt(elements[1]) - 1, Integer.parseInt(elements[2]) - 1));
                    else if (elements[0].equals("p") && elements.length == 4)
                        nVertices = Integer.parseInt(elements[2]);
                    else
                        throw new NumberFormatException();
                }
                line = br.readLine();
            }

            graph.setnVertices(nVertices);
            graph.addEdges(edges);
        } catch (FileNotFoundException e) {
            System.out.println("File not found \"" +filePath+ "\"");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Error reading file \"" +filePath+ "\"");
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.out.println("File is in the wrong format \"" +filePath+ "\"");
            System.exit(-1);
        }
    }
}
