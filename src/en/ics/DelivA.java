package en.ics;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DelivA {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivA(File in, Graph gr) {
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
		
		// Calls the method that will do the work of deliverable A
		runDelivA();
		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	private void runDelivA() {
		System.out.println("Compaire" + "SF".compareTo("Sea"));
		if(graph!=null)
		{
			ArrayList<Node> nodeList = graph.getNodeList();

	        // Calculate indegree and outdegree for each node
	        HashMap<Node, Integer> indegreeMap = new HashMap<>();
	        HashMap<Node, Integer> outdegreeMap = new HashMap<>();
	        for (Node node : nodeList) {
	            int indegree = node.getIncomingEdges().size();
	            int outdegree = node.getOutgoingEdges().size();
	            indegreeMap.put(node, indegree);
	            outdegreeMap.put(node, outdegree);	
	        }

	        // Sort nodes based on indegree (descending) and abbreviation (ascending)
	        nodeList.sort((node1, node2) -> {
	            int indegree1 = indegreeMap.get(node1);
	            int indegree2 = indegreeMap.get(node2);
	            String abbrev1 = node1.getAbbrev();
	            String abbrev2 = node2.getAbbrev();

	            if (indegree1 != indegree2) {
	                return Integer.compare(indegree2, indegree1); // Sort by indegree (descending)
	            } else {
	                return abbrev1.compareToIgnoreCase(abbrev2); // Sort alphabetically by abbreviation
	            }
	        });

	        // Print nodes indegree
	        System.out.println("Indegree");
	        output.println("Indegree: ");
	        for (Node node : nodeList) {
	            int indegree = indegreeMap.get(node);
	            String abbreviation = node.getAbbrev();
	            System.out.printf("Node %s has indegree %d\n", abbreviation, indegree);
	            output.println("Node "+abbreviation+" has indegree "+indegree);//Prints to file
	            
	        }
	        
	     // Sort nodes based on outdegree (descending) and abbreviation (ascending)
	        nodeList.sort((node1, node2) -> {
	            int outdegree1 = outdegreeMap.get(node1);
	            int outdegree2 = outdegreeMap.get(node2);
	            String abbrev1 = node1.getAbbrev();
	            String abbrev2 = node2.getAbbrev();

	            if (outdegree1 != outdegree2) {
	                return Integer.compare(outdegree2, outdegree1); // Sort by outdegree (descending)
	            } else {
	                return abbrev1.compareToIgnoreCase(abbrev2); // Sort alphabetically by abbreviation
	            }
	        });
	        // Print nodes outdegree
	        System.out.println("\nOutdegree");
	        output.println("\nOutdegree: ");
	        for (Node node : nodeList) {
	            int outdegree = outdegreeMap.get(node);
	            String abbreviation = node.getAbbrev();
	            System.out.printf("Node %s has outdegree %d\n", abbreviation, outdegree);
	            output.println("Node "+abbreviation+" has outdegree "+outdegree);//Prints to file    
	        }
	        
		}else {
			System.out.println("DelivA:  The graph is null, please choose a file before running !");//Prints to console
			output.println("DelivA:  The graph is null, please choose a file before running !");//Prints to file
		}
	}

}
