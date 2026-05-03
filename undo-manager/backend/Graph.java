import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Map;

class Graph {
    private final Map<String, List<Edge>> adj = new HashMap<>();

    void addLocation(String location) {
        adj.putIfAbsent(location, new ArrayList<>());
    }

    void addEdge(String from, String to, int distance) {
        addLocation(from);
        addLocation(to);
        adj.get(from).add(new Edge(to, distance));
        adj.get(to).add(new Edge(from, distance));
    }

    RouteResult shortestPath(String src, String destination) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);

        for (String node : adj.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }

        dist.put(src, 0);
        pq.add(new NodeDistance(src, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            if (current.distance > dist.get(current.node)) {
                continue;
            }

            for (Edge edge : adj.getOrDefault(current.node, Collections.emptyList())) {
                int nextDistance = current.distance + edge.weight;
                if (nextDistance < dist.get(edge.to)) {
                    dist.put(edge.to, nextDistance);
                    parent.put(edge.to, current.node);
                    pq.add(new NodeDistance(edge.to, nextDistance));
                }
            }
        }

        if (!dist.containsKey(destination) || dist.get(destination) == Integer.MAX_VALUE) {
            return new RouteResult(Integer.MAX_VALUE, new ArrayList<>());
        }

        List<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return new RouteResult(dist.get(destination), path);
    }

    static class Edge {
        String to;
        int weight;

        Edge(String to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class NodeDistance {
        String node;
        int distance;

        NodeDistance(String node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    static class RouteResult {
        int distance;
        List<String> path;

        RouteResult(int distance, List<String> path) {
            this.distance = distance;
            this.path = path;
        }
    }
}
