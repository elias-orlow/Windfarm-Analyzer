package org.elias.model;

import org.elias.res.Districts;
import org.elias.res.constant.GeneralConstants;

import java.util.Objects;

/**
 * Model-Klasse, die Lokation einer Windkraftanlage repraesentiert.
 * <p>
 * Die Informationen stammen aus den CSV-Spalten "Ort", "Landkreis" und werden einer {@link WindTurbineGroup}
 * zugeordnet.
 */
public class Location
{
    String town = GeneralConstants.EMPTY_STRING;
    Districts district = null;

    /**
     * Erzeugt ein neues {@code Location}-Objekt mit Orts- und Landkreisangabe.
     * <p>
     * Der Konstruktor initialisiert den Ort durch den Namen der Stadt sowie
     * den zugehoerigen {@link Districts}-Landkreis. Die uebergebenen Werte werden uebernommen.
     *
     * @param town     der Name der Ortschaft.
     * @param district der Landkreis, in dem sich der Ort befindet.
     * @precondition Es wird einen Ort als {@code String} uebergeben. Der Landkreis kann
     * angegeben sein oder {@code null} sein, falls keine Landkreisangabe vorhanden ist.
     * @postcondition Das {@code Location}-Objekt ist initialisiert und speichert den
     * uebergebenen Ortsnamen sowie den uebergebenen Landkreis unveraendert.
     */
    public Location (String town, Districts district)
    {
        this.town = town;
        this.district = district;
    }

    // --- Getter & Setter ---

    public String getTown ()
    {
        return town;
    }

    public void setTown (String town)
    {
        this.town = town;
    }

    public Districts getDistrict ()
    {
        return district;
    }

    public void setDistrict (Districts district)
    {
        this.district = district;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(town, location.town) && district == location.district;
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(town, district);
    }
}
