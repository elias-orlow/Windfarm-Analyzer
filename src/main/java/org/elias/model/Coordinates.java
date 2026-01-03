package org.elias.model;

import org.elias.res.constant.GeneralConstants;

public class Coordinates
{
    private float latitude = GeneralConstants.EMPTY_FLOAT_VARIABLE;
    private float longitude = GeneralConstants.EMPTY_FLOAT_VARIABLE;

    public Coordinates (float latitude, float longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // --- Getters & Setters ---

    public float getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (float latitude)
    {
        this.latitude = latitude;
    }

    public float getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (float longitude)
    {
        this.longitude = longitude;
    }
}
