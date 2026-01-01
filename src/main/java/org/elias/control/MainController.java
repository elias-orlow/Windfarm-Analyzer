package org.elias.control;

import org.elias.model.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.ProjectConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.CSVFileReader;
import org.elias.util.CSVLineParser;
import org.elias.util.DataCellParser;
import org.elias.view.ConsoleView;

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
    private static MainController INSTANCE;

    /**
     * Die mit dem Controller verbundene ConsoleView.
     */
    private final ConsoleView view;
    private final WindFarmRepository germanWindFarms;


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


    public void start ()
    {
        List<String> dataRows = CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV);
        List<String[]> CSVDataCells = CSVLineParser.convertToDataUnit(dataRows);
        WindFarmImporter.importData(CSVDataCells, germanWindFarms);

        int i = 0;

        for (WindFarm windFarm : germanWindFarms.getGermanWindFarms()){
            System.out.println(windFarm.getName());
            for (WindTurbineGroup windTurbineGroup : windFarm.getWindTurbineGroups()){
                System.out.println(windTurbineGroup.getID());

                i++;
            }
        }

        System.out.println(i);


        programLoop();

        //WindFarmImporter.importData(CSVDataCells, germanWindFarms);
        //System.out.println(DataCellParser.parseWindTurbineType("Nordex N62/1300 (3×)Nordex N80/2500 (1×)Südwind S70 (1×)"));
        //System.out.println(DataCellParser.parseDistrict("KÜN"));

//        List<String[]> CSVDataTestCells = CSVLineParser.convertToDataUnit(new ArrayList<>(List.of(
//                "21,Windpark Tomerdingen-Bermaringen (Windpark Keltische Schanze),2007,21.9,11,Vestas V82-1.5MW (5×),Tomerdingen,UL,48.4703,9.8633,\"Planet Energy, Solarinvest AG; Efi Wind, THEOLIA Naturenergien GmbH, Windreich\",errichtet an der A 8,,,,,,,",
//                "22,Windpark Tomerdingen-Bermaringen (Windpark Keltische Schanze),2013,,,Nordex N117/2400 (6×),Bermaringen,,48.4703,9.8633,,,,,,,,,\n",
//                "23,Windpark Tomerdingen-Bermaringen (Windpark Keltische Schanze),2017,,,,Temmenhausen,,48.4703,9.8633,,,,,,,,,")));
//
//        WindFarm test = WindFarmFactory.createWindFarm(CSVDataTestCells);
//
//        System.out.println(test);
//        System.out.println(test.getName());
//        System.out.println(test.getTotalPerformance());
//        System.out.println(test.getCoordinates().getLatitude() + " " +  test.getCoordinates().getLongitude());
//        System.out.println(test.getProjectManagers().getFirst().getCompany());
//
//        for (WindTurbineGroup turb : test.getWindTurbineGroups()){
//            System.out.println(turb.getID());
//            System.out.println(turb.getRemarks());
//            System.out.println(turb.getManufactureYear());
//            System.out.println(turb.getLocation().getTown() + " " + turb.getLocation().getDistrict());
//            System.out.println(turb.getWindTurbines());
//        }


        //List<String[]> test = CSVFileReader.convertToDataUnit(new ArrayList<>(List.of("1,Windpark Wittighausen,2002,5.94,9,Vestas V47-660kW (9×),Unterwittighausen,TBB,49.6197,9.8028,UMaAG,Am 17.3.2019 brach durch das Sturmtief Eberhard bei einer Anlage ein Flügel ab. Die Anlage wurde nicht repariert. Der Windpark ist derzeit außer betrieb.,,,,,,,")));
        /* Test
        List<String[]> test = CSVLineParser.convertToDataUnit(CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV));
        for (String[] row : test){
            for (String item : row)
            {
                System.out.print(item + " | ");
            }
            System.out.println();
        }
        */

    }

    private void programLoop ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            view.showMenu();
            int userChoice = view.getChoice();

            switch (userChoice)
            {
                case ViewConstants.SHOW_WINDPARKS:
                    view.printCSV(CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV));
                    break;
                case ViewConstants.EXIT:
                    isRunning = false;
                    //TODO: goodbye message
                    break;
            }
        }

    }

}
