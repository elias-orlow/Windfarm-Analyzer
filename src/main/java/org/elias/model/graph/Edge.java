package org.elias.model.graph;

/**
 * Repraesentiert eine Kante in einer Datenstruktur (in Graphen).
 * <p>
 * Speichert ein Datenelement eines generischen Typs.
 *
 * @param <E> der Typ der in der Kante gespeicherten Daten.
 */
public class Edge<E>
{
    private final Vertex<E> fromElement;
    private final Vertex<E> toElement;
    private final float weight;

    /**
     * Erstellt eine neue Kante zwischen zwei Vertices mit einem Gewicht.
     *
     * @param fromElement Start-Vertex der Kante.
     * @param toElement   End-Vertex der Kante.
     * @param weight      Gewicht der Kante.
     * @precondition fromElement und toElement sind nicht null.
     * @postcondition die Kante verbindet die beiden Vertices mit dem gegebenen Gewicht.
     */
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


    /**
     * Liefert das jeweils andere Vertex dieser Kante.
     *
     * @param vertex ein Vertex, das Teil dieser Kante ist.
     * @return das andere Vertex der Kante.
     * @throws IllegalArgumentException falls Vertex nicht Teil der Kante ist.
     * @precondition das uebergebene Vertex ist entweder fromElement oder toElement.
     * @postcondition es wird das gegenueberliegende Vertex zurueckgegeben.
     */
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
