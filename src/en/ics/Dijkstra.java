package en.ics;

import java.util.*;

public class Dijkstra {

    public static List<Node> shortestPath(Graph graph, Node start, Node end) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<NodeDistancePair> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(NodeDistancePair::getDistance));

        // Initialisation des distances
        for (Node node : graph.getNodeList()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);

        // Ajout du n�ud de d�part � la file de priorit�
        priorityQueue.offer(new NodeDistancePair(start, 0.0));

        while (!priorityQueue.isEmpty()) {
            NodeDistancePair currentPair = priorityQueue.poll();
            Node current = currentPair.getNode();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // Mise � jour des distances et des n�uds pr�c�dents pour les voisins du n�ud actuel
            for (Edge edge : current.getOutgoingEdges()) {
                Node neighbor = edge.getHead();
                double distanceThroughCurrent = distances.get(current) + edge.getDistance();

                if (distanceThroughCurrent < distances.get(neighbor)) {
                    distances.put(neighbor, distanceThroughCurrent);
                    previousNodes.put(neighbor, current);
                    priorityQueue.offer(new NodeDistancePair(neighbor, distanceThroughCurrent));
                }
            }
        }

        // Construction du chemin � partir des n�uds pr�c�dents
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(0, current);
            current = previousNodes.get(current);
        }

        return path;
    }

    private static class NodeDistancePair {
        private Node node;
        private double distance;

        public NodeDistancePair(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public Node getNode() {
            return node;
        }

        public double getDistance() {
            return distance;
        }
    }
}