package org.elias.control;

import org.elias.model.*;
import org.elias.res.Districts;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.view.TablePrinter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableController //TODO: REFACTOR
{
    private static TableController INSTANCE = null;
    /**
     * Die mit dem Controller verbundener {@link TablePrinter}.
     */
    private final TablePrinter tablePrinter;

    private TableController (TablePrinter tablePrinter)
    {
        this.tablePrinter = tablePrinter;
    }

    public static TableController getInstance ()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException(ErrorMessages.TABLE_CONTROLLER_NOT_INITIALIZED);
        }
        return INSTANCE;
    }

    public static TableController getInstance (TablePrinter tablePrinter)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TableController(tablePrinter);
            return INSTANCE;
        } else
        {
            throw new IllegalStateException(ErrorMessages.TABLE_CONTROLLER_ALREADY_INITIALIZED);
        }
    }


    public void printRepository (WindFarmRepository repository)
    {
        List<WindFarm> farms = repository.getGermanWindFarms();

        if (farms.isEmpty())
        {
            tablePrinter.printError(ErrorMessages.NO_WINDFARM);
            return;
        }

        for (WindFarm farm : farms)
        {
            tablePrinter.printUpperSeparator(ViewConstants.TOTAL_TABLE_WIDTH);

            printFarmHeader(farm);
            printProjectManagers(farm);

            tablePrinter.printInnerSeparator(ViewConstants.TOTAL_TABLE_WIDTH);

            printGroups(farm);

            tablePrinter.printLowerSeparator(ViewConstants.TOTAL_TABLE_WIDTH);
            tablePrinter.makeSpace(ViewConstants.ROWS_BETWEEN_FARMS);
        }

    }


    private void printFarmHeader (WindFarm farm)
    {
        String name = addLeadAndTrailSpaces(farm.getName());
        String perf = addLeadAndTrailSpaces(formatPerformance(farm.getTotalPerformance()));
        String coords = addLeadAndTrailSpaces(formatCoordinates(farm.getCoordinates()));

        tablePrinter.printDataCell(padRight(name, ViewConstants.TOTAL_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
        tablePrinter.printDataCell(padRight(
                String.format(ViewConstants.PERFORMANCE_COORDINATES_ROW, perf, coords),
                ViewConstants.TOTAL_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
    }


    private String formatPerformance (float perfMw)
    {
        if (perfMw == GeneralConstants.EMPTY_FLOAT_VARIABLE)
        {
            return ViewConstants.NOT_SPECIFIED;
        } else
        {
            return String.format(ViewConstants.PERFORMANCE_FORMAT, perfMw);
        }
    }


    private String formatCoordinates (Coordinates c)
    {
        return String.format(ViewConstants.COORDINATES_FORMAT, c.getLatitude(), c.getLongitude());
    }


    private void printProjectManagers (WindFarm farm)
    {
        List<ProjectManager> managers = farm.getProjectManagers();
        if (managers.isEmpty())
        {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(addLeadAndTrailSpaces(ViewConstants.MANAGER_ROW));

        for (int i = 0; i < managers.size(); i++)
        {
            if (i > GeneralConstants.INT_ZERO)
            {
                sb.append(GeneralConstants.CHAR_COMMA + GeneralConstants.CHAR_SPACE);
            }
            sb.append(managers.get(i).getCompany());
        }

        tablePrinter.printDataCell(padRight(sb.toString(), ViewConstants.TOTAL_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
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
            String id = ViewConstants.ID_CELL + currentGroup.getID();

            int dataYear = currentGroup.getManufactureYear();
            String year = (dataYear == GeneralConstants.EMPTY_INT_VARIABLE) ? ViewConstants.NOT_SPECIFIED : String.valueOf(dataYear);

            Location loc = currentGroup.getLocation();
            String town = loc.getTown();
            Districts dist = loc.getDistrict();
            String location = GeneralConstants.EMPTY_STRING;

            if (!town.isEmpty() && dist != null)
            {
                location = town + GeneralConstants.CHAR_COMMA + GeneralConstants.CHAR_SPACE + dist;
            } else if (!town.isEmpty())
            {
                location = town;
            }

            ArrayList<String> typesLine = turbineTypesToString(currentGroup);

            if (typesLine.isEmpty())
            {
                typesLine.add(GeneralConstants.EMPTY_STRING);
            }

            for (String type : typesLine)
            {
                String col1 = padRight(addLeadAndTrailSpaces(id), ViewConstants.COL_ID);
                String col2 = padRight(addLeadAndTrailSpaces(year), ViewConstants.COL_YEAR);
                String col3 = padRight(addLeadAndTrailSpaces(location), ViewConstants.COL_LOCATION);
                String col4 = padRight(addLeadAndTrailSpaces(type), ViewConstants.COL_TYPES);

                tablePrinter.printDataCell(col1 + ViewConstants.VERTICAL_BAR + col2 + ViewConstants.VERTICAL_BAR + col3 + ViewConstants.VERTICAL_BAR + col4);
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
            counts.put(name, counts.getOrDefault(name, GeneralConstants.INT_ZERO) + GeneralConstants.INT_ONE);
        }

        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;

        for (String windTurbine : counts.keySet())
        {
            sb = new StringBuilder(String.format(ViewConstants.WINDTURBINE_FORMAT, windTurbine, counts.get(windTurbine)));
            result.add(sb.toString());
        }

        return result;
    }


    private String addLeadAndTrailSpaces (String string)
    {
        return GeneralConstants.CHAR_SPACE + string + GeneralConstants.CHAR_SPACE;
    }


    private String padRight (String s, int width)
    {
        if (s == null)
        {
            s = GeneralConstants.EMPTY_STRING;
        }
        if (s.length() > width)
        {
            return s.substring(GeneralConstants.INT_ZERO, width -ViewConstants.ELLIPSIS_LENGTH) + ViewConstants.ELLIPSIS;
        }

        return s + Character.toString(GeneralConstants.CHAR_SPACE).repeat(width - s.length());
    }
}
