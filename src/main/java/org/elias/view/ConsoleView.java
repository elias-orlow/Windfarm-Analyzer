package org.elias.view;

import org.elias.res.constant.ProjectConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.CSVFileReader;

import java.util.List;
import java.util.Scanner;

public final class ConsoleView
{
    private static ConsoleView INSTANCE;
    private final Scanner scanner = new Scanner(System.in);


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


    public void showMenu ()
    {
        System.out.println(ViewConstants.MAIN_MENU_MESSAGE);
    }

    public int getChoice ()
    {
        return scanner.nextInt();
    }

    public void printMessage (String message)
    {
        System.out.println(message);
    }

    public void printCSV (List<String> CSVRows){
        for (String line : CSVRows)
        {
            System.out.println(line);
        }
    }
}
