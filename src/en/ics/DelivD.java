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
		run();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	
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
            output.println(node.getAbbrev());
        }
    }

	    
	


	ArrayList<Node> nodeList = new ArrayList<>();
    ArrayList<Node> bitonicTour = new ArrayList<>();
	private void runDeliv() {
		nodeList = graph.getNodeList();
		System.out.println("runDeliv Called...");
		   // Get the list of nodes from the graph
		    

		    // Tri des n�uds en fonction de leur valeur (latitude ou longitude) dans l'ordre d�croissant
		    Collections.sort(nodeList, new Comparator<Node>() {
		        @Override
		        public int compare(Node n1, Node n2) {
		            return Double.compare(Double.parseDouble(n2.getValue()), Double.parseDouble(n1.getValue()));
		        }
		    });
		    
		    //Start at the city with the highest number
		    Node currentNode = nodeList.get(0);
		    //bitonicTour.add(currentNode);
		    int i =0;
		    while(currentNode!=null && !currentNode.equals(nodeList.get(nodeList.size()-1))) {
		    	/*if(currentNode.getAbbrev().equals("M")) {
		    		for (Edge e : currentNode.getOutgoingEdges()) {
				        System.out.print(e.getHead().getAbbrev() + " - ");
				    }
		    	}*/
		    	bitonicTour.add(currentNode);
		    	currentNode =  goToLowestNode(currentNode);
		    	//break;
		    	 //System.out.print(currentNode.getAbbrev() + " -> ");
		    	//bitonicTour.add(currentNode);
		    	
		    	
		    	
		    	/*if(currentNode.getAbbrev().equals("C")) {
		    		for (Edge e : currentNode.getOutgoingEdges()) {
				        System.out.print(e.getHead().getAbbrev() + " -> ");
				    }
		    		break;
		    	}*/
		    	
		    }
		    
		    
		    while(currentNode!=null && !currentNode.equals(nodeList.get(0))) {
		    	/*if(currentNode.getAbbrev().equals("M")) {
		    		for (Edge e : currentNode.getOutgoingEdges()) {
				        System.out.print(e.getHead().getAbbrev() + " - ");
				    }
		    	}*/
		    	bitonicTour.add(currentNode);
		    	currentNode =  goToHighestNode(currentNode);
		    	//break;
		    	 //System.out.print(currentNode.getAbbrev() + " -> ");
		    	
		    	
		    	
		    	
		    	/*if(currentNode.getAbbrev().equals("C")) {
		    		for (Edge e : currentNode.getOutgoingEdges()) {
				        System.out.print(e.getHead().getAbbrev() + " -> ");
				    }
		    		break;
		    	}*/
		    	
		    }
		    
		    
		   
		    for (Edge e : graph.getEdgeList()) {
		        System.out.print(e.getHead().getAbbrev() +e.getTail().getAbbrev() + e.getDistance()+" :: ");
		    }
		    
		    double distance = calculateDistance(bitonicTour);
		    
		    System.out.println("Distance du plus court chemin bitonique : " + distance);
		    System.out.println("Ordre des villes dans le chemin : ");
		    for (Node node : bitonicTour) {
		        System.out.print(node.getAbbrev() +node.getValue()+ " -> ");
		    }
	
	}
	
	private Node getNearestEstNode(Node node) {
			
		if(node.getOutgoingEdges().size()==0)
			return null; 
		
			Collections.sort(node.getOutgoingEdges(), new Comparator<Edge>() {
				@Override
				public int compare(Edge edge1, Edge edge2) {
					double val1 = Double.parseDouble(edge1.getHead().getValue());
					double val2 = Double.parseDouble(edge2.getHead().getValue());
					
					if(val2!=val1) {
						 return Double.compare(val2, val1);
					}else {
						return Integer.compare(edge1.getDistance(), edge2.getDistance());
					}
				}
		    });
			
			for(Edge e : node.getOutgoingEdges())
				if(!bitonicTour.contains(e.getHead()))
					return e.getHead();
			
			return null;
	}
	
	
	private Node goToLowestNode(Node node) {
		
		if(node.getOutgoingEdges().size()==0)
			return null; 
		
			Collections.sort(node.getIncomingEdges(), new Comparator<Edge>() {
				@Override
				public int compare(Edge edge1, Edge edge2) {
					double val1 = Double.parseDouble(edge1.getHead().getValue());
					double val2 = Double.parseDouble(edge2.getHead().getValue());
					//return Double.compare(val2, val1);
					
					/*if(edge1.getDistance()!= edge2.getDistance()) {
						return Integer.compare(edge1.getDistance(), edge2.getDistance());
					}else {
						 return Double.compare(val2, val1);
					}*/
					return Double.compare(val2, val1);
					/*if(val2!=val1) {
						return Double.compare(val2, val1);
					}else {
						return Integer.compare(edge1.getDistance(), edge2.getDistance());
					}*/
				}
		    });
			
			for(Edge e : node.getOutgoingEdges())
				if(!bitonicTour.contains(e.getHead()))
					return e.getHead();
			
			return null;
	}
	
	
