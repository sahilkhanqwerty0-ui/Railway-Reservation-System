import java.util.*;

class RouteGraph {
    private Map<String, List<String>> graph = new HashMap<>();

    void addRoute(String a, String b) {
        graph.putIfAbsent(a, new ArrayList<String>());
        graph.putIfAbsent(b, new ArrayList<String>());
        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    boolean hasRoute(String source, String destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) return false;
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.add(source);
        visited.add(source);

        while (!q.isEmpty()) {
            String station = q.poll();
            if (station.equalsIgnoreCase(destination)) return true;
            for (String next : graph.get(station)) {
                if (visited.add(next)) q.add(next);
            }
        }
        return false;
    }
}
