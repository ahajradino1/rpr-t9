package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.*;

import static java.util.Comparator.comparingInt;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private Connection connection;
    private String url = "jdbc:sqlite:C:\\Users\\Korisnik\\IdeaProjects\\rpr-t9\\resources\\baza.db";
    private Statement statement;

    public GeografijaDAO() {
        try {

            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Grad glavniGrad(String drzava) {

        String upit = "SELECT  g.naziv, g.broj_stanovnika FROM grad g, drzava d WHERE d.naziv = ? AND g.id = d.glavni_grad";

        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setString(1,drzava);
            ResultSet rs = ps.executeQuery();
            String imeGrada = null;
            int brojStan = 0;
            while(rs.next()) {

                imeGrada= rs.getString(1);
                brojStan = rs.getInt(2);
            }
            return new Grad(null, imeGrada, brojStan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obrisiDrzavu(String drzava) {
     //   String upit = "DELETE FROM drzava where drzava.naziv = ?";
        String upit = "SELECT glavni_grad from drzava where drzava.naziv = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(upit);
            preparedStatement.setString(1, drzava);
            ResultSet resultSet = preparedStatement.executeQuery();
            int glavniGradId = 0;
            while(resultSet.next()) {
                glavniGradId = resultSet.getInt("glavni_grad");
            }

            String upit1 = "DELETE FROM grad WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(upit1);
            ps.setInt(1, glavniGradId);
            ps.executeUpdate();

            String upit2 = "DELETE FROM drzava WHERE naziv = ?";
            PreparedStatement ps1 = connection.prepareStatement(upit2);
            ps1.setString(1, drzava);
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajGrad(Grad g) {
        String upit = "INSERT INTO grad (id, naziv, broj_stanovnika, drzava) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setString(2, g.getNaziv());
            ps.setInt(3, g.getBrojStanovnika());
            ps.setInt(4, g.getDrzava().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad bech) {
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> cities = new ArrayList<>();
        String upit = "SELECT naziv, broj_stanovnika FROM grad";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String imeGrada= rs.getString(1);
                int brojStan = rs.getInt(2);
                cities.add(new Grad(null, imeGrada, brojStan));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //sortirano u rastucem redoslijedu; treba po opadajucem
     //   cities.sort(Comparator.comparingInt(Grad::getBrojStanovnika));

        return cities;
    }

    public void dodajDrzavu(Drzava drzava) {
        String upit = "INSERT INTO drzava (id, naziv, glavni_grad) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setString(2, drzava.getNaziv());

            String upit1 = "SELECT g.id FROM grad g, drzava d WHERE d.naziv = ? AND g.drzava = d.id";
            PreparedStatement ps1 = connection.prepareStatement(upit1);
            ps1.setString(1, drzava.getNaziv());
            ResultSet rs = ps.executeQuery();
            int id = 0;
            while(rs.next()) {
                id = rs.getInt(1);
            }

            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String francuska) {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
