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

        Collections.sort(list);
        for (int i = 0; i < list.size(); ++i)
            System.out.println(list.get(i));

        Gestiune obj = Gestiune.getInstance();
        obj.setMagazine(list3);
        obj.setProduse(list);
        obj.setTaxes(map);
        obj.setTipuriMagazine(tipuriMagazine);
        try {
            System.setOut(new PrintStream(new File("output.txt")));
            System.out.println(obj);
            System.setOut(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void main (String[] args)
    {
        FileParsing parsing = new FileParsing();
        ArrayList <Produs> list = parsing.getListProdus("produse.txt");
        ArrayList <TaxaProdus> list2 = parsing.getTaxe("taxe.txt");
        ArrayList <Magazin> list3 = parsing.getMagazine("facturi.txt", list, list2);


        TreeSet <String> countries = parsing.tariOrigine;
        TreeMap <String, HashMap<String, Double>> map = new TreeMap<>();
        Iterator iterate = countries.iterator();
        while (iterate.hasNext()) {
            String country = iterate.next().toString();
            HashMap<String, Double> element = new HashMap<>();
            for (int i = 0; i < list2.size(); ++i) {
                if (country.equals(list2.get(i).getTaraOrigine()))
                    element.put(list2.get(i).getDenumire(), list2.get(i).getTaxa());
            }
            map.put(country, element);
        }
        Gestiune obj = Gestiune.getInstance();
        obj.setMagazine(list3);
        obj.setProduse(list);
        obj.setTaxes(map);


        TreeSet<String> tipuriMagazine = parsing.tipuriMagazine;
        Iterator iter = tipuriMagazine.iterator();
        Collections.sort(list3);
        try {
            System.setOut(new PrintStream(new File("output.txt")));
            while (iter.hasNext()) {
                String type = iter.next().toString();
                System.out.println(type);
                for (int i = 0; i < list3.size(); ++i) {
                    // parsam tipul magazinului (in interiorul clasei imi erau aruncate exceptii cand faceam parsarea
                    // asa ca o fac aici
                    if (type.equals(list3.get(i).type.split(" ")[1].split("_")[1].substring(7)))
                        System.out.println(list3.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    } */
}
