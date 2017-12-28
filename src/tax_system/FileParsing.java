package tax_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Vector;

public class FileParsing {
    public TreeSet<String> tariOrigine = new TreeSet<>();
    /* lista cu tarile de origine (puteam sa fac hardcodat, dar da mai bine asa :)) */
    public TreeSet<String> tipuriMagazine = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    });
    /* lista cu categoriile ordonate in ordine inversas (iarasi puteam sa fac hardcodat, dar am zis sa fiu
     cu bun simt :)) */
    public TreeSet<String> categoriiProduse = new TreeSet<>();

    public FileParsing () {};

    // Factory pattern, o fac privata caci o folosesc doar aici, la fel ca celelalte metode private
    private Magazin getMagazin (String name, String shop_name, Vector<Factura> list, TreeSet<String> tariOrigine) {
        if (name.equals("MiniMarket"))
            return new MiniMarket(shop_name, list, tariOrigine);
        else if (name.equals("MediumMarket"))
            return new MediumMarket(shop_name, list, tariOrigine);
        else if (name.equals("HyperMarket"))
            return new HyperMarket(shop_name, list, tariOrigine);
        return null;
    }

    // cautarea de produs, in functie de numele acesteia si de tara ei de origine
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

    public HashMap<String, HashMap<String, Double>> getTaxe1 (String filename) {
        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
            String first_line = scan.nextLine();
            String [] data = first_line.split(" ");
            ArrayList <String> countries = new ArrayList <>();
            for (int i = 1; i < data.length; ++i)
            {
                countries.add(data[i]);
                this.tariOrigine.add(data[i]); // adaug in TreeSet-ul de tari de origine
            }
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] members = line.split(" ");
                HashMap<String, Double> map_value = new HashMap<>();
                for (int i = 0; i < countries.size(); ++i) {
                    map_value.put(countries.get(i), Double.parseDouble(members[i + 1]));
                }
                map.put(members[0], map_value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    private double findTaxa1 (HashMap<String, HashMap<String, Double>> map, String categorie, String taraOrigine) {
        for (Map.Entry<String, HashMap<String, Double>> entry1: map.entrySet()) {
            for (Map.Entry<String, Double> entry2: entry1.getValue().entrySet()) {
                if (categorie.equals(entry1.getKey()) && taraOrigine.equals(entry2.getKey()))
                    return entry2.getValue();
            }
        }
        return -1;
    }

    // clasa privata pentru a stoca tipul si numele magazinului
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

    // Parsing produse.txt - lista de obiecte de tip Produs
    public Vector<Produs> getListProdus (String filename) {
        Vector <Produs> list = new Vector <> ();
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
                    this.categoriiProduse.add(members[1]);
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Parsing facturi.txt - lista de magazine
    public ArrayList <Magazin> getMagazine (String filename, List<Produs> produse, HashMap<String, HashMap<String, Double>> taxe) {
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
                            double taxa = findTaxa1(taxe, produs.getCategorie(), info[1]); // taxa
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
            // adaugare manarie a la Mihalache aka sa parsezi ultima linie, caci citirea din while nu o ia in considerare
            String[] parsing = data.split(" ");
            Produs product = findProdus(produse, parsing[0], parsing[1]);
            double tax = findTaxa1(taxe, product.getCategorie(), parsing[1]);
            int quantity = Integer.parseInt(parsing[2]);
            ProdusComandat prod = new ProdusComandat(product, tax, quantity); // last line
            Vector <Factura> last_facturi = map2.get(count - 1);
            Factura last = last_facturi.get(last_facturi.size() - 1);
            last_facturi.remove(last);
            last.addProdus(prod);
            last_facturi.add(last);
            map2.put(count - 1, last_facturi);
            // construim obiectele de tip Magazin si le adaugam in lista de obiecte Magazin
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

    public double findPretProdusDupaTara (String denumire, String categorie, String taraOrigine, List<Produs> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (denumire.equals(list.get(i).getDenumire())
                    && taraOrigine.equals(list.get(i).getTaraOrigine())
                    && categorie.equals(list.get(i).getCategorie()))
                return list.get(i).getPret();
        }
        return 0;
    }

    public void writeFileProduse (List<Produs> list_produse) {

    }
}
