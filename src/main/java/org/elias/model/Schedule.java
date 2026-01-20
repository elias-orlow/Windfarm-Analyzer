package org.elias.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Repraesentiert einen vollstaendigen Wartungs-/Reiseplan,
 * bestehend aus mehreren Arbeitstagen ({@link WorkDay}).
 */
public class Schedule
{
    /**
     * Liste aller geplanten Arbeitstage in zeitlicher Reihenfolge
     */
    private final List<WorkDay> workDays;

    /**
     * Erstellt einen leeren Wartungsplan ohne Arbeitstage.
     *
     * @precondition keine.
     * @postcondition {@code workDays} ist initialisiert und leer.
     */
    public Schedule ()
    {
        this.workDays = new LinkedList<>();
    }

    // --- Getter ---

    public List<WorkDay> getWorkDays ()
    {
        return workDays;
    }


    /**
     * Fuegt einen einzelnen Arbeitstag zum Plan hinzu.
     *
     * @param day der hinzuzufuegende Arbeitstag.
     * @precondition {@code day} ist nicht null.
     * @postcondition der Arbeitstag ist am Ende der Liste gespeichert.
     */
    public void addWorkDay (WorkDay day)
    {
        workDays.add(day);
    }


    /**
     * Fuegt mehrere Arbeitstage zum Plan hinzu.
     * Die Reihenfolge der Liste bleibt erhalten.
     *
     * @param workDays Liste von Arbeitstagen, die hinzugefuegt werden sollen.
     * @precondition {@code workDays} ist nicht null.
     * @postcondition der Plan enthaelt alle uebergebenen Arbeitstage.
     */
    public void addWorkDays (List<WorkDay> workDays)
    {
        for (WorkDay workDay : workDays)
        {
            addWorkDay(workDay);
        }
    }


}
