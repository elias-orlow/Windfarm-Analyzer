package org.elias.model;

import java.util.ArrayList;

public class WindFarm
{
    private String name;
    private float totalPerformance;
    private Coordinates coordinates;

    private ArrayList<ProjectManager> projectManagers;
    private ArrayList<WindTurbineGroup> windTurbineGroups;

    public WindFarm (String name, float totalPerformance, Coordinates coordinates)
    {
        this.name = name;
        this.totalPerformance = totalPerformance;
        this.coordinates = coordinates;
    }

    // --- Getters & Setters ---


    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getTotalPerformance ()
    {
        return totalPerformance;
    }

    public void setTotalPerformance (float totalPerformance)
    {
        this.totalPerformance = totalPerformance;
    }

    public Coordinates getCoordinates ()
    {
        return coordinates;
    }

    public void setCoordinates (Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }
}
