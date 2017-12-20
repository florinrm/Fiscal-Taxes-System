package tax_system;

import java.util.ArrayList;

public final class Gestiune {
    public ArrayList<Produs> produse;
    public ArrayList<Magazin> magazine;

    private Gestiune () {
        this.produse = new ArrayList<>();
        this.magazine = new ArrayList<>();
    };

    private static final Gestiune obj = new Gestiune();

    public Gestiune getInstance ()
    {
        return obj;
    }
}
