package org.elias.model.graph;

import java.util.List;

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

}

