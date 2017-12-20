package tax_system;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class MiniMarket extends Magazin {

    public MiniMarket (String nume)
    {
        super(nume);
    }

    public MiniMarket (String nume, Vector <Factura> facturi) {
        super (nume, facturi);
    }

    public TreeSet<String> getTariOrigine () {
        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < super.facturi.size(); ++i) {
            for (int j = 0; j < super.facturi.get(i).lista_produse.size(); ++j) {
                set.add(super.facturi.get(i).lista_produse.get(j).getProdus().getTaraOrigine());
            }
        }
        return set;
    }

    public double getTotalTariOrigine (String categorie) {
        double total = 0;
        for (int i = 0; i < super.facturi.size(); ++i) {
            for (int j = 0; j < super.facturi.get(i).lista_produse.size(); ++j) {
                if (categorie.equals(super.facturi.get(i).lista_produse.get(j).getProdus().getTaraOrigine()))
                    total += super.facturi.get(i).lista_produse.get(j).getCantitate()
                            * (super.facturi.get(i).lista_produse.get(j).getCantitate()
                            + super.facturi.get(i).lista_produse.get(j).getTaxa());
            }
        }
        return total;
    }

    @Override
    public double calculScutiriTaxe() {
        TreeSet<String> tariOrigine = this.getTariOrigine();
        Iterator<String> iter = tariOrigine.iterator();
        while (iter.hasNext()) {
            String tara_origine = iter.next();
            if (this.getTotalTariOrigine(tara_origine) >= this.getTotalCuTaxe() / 2)
                return 0.1;
        }
        return 0;
    }
}
