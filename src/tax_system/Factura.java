package tax_system;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class Factura implements Comparable <Factura> {
    public String denumire;
    public Vector<ProdusComandat> lista_produse;
    public TreeSet<String> tariOrigine;

    public Factura (String denumire, Vector<ProdusComandat> lista_produse, TreeSet<String> tariOrigine) {
        this.denumire = denumire;
        this.lista_produse = lista_produse;
        this.tariOrigine = tariOrigine;
    }

    public void addProdus (ProdusComandat produs) {
        this.lista_produse.add(produs);
    }

    public void removeProdus (ProdusComandat produs) {
        this.lista_produse.remove(produs);
    }

    public TreeSet<String> getCountries () {
        TreeSet <String> set = new TreeSet<>();
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            set.add(this.lista_produse.get(i).getProdus().getTaraOrigine());
        }
        return set;
    }

    public String toString () {
        String result = "\n" + this.denumire + "\n\n";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "Total " + df.format(this.getTotalFaraTaxe()) + " " + df.format(this.getTotalCuTaxe()) + "\n\n"
                + "Tara\n";
        Iterator iter = this.tariOrigine.iterator();
        while (iter.hasNext()) {
            String country = iter.next().toString();
            if (this.getTotalTaraFaraTaxe(country) != 0)
                result += country + " " + df.format(this.getTotalTaraFaraTaxe(country)) + " "
                        + df.format(this.getTotalTaraCuTaxe(country)) + "\n";
            else
                result += country + " 0\n";
        }
        return result;
    }

    public double getTotalFaraTaxe () {
        double total = 0;
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            total += this.lista_produse.get(i).getProdus().getPret() * this.lista_produse.get(i).getCantitate();
        }
        return total;
    }

    public double getTotalTaxe () {
        double total = 0;
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            total += this.lista_produse.get(i).getCantitate()
                    * this.lista_produse.get(i).getTaxa() * this.lista_produse.get(i).getProdus().getPret() / 100;
        }
        return total;
    }

    public double getTotalCuTaxe () {
        return this.getTotalFaraTaxe() + this.getTotalTaxe();
    }

    public double getTotalTaraFaraTaxe (String country) {
        double total = 0;
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            if (country.equals(this.lista_produse.get(i).getProdus().getTaraOrigine()))
                total += this.lista_produse.get(i).getProdus().getPret() * this.lista_produse.get(i).getCantitate();
        }
        return total;
    }

    public double getTaxeTara (String country) {
        double total = 0;
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            if (country.equals(this.lista_produse.get(i).getProdus().getTaraOrigine()))
                total += this.lista_produse.get(i).getCantitate()
                        * this.lista_produse.get(i).getTaxa() * this.lista_produse.get(i).getProdus().getPret() / 100;
        }
        return total;
    }

    public double getTotalTaraCuTaxe (String country) {
        return this.getTotalTaraFaraTaxe (country) + this.getTaxeTara (country);
    }

    @Override
    public int compareTo(Factura o) {
        return (-1) * this.denumire.compareTo(o.denumire);
    }
}
