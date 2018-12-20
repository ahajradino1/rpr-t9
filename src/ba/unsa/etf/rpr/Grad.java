package ba.unsa.etf.rpr;

public class Grad implements Comparable<Grad> {
    Drzava drzava;
    String naziv;
    int brojStanovnika, id;

    public Grad ( Drzava drzava, String naziv, int brojStanovnika) {
   //     setId(id);
        setBrojStanovnika(brojStanovnika);
        setDrzava(drzava);
        setNaziv(naziv);
    }

    public Grad() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }


    @Override
    public int compareTo(Grad o) {
        Integer c1 = o.brojStanovnika;
        Integer c2 = brojStanovnika;
        Integer poredak = c2 - c1;
        if(poredak != 0) return poredak;
        return c1.compareTo(c2);
    }
}
