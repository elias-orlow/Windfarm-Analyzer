package org.elias.model;

import org.elias.res.constant.GeneralConstants;

/**
 * Model-Klasse, die Koordinaten eines Windparks repraesentiert.
 * <p>
 * Die Informationen stammen aus den CSV-Spalten "Breitengrad", "Laengengrad" und werden einer {@link WindFarm}
 * zugeordnet.
 */
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

    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(latitude, that.latitude) == 0 && Float.compare(longitude, that.longitude) == 0;
    }
}
