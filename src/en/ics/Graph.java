package en.ics;
import java.util.*;

public class Graph {

	private ArrayList<Node> nodeList;
	private ArrayList<Edge> edgeList;
	
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}
	
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
	
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	
	public void addNode(Node n) {
		nodeList.add(n);
	}
	
	public void addEdge(Edge e) {
		edgeList.add(e);
	}
	
	/**
	 * Get the node that has the specify value
	 * @return the related node
	 */
	public Node getStartedNode(String value) {
		 for(Node node : this.getNodeList()) {
	            if (node.getValue()!=null && node.getValue().equals(value)) {
	                return node;
	            }
	        }
		 return null;
	}
	
	
}

