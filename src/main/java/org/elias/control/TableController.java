package org.elias.control;

import org.elias.model.*;
import org.elias.res.Districts;
import org.elias.res.constant.GeneralConstants;
import org.elias.view.TablePrinter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableController
{
    private final TablePrinter tablePrinter;

    private static final int TOTAL_WIDTH = 100;
    private static final int ROWS_BETWEEN_FARMS = 1;
    private static final int COL_ID = 10;
    private static final int COL_YEAR = 10;
    private static final int COL_LOCATION = 22;
    private static final int COL_TYPES = TOTAL_WIDTH - 5 - COL_ID - COL_YEAR - COL_LOCATION;

    public TableController (TablePrinter tablePrinter)
    {
        this.tablePrinter = tablePrinter;
    }

    public void printRepository (WindFarmRepository repository)
    {
        List<WindFarm> farms = repository.getGermanWindFarms();
        if (farms.isEmpty())
        {
            System.out.println("Error: No Wind Farms found");
            return;
        }

        for (WindFarm farm : farms)
        {
            tablePrinter.printUpperSeparator(TOTAL_WIDTH);
            printFarmHeader(farm);
            printProjectManagers(farm);
            tablePrinter.printInnerSeparator(TOTAL_WIDTH);
            printGroups(farm);
            tablePrinter.printLowerSeparator(TOTAL_WIDTH);
            tablePrinter.makeSpace(ROWS_BETWEEN_FARMS);
        }

    }

    private void printFarmHeader (WindFarm farm)
    {
        String name = " " + farm.getName() + " ";
        String perf = " " + formatPerformance(farm.getTotalPerformance()) + " ";
        String coords = " " + formatCoordinates(farm.getCoordinates()) + " ";

        tablePrinter.printDataCell(padRight(name, TOTAL_WIDTH - 2));
        tablePrinter.printDataCell(padRight(" Performance: " + perf + "  |  Coordinates: " + coords, TOTAL_WIDTH - 2));

    }

    private String formatPerformance (float perfMw)
    {
        if (perfMw == 0.0f || perfMw == -1.0f)
        {
            return "";
        } else
        {
            return String.format("%.1f MW", perfMw);
        }
    }

    private String formatCoordinates (Coordinates c)
    {
        return String.format("%.4f - %.4f", c.getLatitude(), c.getLongitude());
    }

    private void printProjectManagers (WindFarm farm)
    {
        List<ProjectManager> managers = farm.getProjectManagers();
        if (managers.isEmpty())
        {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" Company-Manager: ");
        for (int i = 0; i < managers.size(); i++)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(managers.get(i).getCompany());
        }

        tablePrinter.printDataCell(padRight(sb.toString(), TOTAL_WIDTH - 2));
    }

    private void printGroups (WindFarm farm)
    {
        List<WindTurbineGroup> groups = farm.getWindTurbineGroups();
        if (groups.isEmpty())
        {
            return;
        }

        for (WindTurbineGroup currentGroup : groups)
        {
            String id = "ID " + currentGroup.getID();
            int dataYear = currentGroup.getManufactureYear();
            String year = (dataYear == -1 || dataYear == 0) ? "???" : String.valueOf(dataYear);
            Location loc = currentGroup.getLocation();
            String town = loc.getTown();
            Districts dist = loc.getDistrict();

            String location = GeneralConstants.EMPTY_STRING;
            if (!town.isEmpty() && dist != null)
            {
                location = town + ", " + dist;
            } else if (!town.isEmpty())
            {
                location = town;
            }

            ArrayList<String> typesLine = turbineTypesToString(currentGroup);

            if (typesLine.isEmpty())
            {
                typesLine.add("");
            }

            for (String string : typesLine)
            {
                String col1 = padRight(" " + id + " ", COL_ID);
                String col2 = padRight(" " + year + " ", COL_YEAR);
                String col3 = padRight(" " + location + " ", COL_LOCATION);
                String col4 = padRight(" " + string + " ", COL_TYPES);

                tablePrinter.printDataCell(col1 + "|" + col2 + "|" + col3 + "|" + col4);
                id = GeneralConstants.EMPTY_STRING;
                year = GeneralConstants.EMPTY_STRING;
                location = GeneralConstants.EMPTY_STRING;
            }

        }
    }

    private ArrayList<String> turbineTypesToString (WindTurbineGroup group)
    {
        List<WindTurbineType> turbines = group.getWindTurbines();
        Map<String, Integer> counts = new LinkedHashMap<>();

        for (WindTurbineType turbine : turbines)
        {
            String name = turbine.getModel();
            counts.put(name, counts.getOrDefault(name, 0) + 1);
        }

        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;

        for (String windTurbine : counts.keySet())
        {
            sb = new StringBuilder(windTurbine + "(" + counts.get(windTurbine) + "x)"); 
            result.add(sb.toString());
        }

        return result;
    }


    private String padRight (String s, int width)
    {
        if (s == null)
        {
            s = GeneralConstants.EMPTY_STRING;
        }
        if (s.length() > width)
        {
            return s.substring(0, width - 3) + "...";
        }

        return s + " ".repeat(width - s.length());
    }

}
