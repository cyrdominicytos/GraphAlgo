package en.ics;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class DelivC{

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivC(File in, Graph gr) {
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
		
		// Calls the method that will do the work of deliverable C
		runDelivC();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	int totalVisitedNode = 0;
	int totalCost = 0; // To store the total cost of the MST
	// Create a new graph to store the minimum spanning tree
    Graph mst = new Graph();
    ArrayList<Edge> edgeQueue = new ArrayList<>();
	private void runDelivC() {
		
	    // Find the starting node with value "S"
	    Node startNode = graph.getStartedNode("S");
	    
	    if (startNode == null) {
	        System.out.println("There is no node with value 'S' found.");
	        output.println("There is no node with value 'S' found.");
	        return;
	    }
	    
	    
	    // Add all outgoing edges of the start node to the queue
	    edgeQueue.addAll(startNode.getOutgoingEdges());
	    
	    // Add the start node to the visited set
	    startNode.setVisited(true);
	    totalVisitedNode++;
	    
	    
	    // Iterate until all nodes are visited or the edge queue is empty
	    while (!edgeQueue.isEmpty() && totalVisitedNode < graph.getNodeList().size()) {
	    	
	    	
	    	 // Sort edges by distance, breaking ties alphabetically
	    	Collections.sort(edgeQueue, new Comparator<Edge>() {
				@Override
				public int compare(Edge edge1, Edge edge2) {
					if (edge1.getDistance() != edge2.getDistance()) {
		                return Integer.compare(edge1.getDistance(), edge2.getDistance());
		            } else {
		                return edge1.getHead().getName().compareToIgnoreCase(edge2.getHead().getName());
		            }
				}
	    		
	    	});
	    	    
		 	
	        // Get the edge with the minimum weight from the queue
	        Edge minEdge = edgeQueue.remove(0);
	        
	        Node tail = minEdge.getTail();
	        Node head = minEdge.getHead();
	        
	        // Check if both nodes of the edge are already visited
	        if (tail.isVisited() && head.isVisited()) {
	            continue; // Skip this edge to avoid cycles
	        }
	        
	        // Add the edge to the MST
	        mst.addEdge(minEdge);
	        totalCost += minEdge.getDistance();
	        
	        // Add the non-visited node to the visited set
	        Node nextNode =tail.isVisited() ? head : tail;
	        nextNode.setVisited(true);
	      
	        // Add the outgoing edges of the next node to the queue
	        edgeQueue.addAll(nextNode.getOutgoingEdges());
	    }
	    
	    // Print the included edges and the total cost of the MST
	    System.out.println("The minimum spanning tree has a total cost of "+totalCost+" and includes the following edges:");
	    output.println("The minimum spanning tree has a total cost of "+totalCost+" and includes the following edges:");
	    for (Edge edge : mst.getEdgeList()) {
	        Node tail = edge.getTail();
	        Node head = edge.getHead();
	        System.out.println(head.getName() + "-" + tail.getName());
	        output.println(head.getName() + "-" + tail.getName());
	    }
	    
	}

}

