package org.elias.view;

import org.elias.res.constant.ViewConstants;

/**
 * View-Klasse für Tabellenausgaben in der Konsole
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und es existiert genau eine einzige Instanz davon.
 */
public class TablePrinter
{
    private static TablePrinter INSTANCE = null;

    /**
     * Erzeugt eine neue Instanz des {@code TablePrinter}.
     * <p>
     * Der Konstruktor ist privat, da die Klasse dem Singleton-Muster folgt
     * und ausschliesslich über die Zugriffsmethode {@code getInstance()}
     * instanziiert werden darf.
     *
     * @precondition es existiert noch keine Instanz des {@code TablePrinter}.
     * @postcondition eine neue {@code TablePrinter}-Instanz ist erzeugt und initialisiert.
     */
    private TablePrinter ()
    {
    }


    /**
     * Liefert die einzige Instanz dieser Klasse zurueck.
     * Falls noch keine existiert, wird sie erzeugt.
     *
     * @return Singleton-Instanz von TablePrinter
     */
    public static TablePrinter getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TablePrinter();
        }
        return INSTANCE;
    }


    /**
     * Gibt die obere Rahmenlinie einer Tabelle aus.
     *
     * @param width Gesamtbreite der Tabelle.
     * @precondition {@code width} ist groesser als 2.
     * @postcondition die obere Trennlinie wurde auf der Konsole ausgegeben.
     */
    public void printUpperSeparator (int width)
    {
        System.out.println(ViewConstants.BORDER_TOP_LEFT
                + ViewConstants.BORDER_LINE.repeat(width - ViewConstants.BORDER_OFFSET)
                + ViewConstants.BORDER_TOP_RIGHT);
    }


    /**
     * Gibt die untere Rahmenlinie einer Tabelle aus.
     *
     * @param width Gesamtbreite der Tabelle.
     * @precondition {@code width} ist groesser als 2.
     * @postcondition die untere Trennlinie wurde auf der Konsole ausgegeben.
     */
    public void printLowerSeparator (int width)
    {
        System.out.println(ViewConstants.BORDER_BOTTOM_LEFT
                + ViewConstants.BORDER_LINE.repeat(width - ViewConstants.BORDER_OFFSET)
                + ViewConstants.BORDER_BOTTOM_RIGHT);
    }


    /**
     * Gibt eine innere Trennlinie innerhalb einer Tabelle aus.
     *
     * @param width Gesamtbreite der Tabelle.
     * @precondition {@code width} ist groesser als 2.
     * @postcondition die innere Trennlinie wurde auf der Konsole ausgegeben.
     */
    public void printInnerSeparator (int width)
    {
        System.out.println(ViewConstants.BORDER_INNER_LEFT
                + ViewConstants.BORDER_LINE.repeat(width - ViewConstants.BORDER_OFFSET)
                + ViewConstants.BORDER_INNER_RIGHT);
    }


    /**
     * Gibt eine Datenzelle mit Rahmen aus.
     *
     * @param data Inhalt der Zelle.
     * @precondition {@code data} ist nicht null.
     * @postcondition Die Zelle wurde vollstaendig ausgegeben.
     */
    public void printDataCell (String data)
    {
        System.out.println(ViewConstants.BORDER_SIDE + data + ViewConstants.BORDER_SIDE);
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
     * Gibt die angegebene Nachricht als Error auf der Konsole aus.
     *
     * @param errorMessage die auszugebende Nachricht.
     * @precondition eine Instanz von {@code TablePrinter} existiert.
     * @postcondition die Nachricht wurde auf der Konsole in der roten Farbe ausgegeben.
     */
    public void printError (String errorMessage)
    {
        System.err.println(errorMessage);
    }

}
