package tax_system;

public interface IMagazin {
    public double getTotalFaraTaxe();
    public double getTotalCuTaxe();
    public double getTotalCuTaxeScutite();
    public double getTotalTaraFaraTaxe (String country);
    public double getTotalTaraCuTaxe (String country);
    public double getTotalTaraCuTaxeScutite (String country);
    public double calculScutiriTaxe ();
}
