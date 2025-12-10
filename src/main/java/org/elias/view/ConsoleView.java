package org.elias.view;

public final class ConsoleView
{
    private static ConsoleView INSTANCE;


    private ConsoleView ()
    {
    }


    public static ConsoleView getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ConsoleView();
        }
        return INSTANCE;
    }

}
