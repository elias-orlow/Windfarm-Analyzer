package org.elias.model;

public class WindTurbineType
{
    private String model;

    public WindTurbineType (String model)
    {
        this.model = model;
    }

    // --- Getters & Setters ---

    public String getModel ()
    {
        return model;
    }

    public void setModel (String model)
    {
        this.model = model;
    }
}
