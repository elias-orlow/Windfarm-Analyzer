package org.elias.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader
{
    public static List<String> convertCSVtoList (String pathToCSV)
    {
        List<String> dataRows = new ArrayList<>();

        try (BufferedReader input = new BufferedReader(new FileReader(pathToCSV)))
        {
            String dataLine;

            while ((dataLine = input.readLine()) != null)
            {
                dataRows.add(dataLine);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return dataRows;
    }

}
