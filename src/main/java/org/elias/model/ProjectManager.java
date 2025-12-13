package org.elias.model;

public class ProjectManager
{
    private String company;

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
