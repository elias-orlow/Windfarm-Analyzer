package org.elias.model.graph;

import java.util.*;

public class AdjacencyGraph<E>
{
    private final Map<Vertex<E>, List<Edge<E>>> adjacencyMap;

    public AdjacencyGraph (Map<Vertex<E>, List<Edge<E>>> adjacencyMap)
    {
        this.adjacencyMap = adjacencyMap;
    }

    // --- Getter ---

    public Map<Vertex<E>, List<Edge<E>>> getAdjacencyMap ()
    {
        return adjacencyMap;
    }

    // --- Vertex operations ---

    public void addVertex (Vertex<E> vertex)
    {
        adjacencyMap.putIfAbsent(vertex, new LinkedList<>());
    }

    public boolean containsVertex (Vertex<E> vertex)
    {
        return adjacencyMap.containsKey(vertex);
    }

    public Collection<Vertex<E>> vertices ()
    {
        return adjacencyMap.keySet();
    }

    // --- Edge operations ---

    public void addEdge (Vertex<E> o1, Vertex<E> o2, float weight)
    {
        addVertex(o1);
        addVertex(o2);

        Edge<E> edge = new Edge<>(o1, o2, weight);
        adjacencyMap.get(o1).add(edge);

        edge = new Edge<>(o2, o1, weight);
        adjacencyMap.get(o2).add(edge);

    }

    public List<Vertex<E>> getNeighbors (Vertex<E> vertex)
    {
        List<Vertex<E>> neighbors = new ArrayList<>();
        for (Edge<E> edge : adjacencyMap.getOrDefault(vertex, List.of()))
        {
            neighbors.add(edge.other(vertex));
        }
        return neighbors;
    }

    public List<Edge<E>> getEdges(Vertex<E> vertex) {
        return adjacencyMap.getOrDefault(vertex, List.of());
    }

    // ---------- Graph algorithms ----------

    public List<Set<Vertex<E>>> getConnectedComponents() {
        Set<Vertex<E>> visited = new HashSet<>();
        List<Set<Vertex<E>>> components = new ArrayList<>();

        for (Vertex<E> vertex : adjacencyMap.keySet()) {
            if (!visited.contains(vertex)) {
                Set<Vertex<E>> component = new HashSet<>();
                bfs(vertex, visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private void bfs(Vertex<E> start, Set<Vertex<E>> visited, Set<Vertex<E>> component) {
        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex<E> current = queue.poll();
            component.add(current);

            for (Vertex<E> neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }


}
