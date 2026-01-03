package org.elias.model;

import org.elias.res.constant.GeneralConstants;

public class WindTurbineType
{
    private String model = GeneralConstants.EMPTY_STRING;

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
