package org.elias.model;

import org.elias.util.GeoFormula;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SchedulePlanner {

    private static final int MAX_TURBINES_PER_DAY = 4;
    private static final double AVERAGE_SPEED_KMH = 150.0;
    private static final Duration MAX_DAILY_DRIVE_TIME = Duration.ofHours(2);

    public static Schedule createPlan(List<WindFarm> windFarmsToMaintain) {

        List<WorkDay> workDays = new LinkedList<>();
        int dayNumber = 1;

        for (int farmIndex = 0; farmIndex < windFarmsToMaintain.size(); farmIndex++) {

            WindFarm currentFarm = windFarmsToMaintain.get(farmIndex);
            Queue<WindTurbineType> turbines = collectTurbines(currentFarm);

            // ===== Wartungstage =====
            while (!turbines.isEmpty()) {

                WorkDay workDay = new WorkDay(dayNumber);
                workDay.setMaintainedWindFarm(currentFarm);

                int turbinesToday = 0;
                while (!turbines.isEmpty() && turbinesToday < MAX_TURBINES_PER_DAY) {
                    workDay.addWindTurbine(turbines.poll());
                    turbinesToday++;
                }

                workDays.add(workDay);
                dayNumber++;
            }

            // ===== Fahrt zur nächsten Anlage =====
            if (farmIndex < windFarmsToMaintain.size() - 1) {

                WindFarm nextFarm = windFarmsToMaintain.get(farmIndex + 1);
                Duration remainingDriveTime = calculateDriveTime(currentFarm, nextFarm);

                // 1️⃣ Fahrt am letzten Wartungstag (abends!)
                WorkDay lastWorkDay = workDays.get(workDays.size() - 1);

                Duration driveToday = remainingDriveTime.compareTo(MAX_DAILY_DRIVE_TIME) > 0
                        ? MAX_DAILY_DRIVE_TIME
                        : remainingDriveTime;

                lastWorkDay.setDriveTime(driveToday);
                remainingDriveTime = remainingDriveTime.minus(driveToday);

                // 2️⃣ Falls nötig: weitere Fahrtage OHNE Wartung
                while (remainingDriveTime.compareTo(Duration.ZERO) > 0) {

                    WorkDay travelDay = new WorkDay(dayNumber);
                    travelDay.setMaintainedWindFarm(currentFarm);

                    Duration dailyDrive = remainingDriveTime.compareTo(MAX_DAILY_DRIVE_TIME) > 0
                            ? MAX_DAILY_DRIVE_TIME
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

    // ============================================================
    // Hilfsmethoden
    // ============================================================

    private static Queue<WindTurbineType> collectTurbines(WindFarm farm) {
        Queue<WindTurbineType> turbines = new LinkedList<>();
        for (WindTurbineGroup group : farm.getWindTurbineGroups()) {
            turbines.addAll(group.getWindTurbines());
        }
        return turbines;
    }

    private static Duration calculateDriveTime(WindFarm from, WindFarm to) {

        double distanceKm = GeoFormula.haversineDistance(
                from.getCoordinates().getLatitude(),
                to.getCoordinates().getLatitude(),
                from.getCoordinates().getLongitude(),
                to.getCoordinates().getLongitude()
        );

        double hours = distanceKm / AVERAGE_SPEED_KMH;
        long minutes = Math.round(hours * 60);

        return Duration.ofMinutes(minutes);
    }
}
