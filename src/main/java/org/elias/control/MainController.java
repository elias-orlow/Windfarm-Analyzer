package org.elias.control;

import org.elias.Main;
import org.elias.model.*;
import org.elias.model.sort.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.CSVFileReader;
import org.elias.util.CSVLineParser;
import org.elias.util.CoordinatesNormalizer;
import org.elias.util.Timer;
import org.elias.view.ConsoleView;
import org.elias.view.TablePrinter;

import java.util.*;

/**
 * Singleton main Controller für den Hauptablauf der Anwendung.
 * Verwaltet die Interaktion mit der ConsoleView.
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und muss vor der Verwendung
 * mit {@link #init(ConsoleView, WindFarmRepository)} initialisiert werden.
 */
public final class MainController
{
    private static MainController INSTANCE = null;

    /**
     * Die mit dem Controller verbundene {@link ConsoleView}.
     */
    private final ConsoleView view;
    private final WindFarmRepository germanWindFarms;
    int normalizedCoordinatesCounter = GeneralConstants.EMPTY_INT_VARIABLE;


    /**
     * Erzeugt eine neue Instanz des {@link MainController}.
     *
     * @param view            die {@link ConsoleView}, die fuer die Ein- und Ausgabe der Anwendung verwendet wird.
     *                        Darf nicht {@code null} sein.
     * @param germanWindFarms das {@link WindFarmRepository}, das die verwalteten Windparkdaten enthaelt.
     *                        Darf nicht {@code null} sein.
     * @precondition {@code view != null && germanWindFarms != null}
     * @postcondition {@code this.view == view && this.germanWindFarms == germanWindFarms}
     */
    private MainController (ConsoleView view, WindFarmRepository germanWindFarms)
    {
        this.view = view;
        this.germanWindFarms = germanWindFarms;
    }


