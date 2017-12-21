package tax_system;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;

public class Produs {
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

}
