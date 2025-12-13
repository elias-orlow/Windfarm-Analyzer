package org.elias.model;

public class Location
{
    String town;
    String district;

    public Location (String town, String district)
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

    public String getDistrict ()
    {
        return district;
    }

    public void setDistrict (String district)
    {
        this.district = district;
    }
}
