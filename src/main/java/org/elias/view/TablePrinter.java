package org.elias.view;

public class TablePrinter
{
    private static TablePrinter INSTANCE;

    private TablePrinter ()
    {
    }

    public static TablePrinter getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TablePrinter();
        }
        return INSTANCE;
    }

    public void printUpperSeparator (int width)
    {
        System.out.println("╔" + "═".repeat(width - 2) + "╗");
    }

    public void printLowerSeparator (int width)
    {
        System.out.println("╚" + "═".repeat(width - 2) + "╝");
    }

    public void printInnerSeparator (int width)
    {
        System.out.println("╠" + "═".repeat(width - 2) + "╣");
    }

    public void printDataCell (String data)
    {
        System.out.println("║" + data + "║");
    }

    public void makeSpace (int rowsCount)
    {
        for (int i = 0; i < rowsCount; i++)
        {
            System.out.println();
        }
    }

}
