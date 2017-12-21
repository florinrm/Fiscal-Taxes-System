package tax_system;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public abstract class Magazin implements IMagazin, Comparable<Magazin> {
    public String nume;
    public Vector<Factura> facturi;
    public String type;
    public TreeSet<String> tariOrigine;

    public Magazin (String nume, Vector <Factura> facturi, TreeSet<String> tariOrigine) {
        this.nume = nume;
        this.facturi = facturi;
        this.type = this.getClass().toString();
        this.tariOrigine = tariOrigine;
    }

    public TreeSet<String> getCountries () {
        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < this.facturi.size(); ++i) {
            set.addAll(this.facturi.get(i).getCountries());
        }
        Iterator iter = set.iterator();
        return set;
    }

    public String toString () {
        Collections.sort(this.facturi);
        DecimalFormat df = new DecimalFormat("#.####");
        String result = this.nume + "\n\n" + "Total " + df.format(this.getTotalFaraTaxe()) + " "
                + df.format(this.getTotalCuTaxe()) + " " + df.format(this.getTotalCuTaxeScutite()) + "\n\nTara\n";
        Iterator iter = this.tariOrigine.iterator();
        while (iter.hasNext()) {
            String country = iter.next().toString();
            if (this.getTotalTaraFaraTaxe(country) != 0)
                result += country + " " +  df.format(this.getTotalTaraFaraTaxe(country))
                        + " " + df.format(this.getTotalTaraCuTaxe(country))
                        + " " + df.format(this.getTotalTaraCuTaxeScutite(country)) + "\n";
            else
                result += country + " 0\n";
        }
        for (int i = 0; i < this.facturi.size(); ++i) {
            result += this.facturi.get(i);
        }
        result = result.replaceAll(",", ".");
        return result;
    }

    public double getTotalFaraTaxe() {
        double total = 0;
        for (int i = 0; i < this.facturi.size(); ++i) {
            total += this.facturi.get(i).getTotalFaraTaxe();
        }
        return total;
    }

    public double getTotalCuTaxe () {
        double total = 0;
        for (int i = 0; i < this.facturi.size(); ++i) {
            total += this.facturi.get(i).getTotalCuTaxe();
        }
        return total;
    }

    public double getTotalCuTaxeScutite () {
        if (new Double(this.calculScutiriTaxe()).equals(new Double(0)))
            return this.getTotalCuTaxe();
        else
        {
            double scutire = this.getTotalCuTaxe() * this.calculScutiriTaxe();
            return this.getTotalCuTaxe() - scutire;
        }
    }

    public double getTotalTaraFaraTaxe (String country) {
        double total = 0;
        for (int i = 0; i < this.facturi.size(); ++i) {
            total += this.facturi.get(i).getTotalTaraFaraTaxe (country);
        }
        return total;
    }

    public double getTotalTaraCuTaxe (String country) {
        double total = 0;
        for (int i = 0; i < this.facturi.size(); ++i) {
            total += this.facturi.get(i).getTotalTaraCuTaxe (country);
        }
        return total;
    }

    public double getTotalTaraCuTaxeScutite (String country) {
        if (this.calculScutiriTaxe() == 0)
            return this.getTotalTaraCuTaxe (country);
        else {
            double total = 0;
            for (int i = 0; i < this.facturi.size(); ++i) {
                total += this.facturi.get(i).getTotalTaraCuTaxe (country);
            }
            return total * (1 - this.calculScutiriTaxe());
        }
    }

    public int compareTo (Magazin maga) {
        if (this.nume.substring(0, this.nume.length() - 2).equals(maga.nume.substring(0, maga.nume.length() - 2)))
            return (-1) * Integer.parseInt(this.nume.substring(this.nume.length() - 1))
                    + Integer.parseInt(maga.nume.substring(maga.nume.length() - 1));
        else
            return (-1) * this.type.compareTo(maga.type);
    }

    public abstract double calculScutiriTaxe (); // cu valori din [0, 1]
}