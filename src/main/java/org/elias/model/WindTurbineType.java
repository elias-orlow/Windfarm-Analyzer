package org.elias.model;

import org.elias.res.constant.ProjectConstants;

public class WindTurbineType
{
    private String model = ProjectConstants.EMPTY_STRING;

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
