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

    // --- Vertex operation ---

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

    // --- Edge operations ---

    /**
     * Fuegt eine ungerichtete Kante zwischen zwei Vertices hinzu.
     * <p>
     * Die Kante wird intern in beide Richtungen gespeichert.
     *
     * @param object1     erstes Vertex.
     * @param object2     zweites Vertex.
     * @param weight Gewicht der Kante.
     * @precondition object1 und o2 sind nicht null.
     * @postcondition zwischen object1 und o2 existiert eine Kante mit dem gegebenen Gewicht.
     */
    public void addEdge (Vertex<E> object1, Vertex<E> object2, float weight)
    {
        addVertex(object1);
        addVertex(object2);

        Edge<E> edge = new Edge<>(object1, object2, weight);
        adjacencyMap.get(object1).add(edge);

        edge = new Edge<>(object2, object1, weight);
        adjacencyMap.get(object2).add(edge);

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

}
