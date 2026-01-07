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

    /**
     * Erzeugt ein neues {@code Coordinates}-Objekt mit den angegebenen Koordinaten.
     * <p>
     * Der Konstruktor initialisiert die Koordinate mit einem Breitengrad und
     * einem Längengrad.
     *
     * @param latitude  Der Breitengrad der Koordinate.
     * @param longitude Der Laengengrad der Koordinate.
     * @precondition Es werden zwei float-Werte für Breitengrad und Laengengrad
     * uebergeben. Eine inhaltliche Validierung der Werte erfolgt nicht.
     * @postcondition Das {@code Coordinates}-Objekt ist initialisiert und speichert den
     * uebergebenen Breitengrad sowie den uebergebenen Laengengrad unveraendert.
     */
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

    /**
     * Prueft dieses {@code Coordinates}-Objekt auf Gleichheit mit einem anderen
     * Objekt.
     * <p>
     * Zwei {@code Coordinates}-Objekte gelten als gleich, wenn das uebergebene
     * Objekt ebenfalls vom Typ {@code Coordinates} ist und sowohl Breitengrad
     * als auch Laengengrad exakt uebereinstimmen.
     *
     * @param o das zu vergleichende Objekt.
     * @precondition Es darf ein beliebiges Objekt uebergeben werden.
     * @postcondition Die Methode liefert {@code true}, wenn das uebergebene Objekt eine
     * {@code Coordinates}-Instanz ist und beide Koordinatenwerte mit denen
     * dieses Objekts uebereinstimmen. Andernfalls wird {@code false}
     * zurueckgegeben.
     */
    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(latitude, that.latitude) == 0 && Float.compare(longitude, that.longitude) == 0;
    }
}
