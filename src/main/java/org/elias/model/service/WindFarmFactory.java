package org.elias.model.service;

import org.elias.model.*;
import org.elias.res.constant.GeneralConstants;
import org.elias.util.DataCellParser;

import java.time.Year;
import java.util.List;
import java.util.Map;

/**
 * Factory-Klasse fuer Erzeugung von {@link WindFarm} aus CSV-Daten.
 * <p>
 * Wird von {@link WindFarmImporter} benutzt.
 */
public class WindFarmFactory
{
    /**
     * Erstellt {@link WindFarm} aus der Liste zusammengehöriger CSV-Zeilen.
     * <p>
     * Die erste Zeile wird als Basis fuer den ganzen Windpark benutzt.
     *
     * @param rows Liste von CSV-Zeilen, die logisch zu einem Windpark gehoeren.
     * @return ein initialisiertes {@link WindFarm}-Objekt.
     * @precondition alle Zeilen gehoeren zum selben Windpark und erste Zeile beinhaltet relevante Information fuer
     * den ganzen Windpark.
     * @postcondition die zurueckgegebene Windfarm enthaelt alle Windkraftanlagen, die aus der CSV-Tabelle kommen.
     */
    public static WindFarm createWindFarm (List<String[]> rows) //TODO: in kleineren Methoden zerlegen
    {
        String[] baseRow = rows.getFirst();
        ProjectManagerAdministration projectManagerAdministration = ProjectManagerAdministration.getInstance();
        
        // --- Basisinformation aus der ersten Roh-Zeile ---
        String windFarmName = DataCellParser.parseWindFarmName(baseRow[GeneralConstants.COLUMN_INDEX_NAME]);

        float[] coordinates = DataCellParser.parseCoordinates(
                baseRow[GeneralConstants.COLUMN_INDEX_LATITUDE], 
                baseRow[GeneralConstants.COLUMN_INDEX_LONGITUDE]);
        Coordinates windFarmCoordinates = new Coordinates(
                coordinates[GeneralConstants.INT_ZERO],
                coordinates[GeneralConstants.INT_ONE]);

        float totalPerformance = DataCellParser.parseTotalPerformance(baseRow[GeneralConstants.COLUMN_INDEX_PERFORMANCE]);
        
        WindFarm currentWindFarm = new WindFarm(windFarmName, totalPerformance, windFarmCoordinates);

        // --- Projektbetreiber hinzufuegen ---
        for (String companyName : DataCellParser.parseProjectManager(baseRow[GeneralConstants.COLUMN_INDEX_PROJECTMANAGER]))
        {
            ProjectManager currrentProjectManager = new ProjectManager(companyName);
            currentWindFarm.addProjectManager(currrentProjectManager);
            projectManagerAdministration.addProjectManager(currrentProjectManager);
        }


        // --- Windkraftanlagen hinzufuegen ---
        for (String[] row : rows)
        {
            int ID = DataCellParser.parseObjectID(row[GeneralConstants.COLUMN_INDEX_ID]);
            Year manufactureYear = DataCellParser.parseManufactureYear(row[GeneralConstants.COLUMN_INDEX_MANUFACTURE_YEAR]);

            Location location = new Location(
                    DataCellParser.parseTown(row[GeneralConstants.COLUMN_INDEX_TOWN]),
                    DataCellParser.parseDistrict(row[GeneralConstants.COLUMN_INDEX_DISTRICT]));
            String remarks = DataCellParser.parseRemarks(row[GeneralConstants.COLUMN_INDEX_REMARKS]);

            WindTurbineGroup currentTurbineGroup = new WindTurbineGroup(ID, manufactureYear, location, remarks);

            // --- Windturbine hinzufuegen ---
            Map<String, Integer> turbineTypeMap = DataCellParser.parseWindTurbineType(row[GeneralConstants.COLUMN_INDEX_WINDTURBINE_NAME]);

            for (String windTurbineTypeName : turbineTypeMap.keySet())
            {
                for (int i = 0; i < turbineTypeMap.get(windTurbineTypeName); i++)
                {
                    currentTurbineGroup.addWindTurbine(new WindTurbineType(windTurbineTypeName));
                }
            }

            currentWindFarm.addWindTurbineGroup(currentTurbineGroup);
        }

        return currentWindFarm;
    }
}
