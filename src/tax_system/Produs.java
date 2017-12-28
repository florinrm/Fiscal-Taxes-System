package tax_system;

public class Produs implements Comparable<Produs>{
    private String denumire, categorie, taraOrigine;
    private double pret;

    public Produs (String denumire, String categorie, String taraOrigine, double pret)
    {
        this.denumire = denumire;
        this.categorie = categorie;
        this.taraOrigine = taraOrigine;
        this.pret = pret;
    }

    public double getPret() {
        return this.pret;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public String getTaraOrigine() {
        return this.taraOrigine;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public void setTaraOrigine(String taraOrigine) {
        this.taraOrigine = taraOrigine;
    }

    public int compareTo (Produs prod) {
        if (this.getCategorie().equals(prod.getCategorie()))
            return this.getDenumire().compareTo(prod.getDenumire());
        else
            return this.getCategorie().compareTo(prod.getCategorie());
    }

    public String toString () {
        String result = "Nume: " + this.denumire + "\n" + "Categorie: " + this.categorie + "\n";
        result += "Pret: " + this.pret + "\nTara origine: " + this.taraOrigine + "\n\n";
        return result;
    }

}
