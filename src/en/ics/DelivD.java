package en.ics;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DelivD {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivD(File in, Graph gr) {
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
		runDelivD();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	
	private void runDelivD() {
	    // Get the list of nodes from the graph
	   /* ArrayList<Node> nodeList = graph.getNodeList();

	    // Tri des nœuds en fonction de leur valeur (latitude ou longitude) dans l'ordre décroissant
	    Collections.sort(nodeList, new Comparator<Node>() {
	        @Override
	        public int compare(Node n1, Node n2) {
	            return Double.compare(Double.parseDouble(n2.getValue()), Double.parseDouble(n1.getValue()));
	        }
	    });
*/
	    BitonicTour btn = new BitonicTour(graph);
	   // List<Node> bitonicTour = shortestBitonicTour(nodeList.get(0), nodeList.get(nodeList.size() - 1));
	    
	    List<Node> bitonicTour = btn.shortestBitonicTour();
	    double distance = calculateDistance(bitonicTour);
	    
	    

		System.out.println("Distance du plus court chemin bitonique : " + distance);
	    System.out.println("Ordre des villes dans le chemin : ");
	    for (Node node : bitonicTour) {
	        System.out.print(node.getAbbrev() + " -> ");
	    }
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
    public List<Node> shortestBitonicTour2(Node startNode, Node endNode) {
        List<Node> bitonicTour = new ArrayList<>();

        // Tri des nœuds en fonction de leur valeur (latitude ou longitude)
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
        reversePath.remove(0); // On enlève le premier nœud car il sera présent deux fois dans le chemin bitonique
        bitonicTour.addAll(reversePath);

        return bitonicTour;
    }
    
    public List<Node> shortestBitonicTour3(Node startNode, Node endNode) {
    	 List<Node> bitonicTour = new ArrayList<>();

    	    // Recherche du chemin le plus court entre startNode et endNode en suivant une direction
    	    List<Node> forwardPath = Dijkstra.shortestPath(graph, startNode, endNode);

    	    // Recherche du chemin le plus court entre startNode et endNode en suivant l'autre direction
    	    List<Node> reversePath = Dijkstra.shortestPath(graph, endNode, startNode);

    	    // Si le chemin reversePath est plus court que le chemin forwardPath, on les échange
    	    if (calculateDistance(reversePath) < calculateDistance(forwardPath)) {
    	        List<Node> temp = forwardPath;
    	        forwardPath = reversePath;
    	        reversePath = temp;
    	    }

    	    // Construction du chemin bitonique en combinant les chemins forward et reverse
    	    bitonicTour.addAll(forwardPath);
    	    bitonicTour.remove(bitonicTour.size() - 1); // On enlève le dernier nœud car il sera présent deux fois dans le chemin bitonique
    	    bitonicTour.addAll(reversePath);

    	    return bitonicTour;
    }
    
    
    public List<Node> shortestBitonicTour(Node startNode, Node endNode) {
        List<Node> bitonicTour = new ArrayList<>();

        // Trouver le nœud le plus à l'ouest et le plus à l'est
        Node westmostNode = getWestmostNode();
        Node eastmostNode = getEastmostNode();

        // Trouver le plus court chemin d'ouest en est (ou d'est en ouest)
        List<Node> shortestPath = Dijkstra.shortestPath(graph, westmostNode, eastmostNode);

        // Ajouter tous les nœuds restants qui n'appartiennent pas au chemin direct
        List<Node> remainingNodes = new ArrayList<>(graph.getNodeList());
        remainingNodes.removeAll(shortestPath);
        int indexOfEastmostNode = shortestPath.indexOf(eastmostNode);
        shortestPath.addAll(indexOfEastmostNode + 1, remainingNodes);

        // Trouver le plus court chemin du nœud le plus à l'est au nœud de départ
        List<Node> returnPath = Dijkstra.shortestPath(graph, eastmostNode, startNode);

        // Construction du chemin bitonique complet
        bitonicTour.addAll(shortestPath);
        bitonicTour.addAll(returnPath);

        return bitonicTour;
    }

    // Méthode pour trouver le nœud le plus à l'ouest (ou le plus au nord, selon le fichier)
    private Node getWestmostNode() {
        Node westmostNode = graph.getNodeList().get(0);
        for (Node node : graph.getNodeList()) {
            if (Double.parseDouble(node.getValue()) < Double.parseDouble(westmostNode.getValue())) {
                westmostNode = node;
            }
        }
        return westmostNode;
    }

    // Méthode pour trouver le nœud le plus à l'est (ou le plus au sud, selon le fichier)
    private Node getEastmostNode() {
        Node eastmostNode = graph.getNodeList().get(0);
        for (Node node : graph.getNodeList()) {
            if (Double.parseDouble(node.getValue()) > Double.parseDouble(eastmostNode.getValue())) {
                eastmostNode = node;
            }
        }
        return eastmostNode;
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