    /**
     * Gibt die Singleton-Instanz zurück.
     *
     * @return die existierende MainController-Instanz
     * @throws IllegalStateException wenn die Instanz noch nicht initialisiert wurde
     * @precondition {@code init(ConsoleView view)} wurde vorher aufgerufen
     * @postcondition die gleiche Singleton-Instanz wird zurückgegeben
     */
    public static MainController getInstance ()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException(ErrorMessages.MAIN_CONTROLLER_NOT_INITIALIZED);
        }
        return INSTANCE;
    }


    /**
     * Initialisiert den {@code MainController} mit einer {@code ConsoleView}.
     * Muss vor {@code getInstance()} aufgerufen werden.
     *
     * @param view die zu MainController verbindende ConsoleView
     * @throws IllegalStateException wenn die Instanz bereits initialisiert wurde
     * @precondition view ist nicht leer
     * @postcondition {@link #INSTANCE} wird auf eine neue MainController-Instanz gesetzt
     */
    public static void init (ConsoleView view, WindFarmRepository germanWindFarms)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new MainController(view, germanWindFarms);
        } else
        {
            throw new IllegalStateException(ErrorMessages.MAIN_CONTROLLER_ALREADY_INITIALIZED);
        }
    }


    /**
     * Startet das Program.
     * <p>
     * Ablauf:
     * <ol>
     *     <li>Begruessung ausgeben.</li>
     *     <li>Daten vorbereiten.</li>
     *     <li>Koordinaten normalisieren.</li>
     *     <li>Hauptloop aufrufen.</li>
     * </ol>
     *
     * @precondition diese Methode wird nur einmal in {@link Main} aufgerufen.
     * @postcondition die Datenvorbereitung zum Programmablauf wurde durchgefuehrt und
     * der Hauptzyklus wird gestartet.
     */
    public void start ()
    {
        view.printMessage(ViewConstants.WELCOME_MESSAGE);

        calculateRuntime(this::setupData);

        waitForEnter();

        calculateRuntime(this::validateCoordinates);

        waitForEnter();

        programLoop();
    }


    /**
     * Liest die CSV-Datei ein, wandelt die Zellen aus Zeilen um un importiert die Daten
     * in das {@link WindFarmRepository}.
     *
     * @precondition der Pfad {@code GeneralConstants.PATH_TO_CSV} zur CSV-Datei ist korrekt und die Daten sind lesbar.
     * @postcondition das Repository {@code germanWindFarm} enthaelt WindFarms aus CSV-Tabelle.
     */
    private void setupData ()
    {
        List<String> dataRows = CSVFileReader.convertCSVtoList(GeneralConstants.PATH_TO_CSV);
        List<String[]> CSVDataCells = CSVLineParser.convertToDataUnit(dataRows);
        WindFarmImporter.importData(CSVDataCells, germanWindFarms);

        calculateData();
    }


    /**
     * Fuehrt eine Aufgabe aus und misst ihre Laufzeit.
     *
     * @param task eine Methode, deren Laufzeit gemessen wird.
     * @precondition {@code task} ist nicht null.
     * @postcondition Es wird die Laufzeit der gegebenen Methode in der Konsole aufgegeben.
     */
    private void calculateRuntime (Runnable task)
    {
        Timer.startTimer();
        task.run();
        view.printMessage(String.format(ViewConstants.TIME_MESSAGE, Timer.getTime()));
    }


    /**
     * Berechnet statistische Information ueber die importierten Daten.
     * <p>
     * Es wird die Anzahl der erfolgreich importierten Windparks,
     * sowie die ungueltige Dateneintraege ausgegeben.
     *
     * @precondition {@link WindFarmRepository} wurde bereits initialisiert und aufgefuellt.
     * @postcondition Informationen wurden ueber die {@link ConsoleView} ausgegeben.
     */
    private void calculateData ()
    {
        int windFarmCount = germanWindFarms.getGermanWindFarms().size();
        int invalidRowCount = germanWindFarms.getInvalidRows().size();

        view.printMessage(String.format(ViewConstants.TOTAL_WINDFARM_MESSAGE, windFarmCount));
        view.printMessage(String.format(ViewConstants.TOTAL_INVALID_MESSAGE, invalidRowCount));

        for (String[] failedRow : germanWindFarms.getInvalidRows().keySet())
        {
            view.printMessage(String.format(ViewConstants.FAILED_CAUSE_MESSAGE,
                    failedRow[GeneralConstants.INT_ZERO],
                    failedRow[GeneralConstants.INT_ONE],
                    germanWindFarms.getInvalidRows().get(failedRow)));
        }

        view.makeSpace(GeneralConstants.INT_ONE);
    }


    /**
     * Validiert und normalisiert die Koordinaten aller Windparks.
     * <p>
     * Die Anzahl an geaenderten Koordinaten werden zusammengezaehlt und ausgegeben.
     *
     * @precondition {@link WindFarmRepository} enthaelt einen oder mehrere {@link WindFarm}
     * @postcondition Alle WindFarm-Objekte enthalten normalisierte Koordinaten.
     */
    private void validateCoordinates ()
    {
        for (WindFarm windFarm : germanWindFarms.getGermanWindFarms())
        {
            Coordinates originalCoordinates = windFarm.getCoordinates();
            windFarm.setCoordinates(
                    CoordinatesNormalizer.normalizeCoordinates(
                            windFarm.getCoordinates().getLatitude(),
                            windFarm.getCoordinates().getLongitude()));

            if (!originalCoordinates.equals(windFarm.getCoordinates()))
            {
                normalizedCoordinatesCounter++;
            }
        }

        view.printMessage(String.format(ViewConstants.TOTAL_NORMALIZED_COORDINATE_MESSAGE, normalizedCoordinatesCounter));
    }


    /**
     * Fuehrt die Hauptprogrammschleife aus.
     * <p>
     * Das Menue wird angezeigt, Benutzereingaben verarbeitet und entsprechende Aktionen ausgefuehrt.
     *
     * @precondition View, Repository und Controller sind vollstaendig initialisiert.
     * @postcondition das Programm wird auf die Benutzeraktionen ordnungsgemaess reagieren.
     */
    private void programLoop ()
    {
        boolean isRunning = true;

        TableController tableController = TableController.getInstance(TablePrinter.getInstance());

        while (isRunning)
        {
            view.showMenu();
            int userChoice = view.getChoice();

            switch (userChoice)
            {
                case ViewConstants.SHOW_WINDPARKS:
                    view.printCSV(CSVFileReader.convertCSVtoList(GeneralConstants.PATH_TO_CSV));
                    break;
                case ViewConstants.PRINT_WINDREPO:
                    tableController.printRepository(germanWindFarms);
                    break;
                case ViewConstants.SORT_WINDREPO:
                    sortProcess();
                    break;
                case ViewConstants.ANALYSIS_WINDREPO:
                    analysisProcess();
                    break;
                case ViewConstants.EXIT:
                    isRunning = false;
                    view.printMessage(ViewConstants.EXIT_MESSAGE);
                    break;
                default:
                    view.printError(ErrorMessages.INVALID_NUMBER);
            }
        }
    }


    private void sortProcess ()
    {
        view.printMessage(ViewConstants.SORT_MENU_MESSAGE);
        String userChoice = view.getUserInput();

        switch (userChoice)
        {
            case ViewConstants.ID_ASC:
                sortByAndPrint(new IdComparator());
                break;
            case ViewConstants.ID_DESC:
                sortByAndPrint(new IdComparator().reversed());
                break;
            case ViewConstants.TURBINE_AGE_OLD:
                sortByAndPrint(new OldestTurbineCommissioningComparator());
                break;
            case ViewConstants.TURBINE_AGE_LATE:
                sortByAndPrint(new LatestTurbineCommissioningComparator());
                break;
            case ViewConstants.LATITUDE_ASC:
                sortByAndPrint(new LatitudeComparator());
                break;
            case ViewConstants.LATITUDE_DESC:
                sortByAndPrint(new LatitudeComparator().reversed());
                break;
            case ViewConstants.TURBINE_COUNT:
                sortByAndPrint(new WindTurbineCountComparator());
                break;
            case ViewConstants.MANAGER_COUNT:
                sortByAndPrint(new ManagerCountComparator());
                break;
            case ViewConstants.PERFORMANCE_ASC:
                sortByAndPrint(new TotalPerformanceComparator());
                break;
            case ViewConstants.PERFORMANCE_DESC:
                sortByAndPrint(new TotalPerformanceComparator().reversed());
                break;
            case ViewConstants.NAME_AZ:
                sortByAndPrint(new WindFarmNameComparator());
                break;
            case ViewConstants.NAME_ZA:
                sortByAndPrint(new WindFarmNameComparator().reversed());
                break;
            case ViewConstants.AGE_PERFORMANCE:
                sortByAndPrint(new LatestTurbineCommissioningComparator().thenComparing(new TotalPerformanceComparator().reversed()));
                break;
            case ViewConstants.TURBINE_PERFORMANCE:
                sortByAndPrint(new WindTurbineCountComparator().thenComparing(new TotalPerformanceComparator().reversed()));
                break;
            case ViewConstants.TURBINE_MANAGER_COUNT:
                sortByAndPrint(new WindTurbineCountComparator().thenComparing(new ManagerCountComparator()));
                break;
            case ViewConstants.EXIT_STRING:
                break;
            default:
                view.printError(ErrorMessages.INVALID_USER_SORT);
                waitForEnter();
                sortProcess();
        }
    }


    private void sortByAndPrint (Comparator<WindFarm> comparator)
    {
        germanWindFarms.getGermanWindFarms().sort(comparator);
        TableController tableController = TableController.getInstance();
        tableController.printRepository(germanWindFarms);
    }


    private void analysisProcess ()
    {
        view.printMessage(ViewConstants.ANALYSIS_MENU_MESSAGE);

        WindFarmAnalyzer windFarmAnalyzer = new WindFarmAnalyzer(germanWindFarms);
        TableController tableController = TableController.getInstance();
        int userChoice = view.getChoice();

        switch (userChoice)
        {
            case ViewConstants.FIND_SOUTHERNMOST:
                Timer.startTimer();
                view.printMessage(ViewConstants.SOUTHERNMOST_MESSAGE);
                tableController.printWindfarm(windFarmAnalyzer.findSouthernmostWindfarm());
                view.printMessage(String.format(ViewConstants.TIME_MESSAGE, Timer.getTime()));
                break;
            case ViewConstants.FIND_HIGHEST_PERFORMANCE:
                Timer.startTimer();
                view.printMessage(ViewConstants.HIGHEST_PERFORMANCE_MESSAGE);
                tableController.printWindfarm(windFarmAnalyzer.findHighestPerformance());
                view.printMessage(String.format(ViewConstants.TIME_MESSAGE, Timer.getTime()));
                break;
            case ViewConstants.FIND_MOST_TURBINES:
                Timer.startTimer();
                view.printMessage(ViewConstants.MOST_TURBINES_MESSAGE);
                tableController.printWindfarm(windFarmAnalyzer.findMostWindturbine());
                view.printMessage(String.format(ViewConstants.TIME_MESSAGE, Timer.getTime()));
                break;
            case ViewConstants.CALCULATE_TOTAL_PERFORMACE:
                Timer.startTimer();
                view.printMessage(String.format(
                        ViewConstants.TOTAL_PERFORMANCE_MESSAGE,
                        windFarmAnalyzer.calculateTotalPerformanceOfAllWindFarms()));
                view.printMessage(String.format(ViewConstants.TIME_MESSAGE, Timer.getTime()));
                break;
            default:
                view.printError(ErrorMessages.INVALID_NUMBER);
        }
    }


    /**
     * Wartet auf die Bestaetigung mit <enter> vom Benutzer.
     *
     * @precondition die View ist initialisiert und kann Benutzereingaben lesen.
     * @postcondition die Methode wird beendet, wenn der Benutzer auf <enter> drueckt.
     */
    public void waitForEnter ()
    {
        boolean isNotEnter = true;

        while (isNotEnter)
        {
            view.pressEnterToContinue();

            if (view.getUserInput().isEmpty())
            {
                isNotEnter = false;
            }
        }
    }
}
