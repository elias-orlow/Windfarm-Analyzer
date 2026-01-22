package org.elias.util;

import org.elias.res.constant.GeneralConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Util-Klasse zur Zerlegung jeder CSV-Zeile aus {@link CSVFileReader} in eine Liste aus einzelnen Zellen.
 */
public class CSVLineParser
{
    /**
     * Zerlegt eine Liste aus CSV-Zeilen in einzelne Datenfelder
     * <p>
     * Jede Zeile wird zeichenweise analysiert und in Spalten aufgeteilt.
     * Dabei werden Kommas innerhalb von Anführungszeichen sowie Kommas,
     * denen ein Leerzeichen folgt, nicht als Trennzeichen interpretiert.
     *
     * @param dataRows Liste von Zeilen aus einer CSV-Datei.
     * @return Liste von String-Arrays, wo jedes Array die einzelnen Zellen einer CSV-Zeile enthaelt.
     * @precondition {@code dataRows} ist nicht null.
     * @postcondition Für jede uebergebene CSV-Zeile existiert genau.
     * ein String-Array im Rueckgabewert, die Reihenfolge der Zeilen bleibt erhalten.
     */
    public static List<String[]> convertToDataUnit (List<String> dataRows)
    {
        List<String[]> result = new ArrayList<>();

        for (String row : dataRows)
        {
            String[] cells = new String[GeneralConstants.COLUMN_COUNT];
            StringBuilder currentCell = new StringBuilder();

            boolean insideQuotes = false;
            int cellIndex = GeneralConstants.INT_ZERO;

            for (int i = 0; i < row.length(); i++)
            {
                char currentChar = row.charAt(i);

                if (currentChar == GeneralConstants.CHAR_DOUBLE_QUOTES)
                {
                    insideQuotes = !insideQuotes;
                    continue;
                }

                // Kommas als Spaltentrenner nur außerhalb von Anfuehrungszeichen
                if (currentChar == GeneralConstants.CHAR_COMMA && !insideQuotes)
                {
                    // Wenn nach dem Komma ein Leerzeichen folgt → Teil des Inhalts der Zelle
                    if (i + GeneralConstants.INT_ONE < row.length()
                            && row.charAt(i + GeneralConstants.INT_ONE) == GeneralConstants.CHAR_SPACE)
                    {
                        currentCell.append(currentChar);
                    } else
                    {
                        cells[cellIndex++] = currentCell.toString().trim();
                        currentCell.setLength(GeneralConstants.INT_ZERO);

                        if (cellIndex >= GeneralConstants.COLUMN_COUNT)
                        {
                            break;
                        }
                    }
                    continue;
                }

                // Normales Zeichen → zur aktuellen Zelle addieren
                currentCell.append(currentChar);
            }

            if (cellIndex < GeneralConstants.COLUMN_COUNT)
            {
                cells[cellIndex] = currentCell.toString().trim();
            }

            result.add(cells);
        }

        return result;
    }

}
