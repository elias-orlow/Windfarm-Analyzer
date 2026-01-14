package org.elias.model;

import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Model-Klasse fuer eine Windkraftanlage, die ganze Zeile aus der CSV-Tabelle repraesentiert.
 */
public class WindTurbineGroup
{
    private int ID = GeneralConstants.EMPTY_INT_VARIABLE;
    private Year manufactureYear = Year.of(GeneralConstants.EMPTY_INT_VARIABLE);
    private Location location = null;
    private String remarks = null;

    private final List<WindTurbineType> windTurbines = new ArrayList<>();

    /**
     * Erzeugt eine neue {@link WindTurbineGroup}.
     * <p>
     * Der Konstruktor initialisiert die Gruppe mit einer eindeutigen Kennung,
     * dem Herstellungsjahr, der zugehoerigen {@link Location} sowie optionalen
     * Bemerkungen.
     *
     * @param ID              die eindeutige Identifikationsnummer der Windturbinen-Gruppe.
     * @param manufactureYear das Herstellungsjahr der Windturbinen.
     * @param location        der Standort der Windturbinen-Gruppe.
     * @param remarks         zusaetzliche Bemerkungen zur Windturbinen-Gruppe.
     * @precondition es werden eine Identifikationsnummer, ein Herstellungsjahr, ein
     * Standort sowie eine Bemerkung uebergeben. Die Parameter koennen
     * inhaltlich leer oder {@code null} sein; eine Validierung erfolgt nicht.
     * @postcondition die {@code WindTurbineGroup} ist initialisiert und speichert die
     * uebergebenen Werte unveraendert.
     */
    public WindTurbineGroup (int ID, Year manufactureYear, Location location, String remarks)
    {
        this.ID = ID;
        this.manufactureYear = manufactureYear;
        this.location = location;
        this.remarks = remarks;
    }

    // --- Getters & Setters ---

    public int getID ()
    {
        return ID;
    }

    public void setID (int ID)
    {
        this.ID = ID;
    }

    public Year getManufactureYear ()
    {
        return manufactureYear;
    }

    public void setManufactureYear (Year manufactureYear)
    {
        this.manufactureYear = manufactureYear;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public String getRemarks ()
    {
        return remarks;
    }

    public void setRemarks (String remarks)
    {
        this.remarks = remarks;
    }

    public List<WindTurbineType> getWindTurbines ()
    {
        return windTurbines;
    }

    // --- Add methods ---

    /**
     * Fuegt einen Windturbinentyp zur Gruppe hinzu.
     *
     * @param windTurbineType der hinzuzufuegende {@link WindTurbineType}.
     * @precondition windTurbineType darf nicht null sein.
     * @postcondition der Windturbinentyp befindet sich in der Liste windTurbines
     */
    public void addWindTurbine (WindTurbineType windTurbineType)
    {
        this.windTurbines.add(windTurbineType);
    }


    /**
     * Fuegt mehrere Windturbinentypen zur Gruppe hinzu.
     *
     * @param windTurbines die Liste der hinzuzufuegenden {@link WindTurbineType}-Objekte
     * @precondition windTurbines darf nicht null sein; die Liste kann leer sein
     * @postcondition Alle uebergebenen Windturbinentypen befinden sich in der Liste windTurbines
     */
    public void addWindTurbines (List<WindTurbineType> windTurbines)
    {
        for (WindTurbineType windTurbine : windTurbines)
        {
            this.addWindTurbine(windTurbine);
        }
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        WindTurbineGroup that = (WindTurbineGroup) o;
        return ID == that.ID && Objects.equals(manufactureYear, that.manufactureYear) && Objects.equals(location, that.location) && Objects.equals(remarks, that.remarks) && Objects.equals(windTurbines, that.windTurbines);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(ID, manufactureYear, location, remarks, windTurbines);
    }
}
