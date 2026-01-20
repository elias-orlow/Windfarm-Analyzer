package org.elias.model.graph;

import java.util.Objects;

/**
 * Repraesentiert einen Knoten in einer Datenstruktur (in Graphen).
 * <p>
 * Speichert ein Datenelement eines generischen Typs.
 *
 * @param <E> der Typ der im Knoten gespeicherten Daten.
 */
public class Vertex<E>
{
    public E data = null;

    /**
     * Erzeugt einen neuen {@code Vertex} ohne gespeicherte Daten.
     *
     * @precondition keine.
     * @postcondition ein Vertex ist erzeugt und hat keine Daten.
     */
    public Vertex ()
    {
    }

    /**
     * Erzeugt einen neuen {@code Vertex} mit gespeicherten Daten.
     *
     * @precondition es werden Daten als Parameter uebergeben (auch null).
     * @postcondition ein Vertex ist erzeugt und speichert die uebergebenen Daten.
     */
    public Vertex (E data)
    {
        this.data = data;
    }

    // --- Getter & Setter ---

    public E getData ()
    {
        return data;
    }

    public void setData (E data)
    {
        this.data = data;
    }

    /**
     * Vergleicht diesen Vertex mit einem anderen Objekt.
     *
     * @param object das Objekt, das mit diesem Vertex verglichen werden soll.
     * @return boolean-Wert, ob die Objekte gleich sind.
     * @precondition das uebergebene Objekt ist nicht null.
     * @postcondition es wird true zurueckgegeben, wenn die Daten gleich sind, sonst false.
     */
    @Override
    public boolean equals (Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || getClass() != object.getClass())
        {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) object;

        return Objects.equals(getData(), vertex.getData());
    }

    /**
     * Berechnet den Hashcode dieses Objektes.
     *
     * @return einen ganzzahligen Hashwert basierend auf den Daten.
     * @precondition die gespeicherten Daten sind nicht null.
     * @postcondition gleiche Objekte liefern den gleichen Hashwert.
     */
    @Override
    public int hashCode ()
    {
        return Objects.hash(getData());
    }

}

