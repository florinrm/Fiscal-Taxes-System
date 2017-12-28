package tax_system;

import java.io.*;
import java.util.*;

public class Main {

    public double findPret (Vector<Produs> list, String nume, String taraOrigine, String categorie) {
        for (int i = 0; i < list.size(); ++i) {
            if (nume.equals(list.get(i).getDenumire())
                    && categorie.equals(list.get(i).getCategorie())
                    && taraOrigine.equals(list.get(i).getTaraOrigine()))
                return list.get(i).getPret();
        }
        return 0;
    }

    public Main () {
        FileParsing parsing = new FileParsing();
        Vector <Produs> list = parsing.getListProdus("produse.txt");
        HashMap <String, HashMap<String, Double>> map = parsing.getTaxe1("taxe.txt");
        ArrayList <Magazin> list3 = parsing.getMagazine("facturi.txt", list, map);
        TreeSet<String> tipuriMagazine = parsing.tipuriMagazine;
        ArrayList<String> countries = new ArrayList<>(parsing.tariOrigine); /*
        try {
            System.setOut(new PrintStream(new File("testing.txt")));
            System.out.print("Produs Categorie ");
            int no_countries = countries.size();
            for (int i = 0; i < no_countries; ++i) {
                if (i == no_countries - 1)
                    System.out.print(countries.get(i) + "\n");
                else
                    System.out.print(countries.get(i) + " ");
            }
            Collections.sort(list, new Comparator<Produs>() {
                @Override
                public int compare(Produs o1, Produs o2) {
                    if (o1.getCategorie().equals(o2.getCategorie()))
                        return (-1) * o1.getDenumire().compareTo(o2.getDenumire());
                    else
                        return (-1) * o1.getCategorie().compareTo(o2.getCategorie());
                }
            });
            for (int i = 0; i < list.size(); i = i + no_countries) {
                System.out.print(list.get(i).getDenumire() + " " + list.get(i).getCategorie() + " ");
                for (int j = 0; j < no_countries; ++j) {
                    if (j == no_countries - 1)
                        System.out.print (findPret(list, list.get(i).getDenumire(), countries.get(j), list.get(i).getCategorie()) + "\n");
                    else
                        System.out.print (findPret(list, list.get(i).getDenumire(), countries.get(j), list.get(i).getCategorie()) + " ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
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
