package org.elias.util;

/**
 * Util-Klasse zur Messung von Ausfuehrungszeiten.
 */
public class Timer
{
    /** Zeitpunkt des letzten Timer-Starts in Millisekunden. */
    private static long startTime;

    /**
     * Startet den Timer, indem der aktuelle Zeitpunkt gespeichert wird.
     *
     * @precondition keine (der Timer kann jederzeit gestartet werden).
     * @postcondition {@code startTime} enthaelt den aktuellen Zeitpunkt in Millisekunden.
     */
    public static void startTimer ()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Gibt den Zeitabstand seit dem letzten Aufruf von {@link #startTimer()}
     *
     * @return vergangene Zeit in Millisekunden.
     * @precondition {@link #startTimer()} wurde aufgerufen.
     * @postcondition gibt der Zeitdifferenz zurueck.
     */
    public static long getTime ()
    {
        return System.currentTimeMillis() - startTime;
    }
}