private Node goToHighestNode(Node node) {
		
		if(node.getOutgoingEdges().size()==0)
			return null; 
		
			Collections.sort(node.getIncomingEdges(), new Comparator<Edge>() {
				@Override
				public int compare(Edge edge1, Edge edge2) {
					double val1 = Double.parseDouble(edge1.getHead().getValue());
					double val2 = Double.parseDouble(edge2.getHead().getValue());
					//return Double.compare(val1, val2);
					/*if(val2!=val1) {
						 return Double.compare(val1, val2);
					}else {
						return Integer.compare(edge1.getDistance(), edge2.getDistance());
					}*/
					
					if(edge1.getDistance()!= edge2.getDistance()) {
						return Integer.compare(edge1.getDistance(), edge2.getDistance());
					}else {
						 return Double.compare(val1, val2);
					}
				}
		    });
			
			for(Edge e : node.getOutgoingEdges())
				if(!bitonicTour.contains(e.getHead()))
					return e.getHead();
			
			return null;
	}
	
	
	
	private void runDelivD() {
	    // Get the list of nodes from the graph
	   /* ArrayList<Node> nodeList = graph.getNodeList();

	    // Tri des n�uds en fonction de leur valeur (latitude ou longitude) dans l'ordre d�croissant
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
    
    public List<Node> shortestBitonicTour3(Node startNode, Node endNode) {
    	 List<Node> bitonicTour = new ArrayList<>();

    	    // Recherche du chemin le plus court entre startNode et endNode en suivant une direction
    	    List<Node> forwardPath = Dijkstra.shortestPath(graph, startNode, endNode);

    	    // Recherche du chemin le plus court entre startNode et endNode en suivant l'autre direction
    	    List<Node> reversePath = Dijkstra.shortestPath(graph, endNode, startNode);

    	    // Si le chemin reversePath est plus court que le chemin forwardPath, on les �change
    	    if (calculateDistance(reversePath) < calculateDistance(forwardPath)) {
    	        List<Node> temp = forwardPath;
    	        forwardPath = reversePath;
    	        reversePath = temp;
    	    }

    	    // Construction du chemin bitonique en combinant les chemins forward et reverse
    	    bitonicTour.addAll(forwardPath);
    	    bitonicTour.remove(bitonicTour.size() - 1); // On enl�ve le dernier n�ud car il sera pr�sent deux fois dans le chemin bitonique
    	    bitonicTour.addAll(reversePath);

    	    return bitonicTour;
    }
    
    
    public List<Node> shortestBitonicTour(Node startNode, Node endNode) {
        List<Node> bitonicTour = new ArrayList<>();

        // Trouver le n�ud le plus � l'ouest et le plus � l'est
        Node westmostNode = getWestmostNode();
        Node eastmostNode = getEastmostNode();

        // Trouver le plus court chemin d'ouest en est (ou d'est en ouest)
        List<Node> shortestPath = Dijkstra.shortestPath(graph, westmostNode, eastmostNode);

        // Ajouter tous les n�uds restants qui n'appartiennent pas au chemin direct
        List<Node> remainingNodes = new ArrayList<>(graph.getNodeList());
        remainingNodes.removeAll(shortestPath);
        int indexOfEastmostNode = shortestPath.indexOf(eastmostNode);
        shortestPath.addAll(indexOfEastmostNode + 1, remainingNodes);

        // Trouver le plus court chemin du n�ud le plus � l'est au n�ud de d�part
        List<Node> returnPath = Dijkstra.shortestPath(graph, eastmostNode, startNode);

        // Construction du chemin bitonique complet
        bitonicTour.addAll(shortestPath);
        bitonicTour.addAll(returnPath);

        return bitonicTour;
    }

    // M�thode pour trouver le n�ud le plus � l'ouest (ou le plus au nord, selon le fichier)
    private Node getWestmostNode() {
        Node westmostNode = graph.getNodeList().get(0);
        for (Node node : graph.getNodeList()) {
            if (Double.parseDouble(node.getValue()) < Double.parseDouble(westmostNode.getValue())) {
                westmostNode = node;
            }
        }
        return westmostNode;
    }

    // M�thode pour trouver le n�ud le plus � l'est (ou le plus au sud, selon le fichier)
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

