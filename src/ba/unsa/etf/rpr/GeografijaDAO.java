package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.*;

import static java.util.Comparator.comparingInt;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private Connection connection;
   // jdbc:sqlite:baza.db
    private String url = "jdbc:sqlite:baza.db";
    private Statement statement;

    public GeografijaDAO() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            statement.executeQuery("select * from grad");
            
        } catch (SQLException e) {
            String upit1 = "create table grad(id integer, naziv text, broj_stanovnika integer);";
            String upit2 = "create table drzava(id integer primary key, naziv text, glavni_grad references grad(id));";
            String upit3 = "alter table grad add drzava integer references drzava(id);";

            try {
                Statement st = connection.createStatement();
                st.execute(upit1);
                st.execute(upit2);
                st.execute(upit3);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
             Grad pariz = new Grad(-1,null, "Pariz", 2206488);
             Grad london = new Grad(-1,null, "London", 8825000);
             Grad bech = new Grad(-1,null, "Beč", 1899055);
             Grad manchester = new Grad(-1,null, "Manchester", 545500);
             Grad graz = new Grad(-1,null, "Graz", 280200);

             Drzava francuska = new Drzava(-1, pariz,"Francuska");
             Drzava velikaBritanija = new Drzava(-1, london, "Velika Britanija");
             Drzava austrija = new Drzava(-1, bech, "Austrija");

             pariz.setDrzava(francuska);
             london.setDrzava(velikaBritanija);
             bech.setDrzava(austrija);
             manchester.setDrzava(velikaBritanija);
             graz.setDrzava(austrija);

             dodajGrad(pariz);
             dodajGrad(london);
             dodajGrad(bech);
             dodajGrad(manchester);
             dodajGrad(graz);
             dodajDrzavu(francuska);
             dodajDrzavu(velikaBritanija);
             dodajDrzavu(austrija);
         //   e.printStackTrace();
        }
    }
    private static void initialize() throws SQLException {
        instance = new GeografijaDAO();
    }
    public static GeografijaDAO getInstance() throws SQLException {
        if(instance == null) initialize();
        return instance;
    }
    public static void removeInstance() {
        instance = null;
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        String upit = "SELECT g.id, g.naziv, g.broj_stanovnika, d.id, d.naziv FROM grad g, drzava d where g.drzava = d.id";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int gradId = rs.getInt(1);
                String imeGrada= rs.getString(2);
                int brojStan = rs.getInt(3);
                int drzavaId = rs.getInt(4);
                String imeDrzave = rs.getString(5);
                Grad g = new Grad(gradId, null, imeGrada, brojStan);
                Drzava d = new Drzava(drzavaId, g, imeDrzave);
                g.setDrzava(d);
                gradovi.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //sortirano u rastucem redoslijedu; treba po opadajucem
        gradovi.sort(Comparator.comparingInt(Grad::getBrojStanovnika));
        Collections.reverse(gradovi);
        return gradovi;
    }
    public Grad glavniGrad(String drzava) {
        ArrayList<Grad> grads = gradovi();
        if(grads == null)
            return null;
        for(Grad g : grads) {
            if(g.getDrzava().getNaziv().equals(drzava)) {
                return g;
            }
        }
        return null;
    }

    public Drzava nadjiDrzavu(String drzava) {
        String query = "select d.id, d.naziv, d.glavni_grad from drzava d where d.naziv=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drzava);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isClosed())
                return null;

            int id = resultSet.getInt(1);
            int gGrad = resultSet.getInt(2);
            return new Drzava(id, glavniGrad(drzava), drzava);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void obrisiDrzavu(String drzava) {
        String upit = "DELETE FROM drzava WHERE naziv = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(upit);
            preparedStatement.setString(1, drzava);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajGrad(Grad g) {
        String upit = "INSERT INTO grad VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setInt(1, g.getId());
            ps.setString(2, g.getNaziv());
            ps.setInt(3, g.getBrojStanovnika());
            ps.setInt(4, g.getDrzava().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad) {
        String upit = "update grad set naziv = ?, broj_stanovnika = ?, drzava = ? where id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setString(1, grad.getNaziv());
            ps.setInt(2, grad.getBrojStanovnika());
            ps.setInt(3, grad.getDrzava().getId());
            ps.setInt(4, grad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dodajDrzavu(Drzava drzava) {
        String query = "insert into drzava values (?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, drzava.getId());
            preparedStatement.setString(2, drzava.getNaziv());
            preparedStatement.setInt(3, drzava.getGlavniGrad().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
