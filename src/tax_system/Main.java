package tax_system;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args)
    {
        FileParsing parsing = new FileParsing();
        ArrayList <Produs> list = parsing.getListProdus("produse.txt");
        ArrayList <TaxaProdus> list2 = parsing.getTaxe("taxe.txt"); /*
        for (int i = 0; i < list2.size(); ++i)
            System.out.println(list2.get(i));
        System.out.println("Magazine"); */
        ArrayList <Magazin> list3 = parsing.getMagazine("facturi.txt", list, list2); /*
        for (int i = 0; i < list3.size(); ++i)
            System.out.println(list3.get(i)); */
    }
}
