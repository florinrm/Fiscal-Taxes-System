package tax_system;

import java.util.TreeSet;
import java.util.Vector;

public class HyperMarket extends Magazin {

    public HyperMarket (String nume, Vector<Factura> facturi, TreeSet<String> tariOrigine) {
        super (nume, facturi, tariOrigine);
    }

    @Override
    public double calculScutiriTaxe() {
        for (int i = 0; i < super.facturi.size(); ++i) {
            if (super.facturi.get(i).getTotalCuTaxe() >= super.getTotalCuTaxe() * 10 / 100) {
                return 0.01; // procentajul
            }
        }
        return 0;
    }
}
