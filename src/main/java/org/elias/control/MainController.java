package org.elias.control;

import org.elias.Main;
import org.elias.model.*;
import org.elias.model.sort.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.*;
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

        calculateRuntime(this::validatePerformance);

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
     * @precondition {@link WindFarmRepository} enthaelt einen oder mehrere {@link WindFarm}.
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
     * Validiert und normalisiert die Leistungen aller Windparks.
     * <p>
     * Die Anzahl an geaenderten Leistungen werden zusammengezaehlt und ausgegeben.
     *
     * @precondition {@link WindFarmRepository} enthaelt einen oder mehrere {@link WindFarm} und die Leistungswerte
     * der Windparks sind gesetzt.
     * @postcondition Alle WindFarm-Objekte enthalten normalisierte Leistungen. Die Anzahl der vorgenommenen Korrekturen
     * wurden ausgegeben.
     */
    private void validatePerformance ()
    {
        germanWindFarms.setWindFarmGraph(GraphFactory.createGraph(germanWindFarms.getGermanWindFarms()));

        for (WindFarm windFarm : germanWindFarms.getGermanWindFarms())
        {
            PerformanceNormalizer.normalizePerformance(windFarm, germanWindFarms.getWindFarmGraph());
        }

        List<String> correctedPerformance = PerformanceNormalizer.getChangedPerf();

        view.printMessage(String.format(ViewConstants.UPDATED_PERFORMANCE_MESSAGE, correctedPerformance.size()));
        for (String message : correctedPerformance)
        {
            view.printMessage(message);
        }
    }


    /**
     * Fuehrt die Hauptprogrammschleife aus.
     * <p>
     * Das Menue wird angezeigt, Benutzereingaben verarbeitet und entsprechende Aktionen ausgefuehrt.
     * <ul>
     *      <li><b>1. Show CSV-File:</b> Zeigt die Rohdaten der Windparks aus der CSV-Datei an.</li>
     *      <li><b>2. Print repository of wind-farms:</b> Gibt alle Windparks formatiert als Tabellen aus.</li>
     *      <li><b>3. Sort and show wind-farms:</b> Ermoeglicht dem Benutzer, ein Sortierkriterium zu waehlen
     * und zeigt anschliessend die sortierten Windparks an.</li>
     *      <li><b>4. Analysis of wind-farms:</b> Führt verschiedene Analysen durch</li>
     * </ul>
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
                case ViewConstants.WINDFARM_PLAN:
                    createPlanProcess();
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


    /**
     * Verarbeitet die Benutzereingabe fuer das Sortiermenue.
     * <p>
     * Der Benutzer kann verschiedene Sortierkriterien auswaehlen, um die
     * Windparks nach bestimmten Eigenschaften zu ordnen. Zu den moeglichen
     * Sortieroptionen gehoeren:
     * <ul>
     *   <li>Sortieren nach ID (auf- oder absteigend)</li>
     *   <li>Sortieren nach Alter der Turbinen</li>
     *   <li>Sortieren nach Breitengrad</li>
     *   <li>Sortieren nach Anzahl der Turbinen</li>
     *   <li>Sortieren nach Anzahl der Projektleiter</li>
     *   <li>Sortieren nach Gesamtleistung (auf- oder absteigend)</li>
     *   <li>Sortieren nach Name (A–Z oder Z–A)</li>
     *   <li>Kombinierte Sortierungen</li>
     * </ul>
     *
     * @precondition die View ist initialisiert und kann Benutzereingaben lesen.
     * @postcondition die Windparks wurden entsprechend der Benutzerauswahl sortiert
     * und ausgegeben, oder der Benutzer wurde erneut zur Eingabe aufgefordert.
     */
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
                sortByAndPrint(new TotalPerformanceComparator().reversed());
                break;
            case ViewConstants.PERFORMANCE_DESC:
                sortByAndPrint(new TotalPerformanceComparator());
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


    /**
     * Sortiert die Windparks anhand des uebergebenen Vergleichskriteriums
     * und gibt das Ergebnis anschließend formatiert aus.
     *
     * @param comparator das Sortierkriterium, nach dem die Windparks geordnet werden sollen.
     * @precondition der Comparator ist nicht null und das Repository ist initialisiert.
     * @postcondition die Windparks wurden sortiert und tabellarisch ausgegeben.
     */
    private void sortByAndPrint (Comparator<WindFarm> comparator)
    {
        germanWindFarms.getGermanWindFarms().sort(comparator);
        TableController tableController = TableController.getInstance();
        tableController.printRepository(germanWindFarms);
    }


    /**
     * Verarbeitet die Benutzereingabe fuer das Analysemenue und führt die
     * entsprechende Analysefunktion aus.
     * <p>
     * Der Benutzer kann verschiedene Auswertungen ueber die vorhandenen
     * Windparks durchfuehren. Zu den verfuegbaren Analyseoptionen gehoeren:
     * <ul>
     *   <li>Ermitteln des suedlichsten Windparks</li>
     *   <li>Ermitteln des Windparks mit der hoechsten Gesamtleistung</li>
     *   <li>Ermitteln des Windparks mit den meisten Windturbinen</li>
     *   <li>Berechnung der Gesamtleistung aller Windparks</li>
     * </ul>
     *
     * @precondition die View, das Repository und der TableController sind
     * vollstaendig initialisiert und koennen Eingaben verarbeiten.
     * @postcondition Die vom Benutzer gewaehlte Analyse wurde ausgefuehrt und
     * das Ergebnis sowie Laufzeit wurde ausgegeben. Bei ungueltiger Eingabe
     * wurde eine Fehlermeldung angezeigt.
     */
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


    private void createPlanProcess ()
    {
        List<ProjectManager> projectManagers = new ArrayList<>(ProjectManagerAdministration.getInstance().getProjectManagerList());
        Collections.sort(projectManagers);

        ProjectManager selectedManager = chooseProjectManager(projectManagers);
        if (selectedManager == null)
        {
            return;
        }

        List<WindFarm> windFarmsWithManager = getWindFarmsWithProjectManager(selectedManager);
        WindFarm startingWindFarm = chooseStartingWindFarm(windFarmsWithManager);
        if (startingWindFarm == null)
        {
            return;
        }

        createPlan(startingWindFarm, windFarmsWithManager);
    }


    private ProjectManager chooseProjectManager (List<ProjectManager> projectManagers)
    {
        view.printMessage(ViewConstants.CHOICE_PROJECT_MANAGER_MESSAGE);

        int index = GeneralConstants.INT_ONE;
        for (ProjectManager projectManager : projectManagers)
        {
            view.printMessage(String.format(ViewConstants.INDEX_DATA_MESSAGE, index++, projectManager.getCompany()));
        }

        int choice = view.getChoice();

        if (choice > projectManagers.size())
        {
            view.printError(ErrorMessages.INVALID_MANAGER_CHOICE);
            return null;
        }

        return projectManagers.get(choice - GeneralConstants.INT_ONE);
    }


    private List<WindFarm> getWindFarmsWithProjectManager (ProjectManager manager)
    {
        WindFarmAnalyzer windFarmAnalyzer = new WindFarmAnalyzer(germanWindFarms);

        return windFarmAnalyzer.filterWindFarmsWithProjectManager(manager);
    }


    private WindFarm chooseStartingWindFarm (List<WindFarm> windFarms)
    {
        view.printMessage(ViewConstants.CHOICE_START_POINT_MESSAGE);

        int index = GeneralConstants.INT_ONE;
        for (WindFarm windFarm : windFarms)
        {
            view.printMessage(String.format(ViewConstants.INDEX_DATA_MESSAGE, index++, windFarm.getName()));
        }

        int choice = view.getChoice();

        if (choice > windFarms.size())
        {
            view.printError(ErrorMessages.INVALID_WINDFARM_CHOICE);
            return null;
        }

        return windFarms.get(choice - GeneralConstants.INT_ONE);
    }


    private void createPlan (WindFarm startingWindFarm, List<WindFarm> windFarms)
    {
        NearestNeighborRoutePlanner nearestNeighborRoutePlanner = new NearestNeighborRoutePlanner();
        List<WindFarm> route = nearestNeighborRoutePlanner.calculateRoute(windFarms, startingWindFarm);

        Schedule schedule = SchedulePlanner.createPlan(route);

        view.printMessage(ViewConstants.PLAN_TABLE_HEAD);

        TableController tableController = TableController.getInstance();
        tableController.printSchedule(schedule);

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
