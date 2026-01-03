package org.elias.model;

import org.elias.res.constant.GeneralConstants;

public class ProjectManager
{
    private String company = GeneralConstants.EMPTY_STRING;

    public ProjectManager (String company)
    {
        this.company = company;
    }

    // --- Getters & Setters ---

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }
}
