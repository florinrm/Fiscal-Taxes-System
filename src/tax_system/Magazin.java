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
        this.type = this.getClass().toString(); // tipul magazinului, pe care il iau din getClass si imi da direct
        // tipul magazinului
        this.tariOrigine = tariOrigine;
    }

    public String toString () {
        Collections.sort(this.facturi); // sortam facturile
        DecimalFormat df = new DecimalFormat("#.####"); // aproximarea procentelor vanzarilor
        String result = this.nume + "\n\n" + "Total " + df.format(this.getTotalFaraTaxe()) + " "
                + df.format(this.getTotalCuTaxe()) + " " + df.format(this.getTotalCuTaxeScutite()) + "\n\nTara\n";
        Iterator iter = this.tariOrigine.iterator();
        // aplicam acelasi procedeu de la facturi
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
        result = result.replaceAll(",", "."); // deoarece DecimalFormat pune preturile cu virgula
        // inlocuim virgula cu punctul
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

    // daca procentul de taxe scutite e 0, returnam totalul din magazin cu tot cu taxe, altfel returnam
    // totalul calculat cu procentul de taxe scutite aka calculTaxeScutite
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

    // ordonare crescatoare in functie de costul total fara taxe
    public int compareTo (Magazin maga) {
        return new Double(this.getTotalFaraTaxe()).compareTo(new Double(maga.getTotalFaraTaxe()));
    }

    public abstract double calculScutiriTaxe (); // cu valori din [0, 1)
}
