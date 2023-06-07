//import java.util.*;
package en.ics;
// Edge between two nodes
public class Edge {
	
	private int distance;
	private Node tail;
	private Node head;
	
	//additionnal field
	private String classification;
	
	public Edge(Node tailNode, Node headNode, int dist) {
		distance = dist;
		tail = tailNode;
		head = headNode;
		this.classification = "";
	}
	
	public Node getTail() {
		return tail;
	}
	
	public Node getHead() {
		return head;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setTail(Node newTail) {
		tail = newTail;
	}
	
	public void setHead(Node newHead) {
		head = newHead;
	}
	
	public void setDistance(int dist) {
		distance = dist;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	
}
