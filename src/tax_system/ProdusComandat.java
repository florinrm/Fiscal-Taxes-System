package tax_system;

public class ProdusComandat {
    private Produs produs;
    private double taxa;
    private int cantitate;

    public ProdusComandat (Produs produs, double taxa, int cantitate)
    {
        this.cantitate = cantitate;
        this.produs = produs;
        this.taxa = taxa;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public Produs getProdus() {
        return this.produs;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getCantitate() {
        return this.cantitate;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getTaxa() {
        return this.taxa;
    }

    @Override
    public String toString() {
        String result = "Produs: " + this.produs + "\nTaxa: " + this.taxa + "\nCantitate: " + this.cantitate + "\n";
        return result;
    }
}
