package tax_system;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class MediumMarket extends Magazin {

    public MediumMarket (String nume)
    {
        super(nume);
    }

    public MediumMarket (String nume, Vector<Factura> facturi) {
        super (nume, facturi);
    }

    public TreeSet<String> getCategorii () {
        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < super.facturi.size(); ++i) {
            for (int j = 0; j < super.facturi.get(i).lista_produse.size(); ++j) {
                set.add(super.facturi.get(i).lista_produse.get(j).getProdus().getCategorie());
            }
        }
        return set;
    }

    public double getTotalCategorie (String categorie) {
        double total = 0;
        for (int i = 0; i < super.facturi.size(); ++i) {
            for (int j = 0; j < super.facturi.get(i).lista_produse.size(); ++j) {
                if (categorie.equals(super.facturi.get(i).lista_produse.get(j).getProdus().getCategorie()))
                    total += super.facturi.get(i).lista_produse.get(j).getCantitate()
                            * (super.facturi.get(i).lista_produse.get(j).getCantitate()
                            + super.facturi.get(i).lista_produse.get(j).getTaxa());
            }
        }
        return total;
    }

    @Override
    public double calculScutiriTaxe() {
        TreeSet<String> categorii = this.getCategorii();
        Iterator<String> iter = categorii.iterator();
        while (iter.hasNext()) {
            String categorie_produs = iter.next();
            if (this.getTotalCategorie(categorie_produs) >= this.getTotalCuTaxe() / 2)
                return 0.05;
        }
        return 0;
    }
}
