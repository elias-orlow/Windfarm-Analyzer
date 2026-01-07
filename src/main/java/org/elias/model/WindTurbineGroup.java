package org.elias.model;

import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


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
//TODO:
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

    public void addWindTurbine (WindTurbineType windTurbineType)
    {
        this.windTurbines.add(windTurbineType);
    }

    public void addWindTurbines (List<WindTurbineType> windTurbines)
    {
        for (WindTurbineType windTurbine : windTurbines)
        {
            this.addWindTurbine(windTurbine);
        }
    }
}
