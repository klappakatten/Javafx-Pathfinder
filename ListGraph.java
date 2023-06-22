
import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    @Override
    public void add(T node) {
        if (nodes.containsKey(node))
            return;
        Set<Edge<T>> edges = new HashSet<>();
        nodes.put(node, edges);
    }

    // Remove node from graph
    @Override
    public void remove(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException();
        }
        for (Edge<T> edge : nodes.get(node)) {
            Iterator<Edge<T>> iter = nodes.get(edge.getDestination()).iterator();
            Set<Edge<T>> newEdges = nodes.get(edge.getDestination());
            while (iter.hasNext()) {
                Edge<T> currentEdge = iter.next();
                if (currentEdge.getDestination().equals(node)) {
                    iter.remove();
                    newEdges.remove(edge);
                }
            }
        }

        nodes.remove(node);
    }

    // Connect node with edge
    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        } else if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException();
        }

        Edge<T> node1Edge = new Edge<>(node1, name, weight);
        Edge<T> node2Edge = new Edge<>(node2, name, weight);

        Set<Edge<T>> node1Edges = nodes.get(node1);
        Set<Edge<T>> node2Edges = nodes.get(node2);

        node1Edges.add(node2Edge);
        node2Edges.add(node1Edge);

        nodes.put(node1, node1Edges);
        nodes.put(node2, node2Edges);

    }

    // Disconnect node between nodes
    @Override
    public void disconnect(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        if (getEdgeBetween(node1, node2) == null) {
            throw new IllegalStateException();
        }

        Set<Edge<T>> edges = nodes.get(node1);
        for (Edge<T> edge : edges) {
            if (edge.getDestination().equals(node2)) {
                Set<Edge<T>> destinationEdges = nodes.get(edge.getDestination());
                for (Edge<T> destinationEdge : destinationEdges) {
                    if (destinationEdge.getDestination().equals(node1)) {
                        destinationEdges.remove(destinationEdge);
                        edges.remove(edge);
                        nodes.put(node1, edges);
                        nodes.put(node2, destinationEdges);
                        return;
                    }
                }
            }
        }
    }

    // Set new weight in edge
    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        } else if (getEdgeBetween(node1, node2) == null) {
            throw new IllegalStateException();
        }

        // Set first edge
        for (Edge<T> edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                edge.setWeight(weight);
            }
        }
        // Set second edge
        for (Edge<T> edge : nodes.get(node2)) {
            if (edge.getDestination().equals(node1)) {
                edge.setWeight(weight);
            }
        }
    }

    // Get list of nodes
    @Override
    public Set<T> getNodes() {
        return Collections.unmodifiableSet(nodes.keySet());
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException();
        }
        return Collections.unmodifiableCollection(nodes.get(node));
    }

    // Get edges between nodes
    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        for (Edge<T> edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (T key : nodes.keySet()) {
            sb.append("Key: ");
            sb.append(key);
            sb.append(", ");
            for (Edge<T> edge : nodes.get(key)) {
                sb.append(edge);
                sb.append(", ");
            }
        }
        sb.append("]\n");
        return sb.toString();
    }

    // Check if there is a path available with depth first search
    @Override
    public boolean pathExists(T from, T to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            return false;
        }
        Map<T, T> visited = new HashMap<>();
        depthFirstSearch(from, to, visited);
        return visited.containsKey(to);
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException();
        }
        Map<T, T> connections = new HashMap<>();
        LinkedList<T> queue = new LinkedList<>();

        connections.put(from, null);
        queue.add(from);

        while (!queue.isEmpty()) {
            T obj = queue.pollFirst();
            for (Edge<T> edge : nodes.get(obj)) {
                T target = edge.getDestination();
                if (!connections.containsKey(target)) {
                    connections.put(target, obj);
                    queue.add(target);
                }
            }
        }
        if (!connections.containsKey(to)) {
            return null;
        }
        return gatherPath(from, to, connections);
    }

    // Get path as list from search
    private List<Edge<T>> gatherPath(T from, T to, Map<T, T> connections) {
        LinkedList<Edge<T>> path = new LinkedList<>();
        T current = to;

        while (!current.equals(from)) {
            T next = connections.get(current);
            Edge<T> edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return path;
    }

    private void depthFirstSearch(T from, T to, Map<T, T> visited) {
        if (from.equals(to)) {
            return;
        }
        for (Edge<T> edge : nodes.get(from)) {
            T current = edge.getDestination();
            if (!visited.containsKey(edge.getDestination())) {
                visited.put(current, from);
                depthFirstSearch(edge.getDestination(), to, visited);
            }
        }
    }

}
