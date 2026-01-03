package org.elias.model;

import org.elias.res.Districts;
import org.elias.res.constant.GeneralConstants;

public class Location
{
    String town = GeneralConstants.EMPTY_STRING;
    Districts district = null;

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
}
