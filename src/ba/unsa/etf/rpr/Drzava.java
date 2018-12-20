package ba.unsa.etf.rpr;

public class Drzava {
    int id;
    Grad glavniGrad;
    String naziv;
    Drzava( Grad glavniGrad, String naziv) {
       // setId(id);
        setGlavniGrad(glavniGrad);
        setNaziv(naziv);
    }

    public Drzava() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }
}
