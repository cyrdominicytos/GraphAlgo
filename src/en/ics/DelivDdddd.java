package en.ics;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DelivDdddd {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivDdddd(File in, Graph gr) {
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
		//runDeliv();
		runDeliv();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	
	ArrayList<Node> nodeList = new ArrayList<>();
    ArrayList<Node> bitonicTour = new ArrayList<>();
	
 // ... (existing code)

 // Modify the runDeliv() method as follows:
 private void runDeliv() {
     nodeList = graph.getNodeList();
     List<Node> decreasingPhase = new ArrayList<>();
     List<Node> increasingPhase = new ArrayList<>();

     // Sort the nodes in descending order
     Collections.sort(nodeList, new Comparator<Node>() {
         @Override
         public int compare(Node n1, Node n2) {
             return Double.compare(Double.parseDouble(n2.getValue()), Double.parseDouble(n1.getValue()));
         }
     });

     // Start at the city with the highest number
     Node currentNode = nodeList.get(0);

     // Phase 1: Decreasing values
     while (currentNode != null && !currentNode.equals(nodeList.get(nodeList.size() - 1))) {
         decreasingPhase.add(currentNode);
         currentNode.setVisited(true);
         currentNode = goToLowestNode(currentNode);
     }

     // Phase 2: Increasing values
     currentNode = nodeList.get(0); // Start again from the highest value node
     while (currentNode != null && !currentNode.equals(decreasingPhase.get(decreasingPhase.size() - 1))) {
         increasingPhase.add(currentNode);
         currentNode.setVisited(true);
         currentNode = goToHighestNode(currentNode);
     }

     // Combine both phases to get the complete bitonic tour
     List<Node> bitonicTour = new ArrayList<>(decreasingPhase);
     bitonicTour.addAll(increasingPhase);

     double distance = calculateDistance(bitonicTour);

     System.out.println("Distance du plus court chemin bitonique : " + distance);
     System.out.println("Ordre des villes dans le chemin : ");
     for (Node node : bitonicTour) {
         System.out.print(node.getAbbrev() + node.getValue() + " -> ");
     }
 }

 // ... (existing code)

 // Modify the goToLowestNode() and goToHighestNode() methods as follows:

 private Node goToLowestNode(Node node) {
     if (node.getOutgoingEdges().isEmpty()) {
         return null;
     }

     Collections.sort(node.getOutgoingEdges(), new Comparator<Edge>() {
         @Override
         public int compare(Edge edge1, Edge edge2) {
             double val1 = Double.parseDouble(edge1.getHead().getValue());
             double val2 = Double.parseDouble(edge2.getHead().getValue());
             return Double.compare(val1, val2);
         }
     });

     for (Edge e : node.getOutgoingEdges()) {
         if (!e.getHead().isVisited()) {
             return e.getHead();
         }
     }

     return null;
 }

 private Node goToHighestNode(Node node) {
     if (node.getOutgoingEdges().isEmpty()) {
         return null;
     }

     Collections.sort(node.getIncomingEdges(), new Comparator<Edge>() {
         @Override
         public int compare(Edge edge1, Edge edge2) {
             double val1 = Double.parseDouble(edge1.getHead().getValue());
             double val2 = Double.parseDouble(edge2.getHead().getValue());

             if (edge1.getDistance() != edge2.getDistance()) {
                 return Integer.compare(edge1.getDistance(), edge2.getDistance());
             } else {
                 return Double.compare(val1, val2);
             }
         }
     });

     for (Edge e : node.getIncomingEdges()) {
         if (!e.getHead().isVisited()) {
             return e.getHead();
         }
     }

     return null;
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
	
	 public double calculateDistance(List<Node> bitonicTour) {
        double distance = 0;
        for (int i = 0; i < bitonicTour.size() - 1; i++) {
            Node current = bitonicTour.get(i);
            Node next = bitonicTour.get(i + 1);
            distance += distance(current, next);
        }
        return distance;
    }

   
    private Node getRightMostNodeInTop(List<Edge> edges) {
        return null;
    }


    private Node getRightMostNodeInBottom(List<Edge> edges) {
        return null;
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 

	 /*
	  *   for (Edge e : graph.getEdgeList()) {
		        System.out.print(e.getHead().getAbbrev() +e.getTail().getAbbrev() +"("+e.getDistance()+") ");
		    }
		    System.out.println("\n");
		    
		    for (Node node : nodeList) {
		        System.out.print(node.getAbbrev() +":"+node.getValue()+ " ; ");
		    }
	  */
    
    
    
    
    
    
    
    
    
    
    
	
	private void runx() {
	    // First, sort the nodes by their values in descending order (by latitude or longitude)
	    List<Node> sortedNodes = new ArrayList<>(graph.getNodeList());
	    sortedNodes.sort(Comparator.comparingDouble(node -> Double.parseDouble(node.getValue())));
	    Collections.reverse(sortedNodes);

	    int n = sortedNodes.size();
	    int[][] dp = new int[n][n];
	    int[][] prevNode = new int[n][n];
	    
	    for (int i = 0; i < n; i++) {
	        Arrays.fill(prevNode[i], -1);
	    }

	    // Base case: distance from node 0 to all other nodes
	    for (int i = 1; i < n; i++) {
	        dp[0][i] = graph.getEdgeList().get(i - 1).getDistance();
	    }

	    // Calculate the optimal distances
	    for (int i = 1; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            dp[i][j] = dp[i][j - 1] + graph.getEdgeList().get(j - 1).getDistance();
	        }
	        for (int k = 0; k < i; k++) {
	            int cost = dp[k][i] + graph.getEdgeList().get(i - 1).getDistance();
	            if (cost < dp[i][i]) {
	                dp[i][i] = cost;
	                prevNode[i][i] = k;
	            }
	        }
	    }

	    // Find the optimal solution for the entire tour
	    int minTourCost = dp[n - 1][n - 1] + graph.getEdgeList().get(n - 1).getDistance();
	    int endPoint = n - 1;
	    for (int i = 0; i < n - 1; i++) {
	        int tourCost = dp[i][n - 1] + graph.getEdgeList().get(n - 1).getDistance();
	        if (tourCost < minTourCost) {
	            minTourCost = tourCost;
	            endPoint = i;
	        }
	    }

	    // Reconstruct the bitonic tour path
	    List<Node> tourPath = new ArrayList<>();
	    int i = endPoint;
	    while (i >= 0) {
	        tourPath.add(sortedNodes.get(i));
	        i = prevNode[i][n - 1];
	    }
	    for (i = endPoint + 1; i < n; i++) {
	        tourPath.add(sortedNodes.get(i));
	    }

	    // Print the optimal bitonic tour distance and the order of cities in the tour
	    System.out.println("Shortest bitonic tour distance: " + minTourCost);
	    System.out.print("Order of cities in the tour: ");
	    for (i = 0; i < tourPath.size(); i++) {
	        output.print(tourPath.get(i).getAbbrev());
	        if (i < tourPath.size() - 1) {
	            output.print(" -> ");
	        }
	    }
	    System.out.println();
	}


	
	private void run() {
        // Récupérer la liste des nœuds triés par valeur (nombre à virgule flottante)
        List<Node> sortedNodes = new ArrayList<>(graph.getNodeList());
        sortedNodes.sort(Comparator.comparingDouble(node -> Double.parseDouble(node.getValue())));

        // Trouver le nœud de départ (avec le plus grand nombre) et initialiser la variable de circuit
        Node startNode = sortedNodes.get(sortedNodes.size() - 1);
        List<Node> circuit = new ArrayList<>();
        circuit.add(startNode);
        startNode.setVisited(true);

        // Trouver le nœud de valeur la plus basse (au milieu du circuit)
        Node minNode = sortedNodes.get(sortedNodes.size() / 2);
        circuit.add(minNode);
        minNode.setVisited(true);

        // Remplir le circuit en allant vers les nœuds de valeurs décroissantes (à gauche du nœud de valeur la plus basse)
        for (int i = sortedNodes.size() / 2 - 1; i >= 0; i--) {
            Node node = sortedNodes.get(i);
            if (!node.isVisited()) {
                circuit.add(node);
                node.setVisited(true);
            }
        }

        // Remplir le circuit en revenant aux nœuds de valeurs croissantes (à droite du nœud de valeur la plus basse)
        for (int i = sortedNodes.size() / 2 + 1; i < sortedNodes.size(); i++) {
            Node node = sortedNodes.get(i);
            if (!node.isVisited()) {
                circuit.add(node);
                node.setVisited(true);
            }
        }

        // Afficher le circuit obtenu
        for (Node node : circuit) {
            System.out.println(node.getAbbrev());
        }
    }

	

}

