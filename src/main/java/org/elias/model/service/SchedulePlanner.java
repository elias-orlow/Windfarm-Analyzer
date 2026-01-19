package org.elias.model.service;

import org.elias.model.*;
import org.elias.res.constant.GeneralConstants;
import org.elias.util.GeoFormula;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Plant einen Wartungs-/Fahrplan fuer eine Liste von Windparks.
 * <p>
 * Die Klasse erstellt aus einer vorgegebenen Reihenfolge von Windparks
 * einen {@link Schedule}, bestehend aus mehreren {@link WorkDay}-Objekten.
 * Dabei werden Wartungstage und Fahrtage berechnet.
 */
public class SchedulePlanner
{
    /**
     * Erstellt einen Wartungsplan fuer die angegebenen Windparks.
     * <p>
     * Pro Tag koennen maximal eine feste Anzahl an Windkraftanlagen (4 Stueck)
     * gewartet werden. Zwischen zwei Windparks werden Fahrtage eingeplant,
     * falls die benoetigte Fahrzeit einen Tag ueberschreitet.
     *
     * @param windFarmsToMaintain Liste der Windparks in Wartungsreihenfolge.
     * @return ein vollstaendiger {@link Schedule} mit allen Arbeitstagen.
     * @precondition {@code windFarmsToMaintain} ist nicht null und enthaelt mindestens einen {@link WindFarm}.
     * @postcondition ein {@link Schedule} wurde erzeugt, der alle Wartungs-/Fahrtage
     * in zeitlich korrekter Reihenfolge enthaelt.
     */
    public static Schedule createPlan (List<WindFarm> windFarmsToMaintain)
    {
        List<WorkDay> workDays = new LinkedList<>();
        int dayNumber = GeneralConstants.INT_ONE;

        for (int farmIndex = 0; farmIndex < windFarmsToMaintain.size(); farmIndex++)
        {

            WindFarm currentFarm = windFarmsToMaintain.get(farmIndex);
            Queue<WindTurbineType> turbines = collectTurbines(currentFarm);

            // Wartungstage
            while (!turbines.isEmpty())
            {

                WorkDay workDay = new WorkDay(dayNumber);
                workDay.setMaintainedWindFarm(currentFarm);

                int turbinesToday = GeneralConstants.INT_ZERO;
                while (!turbines.isEmpty() && turbinesToday < GeneralConstants.MAX_TURBINES_PER_DAY)
                {
                    workDay.addWindTurbine(turbines.poll());
                    turbinesToday++;
                }

                workDays.add(workDay);
                dayNumber++;
            }

            // Fahrt zur naechsten Anlage
            if (farmIndex < windFarmsToMaintain.size() - GeneralConstants.INT_ONE)
            {

                WindFarm nextFarm = windFarmsToMaintain.get(farmIndex + GeneralConstants.INT_ONE);
                Duration remainingDriveTime = calculateDriveTime(currentFarm, nextFarm);

                // Fahrt am letzten Wartungstag
                WorkDay lastWorkDay = workDays.getLast();

                Duration driveToday = remainingDriveTime.compareTo(GeneralConstants.MAX_DAILY_DRIVE_TIME)
                        > GeneralConstants.INT_ZERO
                        ? GeneralConstants.MAX_DAILY_DRIVE_TIME
                        : remainingDriveTime;

                lastWorkDay.setDriveTime(driveToday);
                remainingDriveTime = remainingDriveTime.minus(driveToday);

                // Weitere Fahrtage ohne Wartung
                while (remainingDriveTime.compareTo(Duration.ZERO) > GeneralConstants.INT_ZERO)
                {

                    WorkDay travelDay = new WorkDay(dayNumber);
                    travelDay.setMaintainedWindFarm(currentFarm);

                    Duration dailyDrive = remainingDriveTime.compareTo(GeneralConstants.MAX_DAILY_DRIVE_TIME)
                            > GeneralConstants.INT_ZERO
                            ? GeneralConstants.MAX_DAILY_DRIVE_TIME
                            : remainingDriveTime;

                    travelDay.setDriveTime(dailyDrive);
                    workDays.add(travelDay);

                    remainingDriveTime = remainingDriveTime.minus(dailyDrive);
                    dayNumber++;
                }
            }
        }

        Schedule schedule = new Schedule();
        schedule.addWorkDays(workDays);
        return schedule;
    }

    // --- Hilfsmethoden ---

    /**
     * Sammelt alle Windkraftturbine eines Windparks in einer Warteschlange.
     *
     * @param farm der Windpark.
     * @return Warteschlange aller zu wartenden Windkraftturbinen.
     * @precondition {@code farm} ist nicht null.
     * @postcondition alle Windkraftturbine des Windparks sind in der Queue enthalten.
     */
    private static Queue<WindTurbineType> collectTurbines (WindFarm farm)
    {
        Queue<WindTurbineType> turbines = new LinkedList<>();
        for (WindTurbineGroup group : farm.getWindTurbineGroups())
        {
            turbines.addAll(group.getWindTurbines());
        }
        return turbines;
    }


    /**
     * Berechnet die benoetigte Fahrzeit zwischen zwei Windparks.
     *
     * @param from Start-Windpark.
     * @param to   Ziel-Windpark.
     * @return Fahrzeit als {@link Duration}.
     * @precondition beide Windparks sind nicht null und besitzen gueltige Koordinaten.
     * @postcondition die zurueckgegebene Dauer repraesentiert die benoetigte Fahrzeit
     * basierend auf einer durchschnittlichen Geschwindigkeit.
     */
    private static Duration calculateDriveTime (WindFarm from, WindFarm to)
    {

        double distanceKm = GeoFormula.haversineDistance(
                from.getCoordinates().getLatitude(),
                to.getCoordinates().getLatitude(),
                from.getCoordinates().getLongitude(),
                to.getCoordinates().getLongitude()
        );

        double hours = distanceKm / GeneralConstants.AVERAGE_SPEED_KMH;
        long minutes = Math.round(hours * GeneralConstants.MINUTES_PER_HOUR);

        return Duration.ofMinutes(minutes);
    }
}
