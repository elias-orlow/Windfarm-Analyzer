package org.elias.util;

import java.util.ArrayList;
import java.util.List;

public class CSVLineParser
{

    /**
     * Zerlegt eine Liste aus CSV-Zeilen in einzelne Datenfelder
     * <p>
     * Jede Zeile wird zeichenweise analysiert und in Spalten aufgeteilt.
     * Dabei werden Kommas innerhalb von Anführungszeichen sowie Kommas,
     * denen ein Leerzeichen folgt, nicht als Trennzeichen interpretiert.
     *
     * @param dataRows Liste von Zeilen aus einer CSV-Datei
     * @return Liste von String-Arrays, wo jedes Array die einzelnen Zellen einer CSV-Zeile enthält
     * @precondition dataRows != null
     * @postcondition Für jede übergebene CSV-Zeile existiert genau ein String-Array im Rückgabewert, die Reihenfolge der Zeilen bleibt erhalten
     */
    public static List<String[]> convertToDataUnit (List<String> dataRows)
    {

        final int COLUMN_COUNT = 12;
        List<String[]> result = new ArrayList<>();

        for (String row : dataRows)
        {

            String[] cells = new String[COLUMN_COUNT];
            StringBuilder currentCell = new StringBuilder();

            boolean insideQuotes = false;
            int cellIndex = 0;

            for (int i = 0; i < row.length(); i++)
            {

                char currentChar = row.charAt(i);

                if (currentChar == '"')
                {
                    insideQuotes = !insideQuotes;
                    continue;
                }

                if (currentChar == ',' && !insideQuotes)
                {

                    if (i + 1 < row.length() && row.charAt(i + 1) == ' ')
                    {
                        currentCell.append(currentChar);
                    } else
                    {
                        cells[cellIndex++] = currentCell.toString().trim();
                        currentCell.setLength(0);

                        if (cellIndex >= COLUMN_COUNT)
                        {
                            break;
                        }
                    }
                    continue;
                }

                currentCell.append(currentChar);
            }

            if (cellIndex < COLUMN_COUNT)
            {
                cells[cellIndex] = currentCell.toString().trim();
            }

            result.add(cells);
        }

        return result;
    }

}
