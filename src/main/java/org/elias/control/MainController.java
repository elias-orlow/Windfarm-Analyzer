package org.elias.control;

import org.elias.view.ConsoleView;

public final class MainController
{
    private static MainController INSTANCE;

    private final ConsoleView view;


    private MainController (ConsoleView view)
    {
        this.view = view;
    }


    public static MainController getInstance ()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException("ConsoleView wurde noch nicht initialisiert!");
        }
        return INSTANCE;
    }


    public static void init (ConsoleView view)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new MainController(view);
        } else
        {
            throw new IllegalStateException("ConsoleView wurde schon initialisiert!");
        }
    }


    public void start ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            System.out.println("TEST!");
            isRunning = false;
        }

    }

}
