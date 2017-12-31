package tax_system;

import java.util.*;

public final class Gestiune {
    public Vector<Produs> produse = new Vector<>();
    public ArrayList<Magazin> magazine = new ArrayList<>();
    public HashMap<String, HashMap<String, Double>> taxes = new HashMap<>();
    public TreeSet<String> tipuriMagazine = new TreeSet<>();

    private Gestiune () {
    };

    public void setProduse (Vector<Produs> produse) {
        this.produse = produse;
    }

    public void setMagazine (ArrayList<Magazin> magazine) {
        this.magazine = magazine;
    }

    public void setTaxes (HashMap<String, HashMap<String, Double>> taxes) {
        this.taxes = taxes;
    }

    public void setTipuriMagazine (TreeSet<String> tipuriMagazine) {
        this.tipuriMagazine = tipuriMagazine;
    }

    // asa vom afisa in out.txt
    public String toString () {
        String result = "";
        Iterator iter = tipuriMagazine.iterator(); // iteram prin tipurile de magazine pentru out.txt
        Collections.sort(magazine);
        while (iter.hasNext()) {
            String type = iter.next().toString();
            result += type + "\n";
            for (int i = 0; i < magazine.size(); ++i) {
                // parsam tipul magazinului (in interiorul clasei imi erau aruncate exceptii cand faceam parsarea
                // asa ca o fac aici
                if (type.equals(magazine.get(i).type.split(" ")[1].split("_")[1].substring(7)))
                    result += magazine.get(i) + "\n";
            }
        }
        return result;
    }

    // Singleton pattern
    private static final Gestiune obj = new Gestiune();
    public static Gestiune getInstance ()
    {
        return obj;
    }
}
