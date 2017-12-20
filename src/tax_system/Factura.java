package tax_system;

import java.util.Vector;

public class Factura {
    public String denumire;
    public Vector<ProdusComandat> lista_produse;

    public Factura (String denumire, Vector<ProdusComandat> lista_produse) {
        this.denumire = denumire;
        this.lista_produse = lista_produse;
    }

    public void addProdus (ProdusComandat produs) {
        this.lista_produse.add(produs);
    }

    public void removeProdus (ProdusComandat produs) {
        this.lista_produse.remove(produs);
    }

    public String toString () {
        String result = "Nume factura: " + this.denumire + "\n";
        for (int i = 0; i < this.lista_produse.size(); ++i) {
            result += this.lista_produse.get(i) + "\n";
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
            total += this.lista_produse.get(i).getTaxa();
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
                total += this.lista_produse.get(i).getTaxa() * this.lista_produse.get(i).getCantitate();
        }
        return total;
    }

    public double getTotalTaraCuTaxe (String country) {
        return this.getTotalTaraFaraTaxe (country) + this.getTaxeTara (country);
    }
}
