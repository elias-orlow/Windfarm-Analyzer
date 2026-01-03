package org.elias.model;

import org.elias.res.constant.ProjectConstants;

import java.util.ArrayList;
import java.util.List;

public class WindTurbineGroup
{
    private int ID = ProjectConstants.EMPTY_INT_VARIABLE;
    private int manufactureYear = ProjectConstants.EMPTY_INT_VARIABLE;
    private Location location = null;
    private String remarks = null;

    private final List<WindTurbineType> windTurbines = new ArrayList<>();

    public WindTurbineGroup (int ID, int manufactureYear, Location location, String remarks)
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

    public int getManufactureYear ()
    {
        return manufactureYear;
    }

    public void setManufactureYear (int manufactureYear)
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
