package org.elias.model;

import org.elias.res.constant.GeneralConstants;
import org.elias.util.DataCellParser;

import java.util.*;

public class WindFarmImporter {

    public static void importData(List<String[]> csvDataCells,
                                  WindFarmRepository repository) {

        List<String[]> currentGroup = new ArrayList<>();
        String currentWindFarmName = GeneralConstants.EMPTY_STRING;

        for (String[] row : csvDataCells) {

            String windFarmName = DataCellParser.parseWindFarmName(row[1]);

            // Erste Zeile
            if (currentWindFarmName.equals(GeneralConstants.EMPTY_STRING)) {
                currentWindFarmName = windFarmName;
            }

            // Neuer WindFarm-Block beginnt
            if (!windFarmName.equals(currentWindFarmName)) {

                // alte Gruppe abschließen
                createAndStoreWindFarm(currentGroup, repository);

                // neue Gruppe starten
                currentGroup = new ArrayList<>();
                currentWindFarmName = windFarmName;
            }

            currentGroup.add(row);
        }

        // letzte Gruppe nicht vergessen
        if (!currentGroup.isEmpty()) {
            createAndStoreWindFarm(currentGroup, repository);
        }
    }

    private static void createAndStoreWindFarm(List<String[]> rows,
                                               WindFarmRepository repository) {
        try {
            WindFarm windFarm = WindFarmFactory.createWindFarm(rows);
            repository.addWindFarm(windFarm);
        } catch (Exception e) {

            String[] faultyRow = rows.getFirst();
            int id = -1;

            try {
                id = DataCellParser.parseObjectID(faultyRow[0]);
            } catch (Exception ignored) {}

            System.err.println(
                    "Error while importing wind farm (ID: " + id + "): " + e.getMessage()
            );
        }
    }

}
