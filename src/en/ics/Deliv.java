package en.ics;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Deliv {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public Deliv(File in, Graph gr) {
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
		run();

		output.flush();
	}
	
	
	List<Node> nodeList = new ArrayList<>();
	List<Node> bitonicTour = new ArrayList<>();
	private void run() {
		System.out.println("Inside the new run function....");
		nodeList = graph.getNodeList();
		
		// sort nodes by values
	    Collections.sort(nodeList, new Comparator<Node>() {
	        @Override
	        public int compare(Node n1, Node n2) {
	            return Double.compare(Double.parseDouble(n1.getValue()), Double.parseDouble(n2.getValue()));
	        }
	    });
	    
	    int n = nodeList.size();
	   
	    int distance = 0;
	    int i= n-1, j=n-1;
	    Node highestNode = nodeList.get(n-1);
	    Node lowestNode = nodeList.get(0);
	    
	    //Specify the begin node of the bitonic tour (the node with the highest value)
	    bitonicTour.add(highestNode);
	    
	   
	    //compute the intermediate nodes with bitonic algorithm
	    distance += bitonic(i, j);
	    
	    bitonicTour.add(lowestNode);
	    
	    //add the unvisited nodes from the and
	    // sort nodes by values
	    Collections.sort(nodeList, new Comparator<Node>() {
	        @Override
	        public int compare(Node n1, Node n2) {
	            return Double.compare(Double.parseDouble(n2.getValue()), Double.parseDouble(n1.getValue()));
	        }
	    });
	    
	    for (Node node : nodeList) {
	       if(!bitonicTour.contains(node))
	    	   bitonicTour.add(node);
	    }
	    
	    //Specify the end  node of the bitonic tour (the node with the highest value)
	    bitonicTour.add(highestNode);
	   
	    
	    System.out.println("Tour is  : ");
	    for (Node node : bitonicTour) {
	        System.out.print(node.getAbbrev()+ "-> ");
	    }
		
	}
	
	private int bitonic(int i, int j) {
		
		Node begin = nodeList.get(i);
		Node end = nodeList.get(j);
		
		//first cas b(0,1) = |P0, P1|
		//if(i==0 && j==1)
		if(i==j-1)
			return distance(begin, end);
		
		//second cas (j-1!= i and i
		if(i<j-1) {
			//addNode(minValueNodes.get(minIndex));
			return bitonic(i, j-1) + distance(nodeList.get(j-1), nodeList.get(j));
		}else {
			//third cas
			
			List<Integer> bitonics = new ArrayList<Integer>();
			List<Node> minValueNodes = new ArrayList<Node>();
			for(int k=1; k<i; k++) {
				//if(!bitonicTour.contains(nodeList.get(k))) {
					int result = bitonic(k, i) + distance(nodeList.get(k), nodeList.get(j));
					bitonics.add(result);
					minValueNodes.add(nodeList.get(k));
				//}
				//System.out.print(nodeList.get(k).getAbbrev()+" ");
			}
			//System.out.println();
			
			//Assume that we found some values
			if(bitonics.size()>0)
			{
				int minIndex = findIndexOfMinBitonic(bitonics);
				addNode(minValueNodes.get(minIndex));
				return bitonics.get(minIndex);
			}
		}
		
		return 0;
	}
	
	//add a node the the bitonic tour
	private void addNode(Node node) {
		//if the node is not already in the bitonic tour, then add it (to avoid duplicated node)
		if(!bitonicTour.contains(node))
			bitonicTour.add(node);
	}
	
	//retrive the min value from a list of integer
	private int findIndexOfMinBitonic(List<Integer> bitonics) {
		
		//set the current min index to the first index
		int minIndex = 0; 
		int minValue = bitonics.get(minIndex);
		
		for(int currentValue: bitonics) {
			if(currentValue < minValue)
			{
				minIndex = bitonics.indexOf(currentValue);
				minValue = currentValue;
			}
				
		}
		
		//System.out.println(bitonics+" min is ="+minIndex);
		return minIndex;
	}

	//retrieve the distance between two nodes
	private int distance(Node node1, Node node2) {
		    
			for(Edge e :graph.getEdgeList()) {
				if(e.getTail().equals(node1) && e.getHead().equals(node2))
					return e.getDistance();
				else if(e.getHead().equals(node1) && e.getTail().equals(node2))
					return e.getDistance();
			}
			
			//This line is just for  test, will be removed
			System.out.println("UnComplet graph...");
			return 0;
	}
	
	//compute the distance of a specific path
	private int calculateDistance(List<Node> bitonicTour) {
        int distance = 0;
        for (int i = 0; i < bitonicTour.size() - 1; i++) {
            Node current = bitonicTour.get(i);
            Node next = bitonicTour.get(i + 1);
            distance += distance(current, next);
        }
        
        return distance;
    }
	
}
