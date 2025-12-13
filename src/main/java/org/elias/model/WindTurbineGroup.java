package org.elias.model;

import java.util.ArrayList;

public class WindTurbineGroup
{
    private int ID;
    private int manufactureYear;
    private Location location;
    private String remarks;

    private ArrayList<WindTurbineType> windTurbines;

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

    public ArrayList<WindTurbineType> getWindTurbines ()
    {
        return windTurbines;
    }

    public void setWindTurbines (ArrayList<WindTurbineType> windTurbines)
    {
        this.windTurbines = windTurbines;
    }
}
