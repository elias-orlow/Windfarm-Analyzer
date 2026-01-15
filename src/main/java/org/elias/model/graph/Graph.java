package org.elias.model.graph;

import java.util.*;

/**
 * Repraesentiert einen ungerichteten Graphen.
 * <p>
 * Der Graph besteht aus Vertices und Edges und wird intern als Adjazenzliste gespeichert.
 *
 * @param <E> Datentyp, der in den Vertices gespeichert wird.
 */
public class Graph<E>
{
    private final Map<Vertex<E>, List<Edge<E>>> adjacencyMap;

    /**
     * Erstellt einen leeren Graphen ohne Vertices und Kanten.
     *
     * @precondition keine.
     * @postcondition der Graph ist leer und besitzt eine initialisierte Adjazenzliste.
     */
    public Graph ()
    {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    // --- Getter ---

    public Map<Vertex<E>, List<Edge<E>>> getAdjacencyMap ()
    {
        return adjacencyMap;
    }

    // --- Vertex operations ---

    /**
     * Fuegt ein Vertex zum Graphen hinzu.
     *
     * @param vertex das hinzuzufuegende Vertex.
     * @precondition vertex ist nicht null.
     * @postcondition Vertex ist im Graphen vorhanden. Falls bereits existiert - passiert nichts.
     */
    public void addVertex (Vertex<E> vertex)
    {
        adjacencyMap.putIfAbsent(vertex, new LinkedList<>());
    }

//    public boolean containsVertex (Vertex<E> vertex)
//    {
//        return adjacencyMap.containsKey(vertex);
//    }
//
//    public Collection<Vertex<E>> vertices ()
//    {
//        return adjacencyMap.keySet();
//    }

    // --- Edge operations ---

    /**
     * Fuegt eine ungerichtete Kante zwischen zwei Vertices hinzu.
     * <p>
     * Die Kante wird intern in beide Richtungen gespeichert.
     *
     * @param o1     erstes Vertex.
     * @param o2     zweites Vertex.
     * @param weight Gewicht der Kante.
     * @precondition o1 und o2 sind nicht null.
     * @postcondition zwischen o1 und o2 existiert eine Kante mit dem gegebenen Gewicht.
     */
    public void addEdge (Vertex<E> o1, Vertex<E> o2, float weight)
    {
        addVertex(o1);
        addVertex(o2);

        Edge<E> edge = new Edge<>(o1, o2, weight);
        adjacencyMap.get(o1).add(edge);

        edge = new Edge<>(o2, o1, weight);
        adjacencyMap.get(o2).add(edge);

    }

    /**
     * Liefert alle Nachbarn eines Vertices.
     *
     * @param vertex das betrachtete Vertex.
     * @return Liste aller benachbarten Vertices.
     * @precondition vertex ist nicht null.
     * @postcondition der Graph bleibt unveraendert.
     */
    public List<Vertex<E>> getNeighbors (Vertex<E> vertex)
    {
        List<Vertex<E>> neighbors = new ArrayList<>();
        for (Edge<E> edge : adjacencyMap.getOrDefault(vertex, List.of()))
        {
            neighbors.add(edge.other(vertex));
        }
        return neighbors;
    }

//    public List<Edge<E>> getEdges (Vertex<E> vertex)
//    {
//        return adjacencyMap.getOrDefault(vertex, List.of());
//    }

    // ---------- Graph algorithms ----------

//    public List<Set<Vertex<E>>> getConnectedComponents ()
//    {
//        Set<Vertex<E>> visited = new HashSet<>();
//        List<Set<Vertex<E>>> components = new ArrayList<>();
//
//        for (Vertex<E> vertex : adjacencyMap.keySet())
//        {
//            if (!visited.contains(vertex))
//            {
//                Set<Vertex<E>> component = new HashSet<>();
//                bfs(vertex, visited, component);
//                components.add(component);
//            }
//        }
//        return components;
//    }
//
//    private void bfs (Vertex<E> start, Set<Vertex<E>> visited, Set<Vertex<E>> component)
//    {
//        Queue<Vertex<E>> queue = new LinkedList<>();
//        queue.add(start);
//        visited.add(start);
//
//        while (!queue.isEmpty())
//        {
//            Vertex<E> current = queue.poll();
//            component.add(current);
//
//            for (Vertex<E> neighbor : getNeighbors(current))
//            {
//                if (!visited.contains(neighbor))
//                {
//                    visited.add(neighbor);
//                    queue.add(neighbor);
//                }
//            }
//        }
//    }

}
