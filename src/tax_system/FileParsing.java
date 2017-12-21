package tax_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Vector;

public class FileParsing {
    public TreeSet<String> tariOrigine = new TreeSet<>();
    public TreeSet<String> categorii = new TreeSet<>();
    public TreeSet<String> tipuriMagazine = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });

    public FileParsing () {};

    // Factory pattern
    private Magazin getMagazin (String name, String shop_name, Vector<Factura> list, TreeSet<String> tariOrigine) {
        if (name.equals("MiniMarket"))
            return new MiniMarket(shop_name, list, tariOrigine);
        else if (name.equals("MediumMarket"))
            return new MediumMarket(shop_name, list, tariOrigine);
        else if (name.equals("HyperMarket"))
            return new HyperMarket(shop_name, list, tariOrigine);
        return null;
    }

    private Produs findProdus (List <Produs> list, String denumire, String taraOrigine)
    {
        for (int i = 0; i < list.size(); ++i)
        {
            if (list.get(i).getDenumire().equals(denumire)
                    && list.get(i).getTaraOrigine().equals(taraOrigine))
                return list.get(i);
        }
        return null;
    }

    private double findTaxa (List <TaxaProdus> list, String denumire, String taraOrigine)
    {
        for (int i = 0; i < list.size(); ++i)
        {
            if (list.get(i).getDenumire().equals(denumire)
                    && list.get(i).getTaraOrigine().equals(taraOrigine))
                return list.get(i).getTaxa();
        }
        return -1;
    }

    // parsing taxe.txt
    public ArrayList <TaxaProdus> getTaxe (String filename) {
        ArrayList <TaxaProdus> list = new ArrayList<>();
        Scanner scan = null;
        try {
            scan = new Scanner (new File(filename));
            String first_line = scan.nextLine();
            String [] data = first_line.split(" ");
            ArrayList <String> countries = new ArrayList <>();
            for (int i = 1; i < data.length; ++i)
            {
                countries.add(data[i]);
                this.tariOrigine.add(data[i]);
            }
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                String[] members = line.split(" ");
                for (int i = 0; i < countries.size(); ++i)
                {
                    list.add(new TaxaProdus(members[0], countries.get(i), Double.parseDouble(members[i + 1])));
                }
            }
            scan.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    private class ShopType {
        public String name;
        public String type;

        public ShopType (String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String toString () {
            String result = "Name: " + this.type + "\nType: " + this.type + "\n";
            return result;
        }
    }

    // Parsing produse.txt
    public ArrayList<Produs> getListProdus (String filename) {
        ArrayList <Produs> list = new ArrayList <> ();
        Scanner scan = null;
        try {
            scan = new Scanner (new File(filename));
            String first_line = scan.nextLine();
            String [] data = first_line.split(" ");
            ArrayList <String> countries = new ArrayList <>();
            for (int i = 2; i < data.length; ++i)
                countries.add(data[i]);
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                String [] members = line.split(" ");
                for (int i = 0; i < countries.size(); ++i)
                {
                    list.add(new Produs (members[0], members[1], countries.get(i), Double.parseDouble(members[i + 2])));
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Parsing facturi.txt
    public ArrayList <Magazin> getMagazine (String filename, List<Produs> produse, List<TaxaProdus> taxe) {
        ArrayList <Magazin> list = new ArrayList<>();
        Scanner scan = null;
        try {
            scan = new Scanner (new File (filename));
            int count = 0;
            HashMap<Integer, ShopType> map = new HashMap<>(); // count + magazin
            HashMap<Integer, Vector<Factura>> map2 = new HashMap<>(); // count + vector factura
            String line, data = null;
            while (scan.hasNextLine())
            {
                line = scan.nextLine(); // tipul de magazin
                if (line.indexOf("Magazin") != -1) {
                    String[] shop_data = line.split(":");
                    this.tipuriMagazine.add(shop_data[1]);
                    map.put(count++, new ShopType(shop_data[1], shop_data[2]));
                }

                if (line.indexOf("Factura") != -1) {
                    data = scan.nextLine();
                    Vector<ProdusComandat> fact = new Vector<>();
                    while (data.length() > 0 && scan.hasNextLine()) {
                        if (data.indexOf("Denumire") == -1) {
                            String[] info = data.split(" ");
                            Produs produs = findProdus(produse, info[0], info[1]); // produsul
                            double taxa = findTaxa(taxe, produs.getCategorie(), info[1]); // taxa
                            int cantitate = Integer.parseInt(info[2]);
                            fact.add (new ProdusComandat(produs, taxa, cantitate));
                        }
                        data = scan.nextLine();
                    }
                    Factura factura = new Factura (line, fact, this.tariOrigine);
                    if (map2.get(count - 1) == null) {
                        map2.put(count - 1, new Vector <>());
                        map2.get(count - 1).add(factura);
                    } else {
                        map2.get(count - 1).add(factura);
                    }
                }
            }
            // System.out.println(data); // adaugare manarie a la Mihalache aka fa in plm parsare cu ultima linie boss
            String[] parsing = data.split(" ");
            Produs product = findProdus(produse, parsing[0], parsing[1]);
            double tax = findTaxa(taxe, product.getCategorie(), parsing[1]);
            int quantity = Integer.parseInt(parsing[2]);
            ProdusComandat prod = new ProdusComandat(product, tax, quantity); // last line
            Vector <Factura> last_facturi = map2.get(count - 1);
            Factura last = last_facturi.get(last_facturi.size() - 1);
            last_facturi.remove(last);
            last.addProdus(prod);
            last_facturi.add(last);
            map2.put(count - 1, last_facturi);
            for (int i = 0; i < count; ++i) {
                Magazin shop = getMagazin(map.get(i).name, map.get(i).type, map2.get(i), this.tariOrigine);
                list.add(shop);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap <String, HashMap <String, Double>> getDictionar (String filename) {
        HashMap <String, HashMap <String, Double>> map = new HashMap<> ();
        Scanner scan = null;
        try {
            scan = new Scanner (new File(filename));
            String firstLine = scan.nextLine();
            String[] data = firstLine.split(" ");

            for (int i = 1; i < data.length; i++)
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
