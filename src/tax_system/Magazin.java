package tax_system;

import java.util.Vector;

public abstract class Magazin implements IMagazin {
    public String nume;
    public Vector<Factura> facturi;

    public Magazin (String nume)
    {
        this.nume = nume;
        this.facturi = new Vector<>();
    }

    public Magazin (String nume, Vector <Factura> facturi) {
        this.nume = nume;
        this.facturi = facturi;
    }

    public String toString () {
        String result = "Magazin: " + this.nume + "\n";
        for (int i = 0; i < this.facturi.size(); ++i) {
            result += this.facturi.get(i) + "\n";
        }
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
        if (this.calculScutiriTaxe() == 0)
            return this.getTotalCuTaxe();
        else
        {
            double scutire = this.getTotalCuTaxe() * this.calculScutiriTaxe() / 100;
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

    public abstract double calculScutiriTaxe ();
}
