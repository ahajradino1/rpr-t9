package ba.unsa.etf.rpr;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        GeografijaDAO.getInstance();
        System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();
        GeografijaDAO.removeInstance();
    }

    public static void glavniGrad() throws SQLException {
        System.out.println("Unesite naziv drzave: ");
        Scanner sc = new Scanner(System.in);
        String nazivDrzave = sc.next();
        Grad grad = GeografijaDAO.getInstance().glavniGrad(nazivDrzave);
        if(grad == null)
            System.out.println("Nepostojeća država");
        System.out.println("Glavni grad države " + nazivDrzave + " je " + grad.getNaziv() + ".");
    }

    public static String ispisiGradove() throws SQLException {
        ArrayList<Grad> grads = GeografijaDAO.getInstance().gradovi();
        String s = "";
        for(Grad grad : grads)
            s += grad.getNaziv() + "(" + grad.getDrzava().getNaziv() + ") - " + grad.getBrojStanovnika() + "\n";
        return  s;
    }
}
