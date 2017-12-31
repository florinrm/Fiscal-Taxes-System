package tax_system;

import java.io.*;
import java.util.*;

public class Main {

    public Main () {
        FileParsing parsing = new FileParsing();
        Vector <Produs> list = parsing.getListProdus("produse.txt");
        HashMap <String, HashMap<String, Double>> map = parsing.getTaxe1("taxe.txt");
        ArrayList <Magazin> list3 = parsing.getMagazine("facturi.txt", list, map);
        TreeSet<String> tipuriMagazine = parsing.tipuriMagazine;

        Gestiune obj = Gestiune.getInstance();
        obj.setMagazine(list3);
        obj.setProduse(list);
        obj.setTaxes(map);
        obj.setTipuriMagazine(tipuriMagazine);
        try {
            System.setOut(new PrintStream(new File("out.txt")));
            System.out.println(obj);
            System.setOut(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
