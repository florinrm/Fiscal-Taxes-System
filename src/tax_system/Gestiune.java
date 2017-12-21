package tax_system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public final class Gestiune {
    public ArrayList<Produs> produse;
    public ArrayList<Magazin> magazine;
    public TreeMap<String, HashMap<String, Double>> taxes;

    private Gestiune () {
        this.produse = new ArrayList<>();
        this.magazine = new ArrayList<>();
        this.taxes = new TreeMap<>();
    };

    public void setProduse (ArrayList<Produs> produse) {
        this.produse = produse;
    }

    public void setMagazine (ArrayList<Magazin> magazine) {
        this.magazine = magazine;
    }

    public void setTaxes (TreeMap<String, HashMap<String, Double>> taxes) {
        this.taxes = taxes;
    }

    public String toString () {
        String result = "";
        for (int i = 0; i < this.magazine.size(); ++i) {

        }
        return result;
    }

    private static final Gestiune obj = new Gestiune();

    public static Gestiune getInstance ()
    {
        return obj;
    }
}
