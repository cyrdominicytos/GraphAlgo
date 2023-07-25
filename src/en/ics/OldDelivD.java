package en.ics;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OldDelivD {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public OldDelivD(File in, Graph gr) {
		inputFile = in;
		graph = gr;

		// Set up for writing to a file
		try {
			// Use input file name to create output file in the same location
			String inputFileName = inputFile.toString();
			String outputFileName = inputFileName.substring(0, inputFileName.length() - 4).concat("_out.txt");
			outputFile = new File(outputFileName);

			// A Printwriter is an object that can write to a file
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		// Calls the method that will do the work of deliverable D
		run();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	
	private void runDelivD3() {
		// Get the list of nodes from the graph
		ArrayList<Node> nodeList = graph.getNodeList();
		for(Edge e : graph.getEdgeList()) {
			System.out.println(e.getTail().getAbbrev().concat("-").concat(e.getHead().getAbbrev().concat(e.getTail().getValue()).concat("/").concat(e.getHead().getValue())));
		}
	}
	
	
	private void run() {
		ArrayList<Node> bitonicList = new ArrayList<>();
		// Get the list of nodes from the graph
		ArrayList<Node> nodeList = graph.getNodeList();

		// Sort nodes by value in descending order
    	Collections.sort(nodeList, new Comparator<Node>() {	
			@Override
			public int compare(Node n1, Node n2) {
	                return n2.getValue().compareToIgnoreCase(n1.getValue());   
			}
    		
    	});
    	
    	List<Node> bitonicTour = shortestBitonicTour(nodeList.get(0), nodeList.get(nodeList.size()-1));
        double distance = calculateDistance(bitonicTour);

    	
    	/*Node currentNode = nodeList.get(0);
    	ArrayList<Edge> edgeQueue = new ArrayList<>();
    	// Add all outgoing edges of the start node to the queue
  	    edgeQueue.addAll(currentNode.getOutgoingEdges());
  	    int distance = 0;
    	while(!currentNode.equals(nodeList.get(nodeList.size()-1))) {
    		
    	}*/
        
        System.out.println("Distance du plus court chemin bitonique : " + distance);
        System.out.println("Ordre des villes dans le chemin : ");
        for (Node node : bitonicTour) {
            System.out.print(node.getAbbrev() + " -> ");
        }
    	
	}
	
	
	private void runDelivD() {
		
		// Get the list of nodes from the graph
		ArrayList<Node> nodeList = graph.getNodeList();

		// Sort nodes by value in descending order
    	Collections.sort(nodeList, new Comparator<Node>() {	
			@Override
			public int compare(Node n1, Node n2) {
	                return n2.getValue().compareToIgnoreCase(n1.getValue());   
			}
    		
    	});
		
		int n = nodeList.size();
		double[][] dp = new double[n + 1][n + 1];

		// Initialize the base case for the dynamic programming algorithm
		dp[1][2] = distance(nodeList.get(0), nodeList.get(1));

		// Fill the dp array
		for (int j = 3; j <= n; j++) {
			for (int i = 1; i < j - 1; i++) {
				dp[i][j] = dp[i][j - 1] + distance(nodeList.get(j - 2), nodeList.get(j - 1));
			}

			dp[j - 1][j] = Double.POSITIVE_INFINITY;

			for (int k = 1; k < j - 1; k++) {
				dp[j - 1][j] = Math.min(dp[j - 1][j], dp[k][j - 1] + distance(nodeList.get(k - 1), nodeList.get(j - 1)));
			}
		}
		
		// Calculate the shortest bitonic traveling-salesman tour length
		double shortestTourLength = dp[n - 1][n];

		// Write the result to the output file
		//output.println("Shortest bitonic tour length: " + shortestTourLength);
		System.out.println("Shortest bitonic tour length: " + shortestTourLength);
	}

    private int distance(Node node1, Node node2) {
      
    	for(Edge e :graph.getEdgeList()) {
    		if(e.getTail().equals(node1) && e.getHead().equals(node2))
    			return e.getDistance();
    		else if(e.getHead().equals(node1) && e.getTail().equals(node2))
    			return e.getDistance();
    	}
    	return 0;
    }

    
    
    
    //
    public List<Node> shortestBitonicTour(Node startNode, Node endNode) {
        List<Node> bitonicTour = new ArrayList<>();

        // Tri des n�uds en fonction de leur valeur (latitude ou longitude)
        // Sort nodes by value in descending order
    	Collections.sort(graph.getNodeList(), new Comparator<Node>() {	
			@Override
			public int compare(Node n1, Node n2) {
	                return n2.getValue().compareToIgnoreCase(n1.getValue());   
			}
    		
    	});

        // Recherche du chemin le plus court entre startNode et endNode
        List<Node> forwardPath = Dijkstra.shortestPath(graph, startNode, endNode);
        List<Node> reversePath = Dijkstra.shortestPath(graph, endNode, startNode);

        // Construction du chemin bitonique en combinant les chemins forward et reverse
        bitonicTour.addAll(forwardPath);
        reversePath.remove(0); // On enl�ve le premier n�ud car il sera pr�sent deux fois dans le chemin bitonique
        bitonicTour.addAll(reversePath);

        return bitonicTour;
    }
    
    public double calculateDistance(List<Node> bitonicTour) {
        double distance = 0;
        for (int i = 0; i < bitonicTour.size() - 1; i++) {
            Node current = bitonicTour.get(i);
            Node next = bitonicTour.get(i + 1);
            distance += distance(current, next);
        }
        return distance;
    }

}


