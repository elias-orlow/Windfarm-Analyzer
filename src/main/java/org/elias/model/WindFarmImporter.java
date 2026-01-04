package org.elias.model;

import org.elias.res.constant.GeneralConstants;
import org.elias.util.DataCellParser;

import java.util.*;

/**
 * Importiert Daten aus einer CSV-Zellenliste in {@link WindFarmRepository}.
 */
public class WindFarmImporter
{
    /**
     * Startet den Import der CSV-Daten.
     *
     * @param csvDataCells nach dem {@link org.elias.util.CSVLineParser#convertToDataUnit(List)} erzeugte
     *                     {@code List<String[]>} die nach Zellen zerlegte Liste aus Zeilen repraesentiert.
     * @param repository   zentrales Repository fuer Speicherung der Daten.
     * @precondition Liste wurde korrekt nach Zellen von {@link org.elias.util.CSVLineParser} zerlegt und enthaelt
     * gueltige Eingaben. Es gibt eine Instanz von {@link WindFarmRepository}.
     * @postcondition {@link WindFarmRepository} wird mit den {@code WindFarm} gefüllt.
     */
    public static void importData (List<String[]> csvDataCells, WindFarmRepository repository)
    {
        List<String[]> currentGroup = new ArrayList<>();
        String currentWindFarmName = GeneralConstants.EMPTY_STRING;

        for (String[] row : csvDataCells)
        {
            String windFarmName = DataCellParser.parseWindFarmName(row[GeneralConstants.COLUMN_INDEX_NAME]);

            // Erste Zeile initialisiert den Namen des aktuellen Windparks.
            if (currentWindFarmName.equals(GeneralConstants.EMPTY_STRING))
            {
                currentWindFarmName = windFarmName;
            }

            // Falls neuer WindFarm-Block beginnt
            if (!windFarmName.equals(currentWindFarmName))
            {
                createAndStoreWindFarm(currentGroup, repository);

                currentGroup = new ArrayList<>();
                currentWindFarmName = windFarmName;
            }

            currentGroup.add(row);
        }


        if (!currentGroup.isEmpty())
        {
            createAndStoreWindFarm(currentGroup, repository);
        }
    }

    /**
     * Erzeugt aus einer Gruppe von CSV-Zeilen einen {@link WindFarm} und speichert es im Repository.
     *
     * @param rows       CSV-Zeilen, die zu einem Windpark gehoeren.
     * @param repository zentrales Repository fuer Speicherung der Daten.
     * @precondition Es gibt eine Instanz von {@link WindFarmRepository}. Die Zeilen wurden korrekt sortiert und gehoeren
     * alle zum selben Windpark.
     * @postcondition {@link WindFarmRepository} wird mit einer {@code WindFarm} gefüllt. Bei ungueltigen Eingaben wird
     * die Zeile mit der entsprechenden Fehlermeldung zur {@link WindFarmRepository#invalidRows} hinzufuegt.
     */
    private static void createAndStoreWindFarm (List<String[]> rows, WindFarmRepository repository)
    {
        try
        {
            WindFarm windFarm = WindFarmFactory.createWindFarm(rows);
            repository.addWindFarm(windFarm);
        } catch (Exception e)
        {
            for (String[] invalidRow : rows)
            {
                repository.addInvalidRow(invalidRow, e.getMessage());
            }
        }
    }

}
