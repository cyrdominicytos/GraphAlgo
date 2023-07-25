package en.ics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BitonicTour {

    private Graph graph;

    public BitonicTour(Graph graph) {
        this.graph = graph;
    }

    public List<Node> shortestBitonicTour() {
        List<Node> nodeList = graph.getNodeList();
        int n = nodeList.size();

        Collections.sort(nodeList, new Comparator<Node>() {
	        @Override
	        public int compare(Node n1, Node n2) {
	            return Double.compare(Double.parseDouble(n2.getValue()), Double.parseDouble(n1.getValue()));
	        }
	    });
       // Collections.sort(nodeList, Comparator.comparingDouble(node -> Double.parseDouble(node.getValue())));

        // b[i][j] represents the length of the shortest bitonic path ending at node j and starts at node 1
        double[][] b = new double[n][n];

        // r[i][j] represents the index of the immediate predecessor of node j on the shortest bitonic path Pij
        int[][] r = new int[n][n];

        // Calculate b[i][j] for all i <= j
        for (int j = 1; j < n; j++) {
            b[0][j] = distance(nodeList.get(0), nodeList.get(j));
            r[0][j] = 0;
            for (int i = 1; i < j - 1; i++) {
                b[i][j] = b[i][j - 1] + distance(nodeList.get(j - 1), nodeList.get(j));
                r[i][j] = j - 1;
            }
            b[j - 1][j] = Double.POSITIVE_INFINITY;
            for (int k = 0; k < j - 1; k++) {
                double value = b[k][j - 1] + distance(nodeList.get(k), nodeList.get(j));
                if (value < b[j - 1][j]) {
                    b[j - 1][j] = value;
                    r[j - 1][j] = k;
                }
            }
        }

        // Find the optimal bitonic tour length
        double tourLength = Double.POSITIVE_INFINITY;
        int tourEndIndex = -1;
        for (int j = 1; j < n; j++) {
            double value = b[j - 1][j] + distance(nodeList.get(j), nodeList.get(n - 1));
            if (value < tourLength) {
                tourLength = value;
                tourEndIndex = j;
            }
        }

        // Reconstruct the optimal bitonic tour
        List<Node> bitonicTour = new ArrayList<>();
        int currentIndex = tourEndIndex;
        while (currentIndex > 0) {
            bitonicTour.add(nodeList.get(currentIndex));
            currentIndex = r[currentIndex][tourEndIndex];
        }
        bitonicTour.add(nodeList.get(0));
        Collections.reverse(bitonicTour);

        return bitonicTour;
    }

    private double distance(Node node1, Node node2) {
    	for(Edge e :graph.getEdgeList()) {
    		if(e.getTail().equals(node1) && e.getHead().equals(node2))
    			return e.getDistance();
    		else if(e.getHead().equals(node1) && e.getTail().equals(node2))
    			return e.getDistance();
    	}
    	return 0.0;
    }

    // Other methods for the Graph class, Edge class, and Node class as provided in the specifications...
}