package org.elias.view;

import org.elias.res.constant.ViewConstants;

import java.util.List;
import java.util.Scanner;

/**
 * View-Klasse für Benutzereingaben und Ausgaben in der Konsole
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und es existiert genau eine einzige Instanz davon.
 */
public final class ConsoleView
{
    private static ConsoleView INSTANCE = null;
    private final Scanner scanner = new Scanner(System.in);


    private ConsoleView ()
    {
    }


    /**
     * Liefert die einzige Instanz dieser Klasse zurueck.
     * Falls noch keine existiert, wird sie erzeugt.
     *
     * @return Singleton-Instanz von ConsoleView
     */
    public static ConsoleView getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ConsoleView();
        }
        return INSTANCE;
    }


    /**
     * Gibt das Hauptmenue auf der Konsole aus.
     *
     * @precondition {@code ViewConstants.MAIN_MENU_MESSAGE} ist nicht null.
     * @postcondition die Menueausgabe wurde auf der Konsole angezeigt.
     */
    public void showMenu ()
    {
        System.out.println(ViewConstants.MAIN_MENU_MESSAGE);
    }


    /**
     * Gibt die angegebene Nachricht auf der Konsole aus.
     *
     * @param message die auszugebende Nachricht.
     * @precondition eine Instanz von {@code ConsoleView} existiert.
     * @postcondition die Nachricht wurde auf der Konsole ausgegeben.
     */
    public void printMessage (String message)
    {
        System.out.println(message);
    }


    /**
     * Liest eine ganzzahlige Benutzereingabe aus der Konsole ein.
     *
     * @return der vom Benutzer eingegebene Integer-Wert.
     * @precondition der Benutzer gibt einen gueltigen ganzzahligen Wert ein.
     * @postcondition gibt den eingelesenen Integer-Wert zurueck.
     */
    public int getChoice ()
    {
        return scanner.nextInt();
    }


    /**
     * Fuegt eine bestimmte Anzahl leerer Zeilen ein.
     *
     * @param rowsCount Anzahl der Leerzeilen.
     * @precondition {@code rowsCount} ist groesser oder gleich 0.
     * @postcondition Es wurden genau {@code rowsCount} Leerzeilen ausgegeben.
     */
    public void makeSpace (int rowsCount)
    {
        for (int i = 0; i < rowsCount; i++)
        {
            System.out.println();
        }
    }


    /**
     * Gibt eine Liste von CSV-Zeilen auf der Konsole aus.
     *
     * @param CSVRows Liste der CSV-Zeilen, darf null oder leer sein.
     * @precondition CSV-Liste existiert und es wurde eine Liste mit {@link org.elias.util.CSVFileReader#convertCSVtoList(String)} erzeugt.
     * @postcondition Jede Zeile der Liste wurde in der urspruenglichen Reihenfolge auf der Konsole ausgegeben.
     */
    public void printCSV (List<String> CSVRows)
    {
        for (String line : CSVRows)
        {
            System.out.println(line);
        }
    }
}
