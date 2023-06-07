package en.ics;
import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node {
	
	private String name;
	private String value;  // The value of the Node which was stored in the value column
	private String abbrev;  // The abbreviation for the Node
	private ArrayList<Edge> outgoingEdges;  
	private ArrayList<Edge> incomingEdges;
	
	//additionnal fields
	private int discoveryTime;
	private int finishTime;
	private boolean isVisited;
	
	public Node(String abbreviation) {
		abbrev = abbreviation;
		value = null;
		name = null;
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
		isVisited = false;
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}
	
	public void setAbbrev(String abbreviation) {
		abbrev = abbreviation;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void addOutgoingEdge(Edge e) {
		outgoingEdges.add(e);
	}
	
	public void addIncomingEdge(Edge e) {
		incomingEdges.add(e);
	}
	
	public int getDegree() {
		
		return this.incomingEdges.size() + this.outgoingEdges.size();
	}

	public int getDiscoveryTime() {
		return discoveryTime;
	}

	public void setDiscoveryTime(int discoveryTime) {
		this.discoveryTime = discoveryTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	
}

