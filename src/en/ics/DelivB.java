package en.ics;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DelivB {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivB(File in, Graph gr) {
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
		
		// Calls the method that will do the work of deliverable B
		runDelivB();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	int time = 0;
	private void runDelivB() {
			//Retrieve and visit the node that has the value S
		 	Node startNode = getStartedNode();
	        dfsVisit(startNode);
	        
	        ArrayList<Node> nodes = graph.getNodeList();
	        // Sort nodes alphabetically by name
	        nodes.sort((node1, node2) -> {
	                return node1.getName().compareToIgnoreCase(node2.getName());
	        });
	        //visit all remaining nodes
	        for (Node node : nodes) {
	            if(!node.isVisited()) {
	                dfsVisit(node);
	            }
	        }
	        
        //Detect Forward, CROSS and BACK edge
        for (Edge edge : graph.getEdgeList()) {
        	if(!edge.getClassification().equals("Tree"))
        		edge.setClassification(classifyEdge(edge));
        }
        
        // Print timestamps and edge classifications
        
        //Print nodes timestamps  into the file
        output.println("DFS of graph:\n");//Prints to file
        output.println("Node\t Disc\t Finish");//Prints to file
        for (Node node : graph.getNodeList()) {
        	output.println(node.getAbbrev() + "\t" + node.getDiscoveryTime()+"\t"+node.getFinishTime());//Prints to file
        }
        
        //Print edges classification into the file
        output.println("\nEdge Classification:\n");//Prints to file
        output.println("Edge\t Type");//Prints to file
        for (Edge edge : graph.getEdgeList()) {
        	output.println(edge.getTail().getAbbrev() + "->" + edge.getHead().getAbbrev()+"\t"+edge.getClassification());//Prints to file   
        }
	}
	
	
	/**
	 * Function to visit a node
	 * @param node
	 */
	private void dfsVisit(Node node) {
		if(node!=null && !node.isVisited()) {  
	        time++;
	        node.setVisited(true); // set the node as visited
	        node.setDiscoveryTime(time);
	
	        ArrayList<Edge> edges = node.getOutgoingEdges();
	        // Sort edges by distance, breaking ties alphabetically
	        edges.sort((edge1, edge2) -> {
	        	if (edge1.getDistance() != edge2.getDistance()) {
	                return Integer.compare(edge1.getDistance(), edge2.getDistance());
	            } else {
	                return edge1.getHead().getName().compareToIgnoreCase(edge2.getHead().getName());
	            }
	        });
	        
	        for (Edge edge : edges) {
	            Node adjacentNode = edge.getHead();
	            if (!adjacentNode.isVisited())
	            {
	                edge.setClassification("Tree");
	                dfsVisit(adjacentNode);
	            }
	        }
           time++;
           node.setFinishTime(time);
		}
    }

	/**
	 * Get the node that has the value S
	 * @return the related node
	 */
	private Node getStartedNode() {
		 for(Node node : graph.getNodeList()) {
	            if (node.getValue()!=null && node.getValue().equals("S")) {
	                return node;
	            }
	        }
		 return null;
	}
	
	/**
	 * Function to classify an edge base on it's nodes values
	 * @param edge
	 * @return the classification type of the edge
	 */
	private String classifyEdge(Edge edge) {
	    Node u = edge.getTail();
	    Node v = edge.getHead();

	    if (u.getDiscoveryTime() < v.getDiscoveryTime() && u.getFinishTime() > v.getFinishTime()) {
	        // Tree or forward edge
	        if (!v.isVisited()) {
	            return "Tree";
	        } else {
	            return "Forward";
	        }
	    } else if (u.getDiscoveryTime() > v.getDiscoveryTime() && u.getFinishTime() < v.getFinishTime()) {
	        return "Back";
	    } else if (
	    		v.getDiscoveryTime() < v.getFinishTime() && 
	    		v.getFinishTime() < u.getDiscoveryTime() && 
	    		u.getDiscoveryTime() < u.getFinishTime()) {
	        return "Cross";
	    } else {
	        return edge.getClassification()!="" ? edge.getClassification() : "";
	    }
	}
}

