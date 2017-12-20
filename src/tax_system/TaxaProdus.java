package tax_system;

import java.util.List;

public class TaxaProdus {
    private String denumire, taraOrigine;
    private double taxa;
    public TaxaProdus (String denumire, String taraOrigine, double taxa)
    {
        this.denumire = denumire;
        this.taraOrigine = taraOrigine;
        this.taxa = taxa;
    }

    public void setDenumire (String denumire) {
        this.denumire = denumire;
    }

    public void setTaraOrigine (String taraOrigine) {
        this.taraOrigine = taraOrigine;
    }

    public void setTaxa (int taxa) {
        this.taxa = taxa;
    }

    public String getDenumire () {
        return this.denumire;
    }

    public String getTaraOrigine () {
        return this.taraOrigine;
    }

    public double getTaxa () {
        return this.taxa;
    }

    public String toString () {
        String result = "Denumire: " + this.denumire + "\nTara origine: " + this.taraOrigine + "\nTaxa: "
                + this.taxa + "\n\n";
        return result;
    }
}
