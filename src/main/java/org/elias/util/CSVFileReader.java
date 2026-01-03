package org.elias.util;

import org.elias.res.constant.ErrorMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Util-Klasse zum Einlesen einer CSV-Datei und Umwandeln in eine Liste von Zeilen.
 */
public class CSVFileReader
{
    /**
     * Liest eine CSV-Datei zeilenweise ein und gibt alle Datenzeilen als Liste zurueck.
     * <p>
     * Die erste Zeile (Header) wird ignoriert.
     *
     * @param pathToCSV Pfad zur CSV-Datei.
     * @return Eine Liste aller Datenzeilen der CSV-Datei ohne Header.
     * @precondition Der angegebene Pfad verweist auf eine existierende gueltige Datei.
     * @postcondition bei Fehlern wird eine leere Liste geliefert.
     */
    public static List<String> convertCSVtoList (String pathToCSV)
    {
        List<String> dataRows = new ArrayList<>();

        try (BufferedReader input = new BufferedReader(new FileReader(pathToCSV)))
        {
            // Erste Zeile (Header) ueberspringen
            String dataLine = input.readLine();

            while ((dataLine = input.readLine()) != null)
            {
                dataRows.add(dataLine);
            }

        } catch (IOException e)
        {
            System.err.println(ErrorMessages.ERROR_READING_CSV + e.getMessage());
        }

        return dataRows;
    }

}
