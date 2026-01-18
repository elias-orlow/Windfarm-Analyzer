package org.elias.control;

import org.elias.model.*;
import org.elias.res.Districts;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.view.TablePrinter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Control-Klasse zur Steuerung der Tabellenausgaben.
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und es existiert genau eine einzige Instanz davon.
 */
public class TableController
{
    private static TableController INSTANCE = null;
    /**
     * Die mit dem Controller verbundener {@link TablePrinter}.
     */
    private final TablePrinter tablePrinter;

    /**
     * Erzeugt eine neue Instanz des {@link TableController}
     *
     * @param tablePrinter der {@link TablePrinter}, der fuer die Ausgabe der Tabellen verantwortlich ist.
     * @precondition {@code tablePrinter} ist nicht null.
     * @postcondition es wird eine Instanz vom Kontroller mit View als {@code TablePrinter} erstellt.
     */
    private TableController (TablePrinter tablePrinter)
    {
        this.tablePrinter = tablePrinter;
    }


    /**
     * Liefert die {@code TableController}-Instanz.
     *
     * @return die initialisierte {@code TableController}-Instanz.
     * @throws IllegalStateException wenn der Controller noch nicht
     *                               mit {@link #getInstance(TablePrinter)} initialisiert wurde.
     * @precondition {@link #getInstance(TablePrinter)} wurde zuvor aufgerufen.
     */
    public static TableController getInstance ()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException(ErrorMessages.TABLE_CONTROLLER_NOT_INITIALIZED);
        }
        return INSTANCE;
    }


    /**
     * Initialisiert den {@code TableController} mit einem {@link TablePrinter} und liefert die erzeugte Instanz.
     * <p>
     * Diese Methode darf nur einmal aufgerufen werden.
     *
     * @param tablePrinter der {@link TablePrinter}, der vom Controller verwendet wird.
     * @return die neu erzeugte {@code TableController}-Instanz.
     * @throws IllegalStateException wenn der Controller bereits initialisiert wurde.
     * @precondition {@code tablePrinter} ist nicht null.
     * @postcondition {@link #getInstance()} gibt dieselbe Instanz zurueck.
     */
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


    /**
     * Gibt alle Windparks eines {@link WindFarmRepository} als Tabelle aus.
     *
     * <p>
     * Fuer jeden {@link WindFarm} wird eine eigene Tabelle erzeugt, bestehend aus:
     * <ul>
     *   <li>Kopfbereich (Name, Gesamtleistung, Koordinaten)</li>
     *   <li>Projektleitern (falls vorhanden)</li>
     *   <li>Gruppenuebersicht der Windkraftanlagen</li>
     * </ul>
     * <p>
     * Ist das Repository leer, wird eine entsprechende Fehlermeldung ausgegeben.
     *
     * @param repository das {@code WindFarmRepository}, dessen Inhalte ausgegeben werden sollen.
     * @precondition {@code repository} ist nicht leer.
     * @postcondition die Daten wurden an {@code tablePrinter} uebergeben.
     */
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
            printWindfarm(farm);
        }
    }

    /**
     * Gibt einen einzelnen {@link WindFarm} als vollstaendige Tabelle aus.
     * <p>
     * Die Ausgabe umfasst Kopfbereich, Projektleiter und Anlagenuebersicht.
     *
     * @param farm der auszugebende Windpark.
     * @precondition {@code farm} ist nicht null.
     * @postcondition die Daten des Windparks wurden vollstaendig ueber {@code tablePrinter} ausgegeben.
     */
    public void printWindfarm (WindFarm farm)
    {
        tablePrinter.printUpperSeparator(ViewConstants.TOTAL_TABLE_WIDTH);

        printFarmHeader(farm);
        printProjectManagers(farm);

        tablePrinter.printInnerSeparator(ViewConstants.TOTAL_TABLE_WIDTH);

        printGroups(farm);

        tablePrinter.printLowerSeparator(ViewConstants.TOTAL_TABLE_WIDTH);
        tablePrinter.makeSpace(ViewConstants.ROWS_BETWEEN_FARMS);
    }

    public void printSchedule (Schedule schedule)
    {
        for (WorkDay workDay : schedule.getWorkDays())
        {
            printWorkday(workDay);
            tablePrinter.makeSpace(ViewConstants.ROWS_BETWEEN_DAYS);
        }
    }

    private void printWorkday (WorkDay workDay)
    {
        tablePrinter.printUpperSeparator(ViewConstants.TOTAL_DAYS_TABLE_WIDTH);

        tablePrinter.printDataCell(padRight(String.format("   --- DAY %d ---", workDay.getDayNumber()),
                ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));

        if (!workDay.getMaintainedWindTurbines().isEmpty())
        {
            tablePrinter.printDataCell(padRight(String.format(" Wind farm: %s", workDay.getMaintainedWindFarm().getName()),
                    ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));

            tablePrinter.printDataCell(padRight(" Maintained wind turbines:",
                    ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));

            for (WindTurbineType windTurbineType : workDay.getMaintainedWindTurbines())
            {
                tablePrinter.printDataCell(padRight(String.format("    -  %s", windTurbineType.getModel()),
                        ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
            }
        }


        if (!(workDay.getDriveTime() == null))
        {
            tablePrinter.printInnerSeparator(ViewConstants.TOTAL_DAYS_TABLE_WIDTH);
            tablePrinter.printDataCell(padRight("Driving in the evening:",
                    ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
            tablePrinter.printDataCell(padRight(String.format("    - %s", formatDuration(workDay.getDriveTime())), ViewConstants.TOTAL_DAYS_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
        }

        tablePrinter.printLowerSeparator(ViewConstants.TOTAL_DAYS_TABLE_WIDTH);
    }


    private static String formatDuration(Duration duration) {
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        if (hours > 0 && minutes > 0) {
            return hours + " hour(s) " + minutes + " minute(s)";
        } else if (hours > 0) {
            return hours + " hour(s)";
        } else {
            return minutes + " minute(s)";
        }
    }



    /**
     * Gibt den Kopfbereich eines Windparks aus.
     *
     * <p>
     * Der Kopfbereich enthält:
     * <ul>
     *   <li>den Namen des Windparks</li>
     *   <li>die Gesamtleistung</li>
     *   <li>die Koordinaten</li>
     * </ul>
     *
     * @param farm der {@link WindFarm}, dessen Kopfdaten ausgegeben werden sollen.
     * @precondition {@code farm} ist nicht leer.
     * @postcondition die formatierten Daten werden in {@link TablePrinter#printDataCell(String)} uebergeben.
     */
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


    /**
     * Formatiert die Gesamtleistung eines Windparks.
     *
     * @param perfMw Gesamtleistung in Megawatt.
     * @return formatierter Leistung-String.
     * @precondition die Methode wird von einer anderen aus {@link TableController} aufgerufen.
     * @postcondition es wird ein String in Format {@code "%.1f MW"} zurueckgegeben.
     */
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


    /**
     * Formatiert die Koordinaten eines Windparks.
     *
     * @param coordinates {@link Coordinates} mit Breitengrad und Laengengrad.
     * @return formatierter Koordinaten-String.
     * @precondition die Methode wird von einer anderen aus {@link TableController} aufgerufen.
     * @postcondition es wird ein String in Format {@code "%.4f - %.4f"} zurueckgegeben.
     */
    private String formatCoordinates (Coordinates coordinates)
    {
        return String.format(ViewConstants.COORDINATES_FORMAT, coordinates.getLatitude(), coordinates.getLongitude());
    }


    /**
     * Gibt die Projektleiter in einer Tabellenzeile aus.
     * <p>
     * Die Projektleiter werden mit Kommas separiert.
     *
     * @param farm der {@link WindFarm} dessen Projektleiter ausgegeben werden.
     * @precondition {@code farm} ist nicht null.
     * @postcondition die formatierte Zeile wird ausgegeben, falls ein Projektleiter existiert, sonst keine Zeile.
     */
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
                sb.append(GeneralConstants.CHAR_COMMA).append(GeneralConstants.CHAR_SPACE);
            }
            sb.append(managers.get(i).getCompany());
        }

        tablePrinter.printDataCell(padRight(sb.toString(), ViewConstants.TOTAL_TABLE_WIDTH - ViewConstants.BORDER_OFFSET));
    }


    /**
     * Gibt alle {@link WindTurbineGroup} einer {@link WindFarm} als Tabelle aus.
     * <p>
     * Fuer jede Gruppe wird mindestens eine Zeile ausgegeben. Falls eine Gruppe mehrere Turbinentypen enthaelt,
     * werden sie untereinander dargestellt.
     *
     * @param farm die {@link WindFarm}, deren WindTurbineGruppen ausgegeben werden.
     * @precondition farm ist nicht null.
     * @postcondition die formatierten Daten werden in {@link TablePrinter#printDataCell(String)} uebergeben.
     */
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

            String year = (currentGroup.getManufactureYear().getValue() == GeneralConstants.EMPTY_INT_VARIABLE)
                    ? ViewConstants.NOT_SPECIFIED
                    : String.valueOf(currentGroup.getManufactureYear());

            String location = formatLocation(currentGroup.getLocation());

            ArrayList<String> typesLine = turbineTypesToString(currentGroup);

            // Es wird mindestens eine Zeile ausgegeben, auch wenn es keine Typen gibt.
            if (typesLine.isEmpty())
            {
                typesLine.add(GeneralConstants.EMPTY_STRING);
            }

            for (String type : typesLine)
            {
                String colID = padRight(addLeadAndTrailSpaces(id), ViewConstants.COL_ID);
                String colYear = padRight(addLeadAndTrailSpaces(year), ViewConstants.COL_YEAR);
                String colLocation = padRight(addLeadAndTrailSpaces(location), ViewConstants.COL_LOCATION);
                String colTypes = padRight(addLeadAndTrailSpaces(type), ViewConstants.COL_TYPES);

                tablePrinter.printDataCell(colID + ViewConstants.VERTICAL_BAR + colYear + ViewConstants.VERTICAL_BAR + colLocation + ViewConstants.VERTICAL_BAR + colTypes);

                // Falls es mehr als ein Windkrafttyp pro Zeile vorhanden ist, werden die ohne Angaben
                // von id, year and location ausgegeben, da die logisch zu einer Windkraftanlage gehoeren.
                id = GeneralConstants.EMPTY_STRING;
                year = GeneralConstants.EMPTY_STRING;
                location = GeneralConstants.EMPTY_STRING;
            }

        }
    }


    /**
     * Formatiert eine {@link Location} zu einer Ortsbeschreibung.
     * <p>
     * Formatierungsregeln:
     * <p>
     * <ul>
     *  <li>Sind Town und District vorhanden: {@code "Town, District"}</li>
     *  <li>Ist nur Town vorhanden: {@code "Town"}</li>
     *  <li>Ist Town leer: leerer String</li>
     * </ul>
     *
     * @param location die Location deren Ort und Landkreis formatiert wird.
     * @return formatierter oder leerer String.
     * @precondition {@code location} ist nicht null.
     * @postcondition ein String unter Formatierungsregeln wird zurueckgegeben.
     */
    private String formatLocation (Location location)
    {
        String town = location.getTown();
        Districts dist = location.getDistrict();

        if (!town.isEmpty() && dist != null)
        {
            return town + GeneralConstants.CHAR_COMMA + GeneralConstants.CHAR_SPACE + dist;
        } else if (!town.isEmpty())
        {
            return town;
        }
        return GeneralConstants.EMPTY_STRING;
    }


    /**
     * Erzeugt eine formatierte String-Liste von Windturbinen und ihrer Anzahl in {@link WindTurbineGroup}.
     *
     * <p>
     * Jeder Windturbinen-Typ wird anhand seines Namens gruppiert und gezaehlt.
     * Die Rückgabe erfolgt in der Reihenfolge des ersten Auftretens der Typen (durch Verwendung einer {@link LinkedHashMap}).
     * <p>
     * - Beispiel:
     * <p>
     * Eingabe - {Nordex N62/1300, Nordex N62/1300, Südwind S70}
     * <p>
     * Ausgabe - {"Nordex N62/1300 (2x)", "Südwind S70 (1x)"}
     *
     * @param group die {@code WindTurbineGroup}, deren Turbinen zurueckgegeben werden.
     * @return eine {@code ArrayList<String>} mit formatierten Eintraegen pro Windturbinen-Typ und dessen Anzahl
     * @precondition {@code group} ist nicht null
     * @postcondition Die zurueckgegebene Liste enthaelt fuer jeden unterschiedlichen
     * Windturbinen-Typ genau einen Eintrag.
     */
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

        for (String windTurbine : counts.keySet())
        {
            result.add(String.format(ViewConstants.WINDTURBINE_FORMAT, windTurbine, counts.get(windTurbine)));
        }

        return result;
    }


    /**
     * Fuegt vor und nach dem gegebenen String ein Leerzeichen hinzu.
     *
     * @param string der Eingabestring.
     * @return ein neuer String mit fuehrendem und nachgestelltem Leerzeichen.
     * @precondition die Methode wird von einer anderen aus {@link TableController} aufgerufen.
     * @postcondition die Laenge des neuen String ist um zwei Zeichen groesser als des Eingabestrings.
     */
    private String addLeadAndTrailSpaces (String string)
    {
        return GeneralConstants.CHAR_SPACE + string + GeneralConstants.CHAR_SPACE;
    }


    /**
     * Formatiert String auf eine feste Breite.
     * <p>
     * Falls String zu lang ist - wird String mit Auslassungspunkten gekuerzt.
     * Falls String zu kurz ist - werden rechts Leerzeihen angefuegt.
     *
     * @param string Zeile zum Formatieren.
     * @param width  Zielbreite des zurueckgegebenen String.
     * @return ein {@code String} mit genau {@code width} Zeichen.
     * @precondition {@code width} ist mehr als {@link ViewConstants#ELLIPSIS_LENGTH}
     * @postcondition der String ist entweder aufgefuellt oder gekuerzt.
     */
    private String padRight (String string, int width)
    {
        if (string == null)
        {
            string = GeneralConstants.EMPTY_STRING;
        }
        if (string.length() > width)
        {
            return string.substring(GeneralConstants.INT_ZERO, width - ViewConstants.ELLIPSIS_LENGTH) + ViewConstants.ELLIPSIS;
        }

        return string + Character.toString(GeneralConstants.CHAR_SPACE).repeat(width - string.length());
    }
}
