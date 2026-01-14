package org.elias.model.graph;

import java.util.Objects;

public class Vertex<E>
{
    public E data = null;

    public Vertex ()
    {
    }

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

    @Override
    public boolean equals (Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) o;

        return Objects.equals(getData(), vertex.getData());
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(getData());
    }

}

