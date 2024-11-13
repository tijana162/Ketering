package repository;

import beans.Kategorija;
import database.ConnectionManager;
import interfaces.IRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategorijaRepository implements IRepository<Kategorija> {

    //Dodaje nov row u bazu
    @Override
    public void dodaj(Kategorija zaDodavanje) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO `kategorije`(`NazivKategorije`, `Program`) VALUES (?,?)";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, zaDodavanje.getNazivKategorije());
            stmt.setString(2, zaDodavanje.getProgram());

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

    //Vraca listu kategorija iz baze
    @Override
    public List<Kategorija> getSve() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT `KategorijaID`, `NazivKategorije`, `Program` FROM `kategorije` WHERE `KategorijaID` != 0";
        List<Kategorija> kategorije = new ArrayList<>();
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                kategorije.add(new Kategorija(rs.getString("Program"), rs.getString("NazivKategorije"), rs.getInt("KategorijaID")));
            }
            rs.close();
            return kategorije;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

    //Menja row sa stariT.ID u bazi sa informacijama iz noviT
    @Override
    public void izmeni(Kategorija stariT, Kategorija noviT) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE `kategorije` SET `NazivKategorije`=?,`Program`=? WHERE `KategorijaID` = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, noviT.getNazivKategorije());
            stmt.setString(2, noviT.getProgram());

            stmt.setInt(3, stariT.getKategorijaID());
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

    // Brise row iz baze, poziva se Before Delete trigger za menjanje kategorija proizvodima sa izbrisanom kategorijom
    @Override
    public void izbrisi(Kategorija zaBrisanje) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM `kategorije` WHERE `KategorijaID` = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, zaBrisanje.getKategorijaID());
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

    //Ne koristi se.
    @Override
    public Kategorija getJedan(Kategorija trazeni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
