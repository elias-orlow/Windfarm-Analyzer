package org.elias.model;

import org.elias.util.DataCellParser; //TODO: Maybe with *

import java.util.List;
import java.util.Map;

public class WindFarmFactory
{
    public static WindFarm createWindFarm (List<String[]> rows)
    {
        String[] currentRow = rows.getFirst();

        String windFarmName = DataCellParser.parseWindFarmName(currentRow[1]); //TODO: validator for some cell
        float[] coordinates = DataCellParser.parseCoordinates(currentRow[8], currentRow[9]); // TODO: Constants

        Coordinates windFarmCoordinates = new Coordinates(coordinates[0], coordinates[1]);
        float totalPerformance = DataCellParser.parseTotalPerformance(currentRow[3]);
        WindFarm result = new WindFarm(windFarmName, totalPerformance, windFarmCoordinates);

        for (String companyName : DataCellParser.parseProjectManager(currentRow[10])){
            result.addProjectManager(new ProjectManager(companyName));
        }



        for (String[] row : rows)
        {
            int ID = DataCellParser.parseObjectID(row[0]);
            int manufactureYear = DataCellParser.parseManufactureYear(row[2]);
            Location location = new Location(DataCellParser.parseTown(row[6]), DataCellParser.parseDistrict(row[7]));
            String remarks = DataCellParser.parseRemarks(row[11]);

            WindTurbineGroup currentTurbineGroup = new WindTurbineGroup(ID, manufactureYear, location, remarks);

            Map<String, Integer> turbineTypeMap = DataCellParser.parseWindTurbineType(row[5]);

            for (String windTurbineTypeName : turbineTypeMap.keySet()){
                for (int i = 0; i < turbineTypeMap.get(windTurbineTypeName); i++){
                    currentTurbineGroup.addWindTurbine(new WindTurbineType(windTurbineTypeName));
                }
            }

            result.addWindTurbineGroup(currentTurbineGroup);
        }

        return result;
    }
}
