package repository;

import beans.Korisnik;
import beans.Narudzbina;
import beans.Proizvod;
import database.ConnectionManager;
import interfaces.IRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NarudzbinaRepository implements IRepository<Narudzbina> {

    /*
    Dodaje predatu narudzbinu u bazu.
    Radi Batch insert u StavkeNarudzbine svih entrija iz hashmape
    */
    @Override
    public void dodaj(Narudzbina zaDodavanje) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sqlNarudzbina = "INSERT INTO `narudzbine`( `KorisnickoIme`, `DatumKreiranja`, `DatumOstvarivanja`, `Status`, `UkupnaCena`, `Popust`) "
                + "VALUES (?,?,?,?,?,?)";
        String sqlStavke = "INSERT INTO `stavkenarudzbine`(`ProizvodID`, `NarudzbinaID`, `Kolicina`)"
                + " VALUES (?,?,?)";
        Date trenutniDatum = new Date();
        java.sql.Date sqlDate = new java.sql.Date(trenutniDatum.getTime());
        //Dodavanje narudzbe
        try ( PreparedStatement stmt = con.prepareStatement(sqlNarudzbina, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, zaDodavanje.getKorisnik().getKorisnickoIme());
            stmt.setDate(2, sqlDate);
            stmt.setDate(3, null);
            stmt.setInt(4, 0);
            stmt.setInt(5, zaDodavanje.getUkupnaCena());
            stmt.setInt(6, zaDodavanje.getPopust());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                zaDodavanje.setNarudzbinaID(rs.getInt(1));
            }
        } catch (SQLException sqle) {
            con.close();
            throw sqle;
        }
        //Dodavanje stavki batch
        try ( PreparedStatement stmt = con.prepareStatement(sqlStavke)) {
            for (HashMap.Entry<Proizvod, Integer> stavka : zaDodavanje.getStavkeNarudzbine().entrySet()) {
                stmt.setInt(1, stavka.getKey().getProizvodID());
                stmt.setInt(2, zaDodavanje.getNarudzbinaID());
                stmt.setInt(3, stavka.getValue());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }

    }

    /*
    Vraca sve redove narudzbina iz baze
    */
    @Override
    public List<Narudzbina> getSve() throws SQLException{
               Connection con = ConnectionManager.getConnection();
        String sqlNarudzba = "SELECT `NarudzbinaID`,`KorisnickoIme`,`DatumKreiranja`, `DatumOstvarivanja`,`Status`, `UkupnaCena`, `Popust` "
                + "FROM `narudzbine` WHERE 1";
        String sqlStavke = "SELECT `proizvodi`.`ProizvodID`, `Kolicina`, `NazivProizvoda`, `CenaPoPorciji`"
                + "FROM `stavkenarudzbine` "
                + "INNER JOIN `proizvodi` ON `stavkenarudzbine`.`ProizvodID` = `proizvodi`.`ProizvodID` "
                + "WHERE `NarudzbinaID` = ?";
        List<Narudzbina> rezultat = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Puni listu narudzbi
        try ( PreparedStatement stmt = con.prepareStatement(sqlNarudzba)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String datumOstvarivanja;
                if (rs.getDate("DatumOstvarivanja") == null) {
                    datumOstvarivanja = "";
                } else {
                    datumOstvarivanja = dateFormat.format(rs.getDate("DatumOstvarivanja"));
                }
                Narudzbina nar = new Narudzbina(dateFormat.format(rs.getDate("DatumKreiranja")),
                        datumOstvarivanja,
                        rs.getInt("Status"),
                        new Korisnik(rs.getString("KorisnickoIme")),
                        rs.getInt("NarudzbinaID"),
                        rs.getInt("Popust"),
                        rs.getInt("UkupnaCena"),
                        new HashMap<Proizvod, Integer>());
                rezultat.add(nar);
            }
            rs.close();
        } catch (SQLException sqle) {
            con.close();
            throw sqle;
        }

        //puni hash mapu svake narudzbe
        for (Narudzbina nar : rezultat) {
            try ( PreparedStatement stmt = con.prepareStatement(sqlStavke)) {
                stmt.setInt(1, nar.getNarudzbinaID());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Proizvod prod = new Proizvod(rs.getInt("ProizvodID"));
                    prod.setCenaPoPorciji(rs.getInt("CenaPoPorciji"));
                    prod.setNazivProizvoda(rs.getString("NazivProizvoda"));
                    nar.dodajProizvod(prod, rs.getInt("Kolicina"));
                }
                rs.close();
            } catch (SQLException sqle) {
                con.close();
                throw sqle;
            }
        }

        con.close();
        return rezultat;
    }
    
    /* 
      Menja stariT row u bazi zadatim noviT
      Ne radi update `stavkenarudzbina`, 
      ukoliko dodje do menjanja NarudzbinaID FK u bazi je OnUpdate Cascade pa se promena vrsi na nivou baze
    */
    @Override
    public void izmeni(Narudzbina stariT, Narudzbina noviT) throws SQLException{
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE `narudzbine` "
                + "SET `NarudzbinaID`=?,`KorisnickoIme`=?,`DatumKreiranja`=?,`DatumOstvarivanja`=?,`Status`=?,`UkupnaCena`=?,`Popust`=? "
                + "WHERE NarudzbinaID = ?";

        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, noviT.getNarudzbinaID());
            stmt.setString(2, noviT.getKorisnik().getKorisnickoIme());
            stmt.setDate(3, java.sql.Date.valueOf(noviT.getDatumKreiranja()));
            
            if(noviT.getDatumOstvarivanja().equals("")){
                stmt.setDate(4, null);
            } else{
                stmt.setDate(4, java.sql.Date.valueOf(noviT.getDatumOstvarivanja()));
            }
            stmt.setInt(5, noviT.getStatus());
            stmt.setInt(6, noviT.getUkupnaCena());
            stmt.setInt(7, noviT.getPopust());
            
            stmt.setInt(8, stariT.getNarudzbinaID());
            
            stmt.executeUpdate();        
        }catch(SQLException sqle){
            throw sqle;
        }finally{
            con.close();
        }
    }

    // Ne koristi se.
    @Override
    public void izbrisi(Narudzbina zaBrisanje) {
        throw new UnsupportedOperationException();
    }

    // Vraca puni objekat narudzbine iz baze po ID-u trazenog.
    @Override
    public Narudzbina getJedan(Narudzbina trazeni) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT `NarudzbinaID`, `KorisnickoIme`, `DatumKreiranja`, `DatumOstvarivanja`, `Status`, `UkupnaCena`, `Popust` "
                + "FROM `narudzbine` WHERE `NarudzbinaID` = ?";
        String sqlStavke = "SELECT `proizvodi`.`ProizvodID`, `Kolicina`, `NazivProizvoda`, `CenaPoPorciji`"
                + "FROM `stavkenarudzbine` "
                + "INNER JOIN `proizvodi` ON `stavkenarudzbine`.`ProizvodID` = `proizvodi`.`ProizvodID` "
                + "WHERE `NarudzbinaID` = ?";
        Narudzbina rezultat = null;

        // Puni narudzbinu
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, trazeni.getNarudzbinaID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String datumOstvarivanja;
                if (rs.getDate("DatumOstvarivanja") == null) {
                    datumOstvarivanja = "";
                } else {
                    datumOstvarivanja = rs.getDate("DatumOstvarivanja").toString();
                }
                rezultat = new Narudzbina(rs.getDate("DatumKreiranja").toString(),
                        datumOstvarivanja,
                        rs.getInt("Status"),
                        new Korisnik(rs.getString("KorisnickoIme")),
                        rs.getInt("NarudzbinaID"),
                        rs.getInt("Popust"),
                        rs.getInt("UkupnaCena"),
                        new HashMap<Proizvod, Integer>());
            }
            rs.close();
        } catch (SQLException sqle) {
            con.close();
            throw sqle;
        }

        //Puni stavke
        try ( PreparedStatement stmt = con.prepareStatement(sqlStavke)) {
            stmt.setInt(1, rezultat.getNarudzbinaID());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Proizvod prod = new Proizvod(rs.getInt("ProizvodID"));
                prod.setCenaPoPorciji(rs.getInt("CenaPoPorciji"));
                prod.setNazivProizvoda(rs.getString("NazivProizvoda"));
                rezultat.dodajProizvod(prod, rs.getInt("Kolicina"));
            }
            rs.close();
        } catch (SQLException sqle) {
            con.close();
            throw sqle;
        }
        
        con.close();
        return rezultat;
    }

    
    /* 
      Metoda nije deo IRepository implementacije
    
      Vraca listu objekata narudzbine, sa napunjenom hash mapom relevantnim proizvodima 
      svih narudzbina iz baze koje je napravio predati korisnik(FK KorisnickoIme) 
    */
    public List<Narudzbina> getSveOdKorisnika(Korisnik korisnik) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sqlNarudzba = "SELECT `NarudzbinaID`,`DatumKreiranja`, `DatumOstvarivanja`,`Status`, `UkupnaCena`, `Popust` "
                + "FROM `narudzbine` WHERE KorisnickoIme = ?";
        String sqlStavke = "SELECT `proizvodi`.`ProizvodID`, `Kolicina`, `NazivProizvoda`, `CenaPoPorciji`"
                + "FROM `stavkenarudzbine` "
                + "INNER JOIN `proizvodi` ON `stavkenarudzbine`.`ProizvodID` = `proizvodi`.`ProizvodID` "
                + "WHERE `NarudzbinaID` = ?";
        List<Narudzbina> rezultat = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Puni listu narudzbi
        try ( PreparedStatement stmt = con.prepareStatement(sqlNarudzba)) {
            stmt.setString(1, korisnik.getKorisnickoIme());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String datumOstvarivanja;
                if (rs.getDate("DatumOstvarivanja") == null) {
                    datumOstvarivanja = "";
                } else {
                    datumOstvarivanja = dateFormat.format(rs.getDate("DatumOstvarivanja"));
                }
                Narudzbina nar = new Narudzbina(dateFormat.format(rs.getDate("DatumKreiranja")),
                        datumOstvarivanja,
                        rs.getInt("Status"),
                        korisnik,
                        rs.getInt("NarudzbinaID"),
                        rs.getInt("Popust"),
                        rs.getInt("UkupnaCena"),
                        new HashMap<Proizvod, Integer>());
                rezultat.add(nar);
            }
            rs.close();
        } catch (SQLException sqle) {
            con.close();
            throw sqle;
        }

        //puni hash mapu svake narudzbe
        for (Narudzbina nar : rezultat) {
            try ( PreparedStatement stmt = con.prepareStatement(sqlStavke)) {
                stmt.setInt(1, nar.getNarudzbinaID());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Proizvod prod = new Proizvod(rs.getInt("ProizvodID"));
                    prod.setCenaPoPorciji(rs.getInt("CenaPoPorciji"));
                    prod.setNazivProizvoda(rs.getString("NazivProizvoda"));
                    nar.dodajProizvod(prod, rs.getInt("Kolicina"));
                }
                rs.close();
            } catch (SQLException sqle) {
                con.close();
                throw sqle;
            }
        }

        con.close();
        return rezultat;
    }
}


