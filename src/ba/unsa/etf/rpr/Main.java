package ba.unsa.etf.rpr;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        GeografijaDAO dao = new GeografijaDAO();
        DatabaseMetaData dm = (DatabaseMetaData) dao.getConnection().getMetaData(); System.out.println("Driver name: " + dm.getDriverName()); System.out.println("Driver version: " + dm.getDriverVersion());
      //  System.out.println("Gradovi su:\n" + ispisiGradove());
      //  glavniGrad();
    }

    public static String ispisiGradove() {
        return  null;
    }
}
