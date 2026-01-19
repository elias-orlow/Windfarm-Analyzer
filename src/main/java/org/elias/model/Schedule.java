package org.elias.model;

import java.util.LinkedList;
import java.util.List;

public class Schedule
{

    private final List<WorkDay> workDays;

    public Schedule ()
    {
        this.workDays = new LinkedList<>();
    }

    public void addWorkDay (WorkDay day)
    {
        workDays.add(day);
    }

    public void addWorkDays (List<WorkDay> workDays)
    {
        for (WorkDay workDay : workDays)
        {
            addWorkDay(workDay);
        }
    }

    public List<WorkDay> getWorkDays ()
    {
        return workDays;
    }

}
