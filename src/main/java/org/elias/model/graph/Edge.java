package org.elias.model.graph;

public class Edge<E>
{
    private final Vertex<E> fromElement;
    private final Vertex<E> toElement;
    private final float weight;

    public Edge (Vertex<E> fromElement, Vertex<E> toElement, float weight)
    {
        this.fromElement = fromElement;
        this.toElement = toElement;
        this.weight = weight;
    }

    // --- Getters ---

    public Vertex<E> getFromElement ()
    {
        return fromElement;
    }

    public Vertex<E> getToElement ()
    {
        return toElement;
    }

    public float getWeight ()
    {
        return weight;
    }

    public Vertex<E> other (Vertex<E> vertex)
    {
        if (vertex.equals(fromElement))
        {
            return toElement;
        }
        if (vertex.equals(toElement))
        {
            return fromElement;
        }
        throw new IllegalArgumentException("Vertex not part of edge");
    }
}
