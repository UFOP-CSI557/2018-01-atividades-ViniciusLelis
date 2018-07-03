package gamaxclique;

import java.util.LinkedList;
import java.util.List;

public class Graph {

    private static Graph instance;
    private int nVertices;
    private List<Edge> edgeList;
    private List<Edge> nonExistentEdgeList;

    private int[][] adjacencyMatrix;

    private Graph() {
        this.edgeList = new LinkedList<>();
        this.nonExistentEdgeList = new LinkedList<>();
    }

    public static Graph getInstance() {
        if (instance == null)
            instance = new Graph();

        return instance;
    }

    public void addEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            this.adjacencyMatrix[edge.getFirstVertex()][edge.getSecondVertex()] = 1;
            this.adjacencyMatrix[edge.getSecondVertex()][edge.getFirstVertex()] = 1;
        }

        this.edgeList.addAll(edges);
        buildNonExistentEdges();
    }

    private void buildNonExistentEdges() {
        this.nonExistentEdgeList.clear();
        for(int i=0; i<nVertices; i++) {
            for(int j=0; j<nVertices; j++) {
                if (i != j && this.adjacencyMatrix[i][j] == 0)
                    this.nonExistentEdgeList.add(new Edge(i, j));
            }
        }
    }

    public int getnVertices() {
        return nVertices;
    }

    public void setnVertices(int nVertices) {
        this.nVertices = nVertices;
        this.adjacencyMatrix = new int[nVertices][nVertices];
    }

    public Edge getEdge(int index) {
        return this.edgeList.get(index);
    }

    public Edge getNonExistentEdge(int index) {
        return this.nonExistentEdgeList.get(index);
    }

    public List<Edge> getEdgeList() {
        return this.edgeList;
    }

    public List<Edge> getNonExistentEdgeList() {
        return this.nonExistentEdgeList;
    }
}
