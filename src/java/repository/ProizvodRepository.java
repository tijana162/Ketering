package repository;

import beans.Kategorija;
import beans.Proizvod;
import database.ConnectionManager;
import interfaces.IRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProizvodRepository implements IRepository<Proizvod> {

    //Dodaje proizvod u bazu
    @Override
    public void dodaj(Proizvod zaDodavanje) throws SQLException{
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO `proizvodi`(`NazivProizvoda`, `Opis`, `Slika`, `CenaPoPorciji`, `KategorijaID`) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,zaDodavanje.getNazivProizvoda());
            stmt.setString(2,zaDodavanje.getOpis());
            stmt.setString(3,zaDodavanje.getSlika());
            stmt.setInt(4,zaDodavanje.getCenaPoPorciji());
            stmt.setInt(5, zaDodavanje.getKategorija().getKategorijaID());
            
            stmt.executeUpdate();
        }catch(SQLException sqle){
            throw sqle;
        }finally{
            con.close();
        }
    }

    // Vraca ArrayList svih proizvoda iz baze i odgovarajuce kategorije inner join-om
    @Override
    public List<Proizvod> getSve() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        List<Proizvod> svi = new ArrayList<>();
        String sql = "SELECT `ProizvodID`, `NazivProizvoda`, `Opis`, `Slika`, `CenaPoPorciji`, proizvodi.`KategorijaID`, `NazivKategorije`, `Program`"
                + " FROM `proizvodi` INNER JOIN `kategorije` ON proizvodi.KategorijaID = kategorije.KategorijaID"
                + " WHERE 1 AND `ProizvodID` != 0";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                svi.add(new Proizvod(rs.getInt("ProizvodID"),
                        rs.getInt("CenaPoPorciji"),
                        new Kategorija(rs.getString("Program"), rs.getString("NazivKategorije"), rs.getInt("KategorijaID")),
                        rs.getString("NazivProizvoda"),
                        rs.getString("Opis"),
                        rs.getString("Slika")));
            }
            rs.close();
            return svi;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

    
    //Menja row u bazi zadatim informacijama
    @Override
    public void izmeni(Proizvod stariT, Proizvod noviT) throws SQLException{
        Connection con = ConnectionManager.getConnection();
        String sql = "UPDATE `proizvodi` SET `NazivProizvoda`=?,`Opis`=?,`Slika`=?,`CenaPoPorciji`=?,`KategorijaID`=? "
                    + "WHERE `ProizvodID` = ?";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, noviT.getNazivProizvoda());
            stmt.setString(2, noviT.getOpis());
            stmt.setString(3, noviT.getSlika());
            stmt.setInt(4, noviT.getCenaPoPorciji());
            stmt.setInt(5, noviT.getKategorija().getKategorijaID());
            
            stmt.setInt(6, stariT.getProizvodID());
            
            stmt.executeUpdate();
        }catch(SQLException sqle){
            throw sqle;
        }finally{
            con.close();
        }
    }

    //Brise row iz baze
    @Override
    public void izbrisi(Proizvod zaBrisanje) throws SQLException{
        Connection con = ConnectionManager.getConnection();
        String sql = "DELETE FROM `proizvodi` WHERE `ProizvodID` = ?";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, zaBrisanje.getProizvodID());            
            stmt.executeUpdate();
        }catch(SQLException sqle){
            throw sqle;
        }finally{
            con.close();
        }             
    }

    //Vraca korisnik row iz baze po predatom trazeni.KorisnickoIme
    @Override
    public Proizvod getJedan(Proizvod trazeni) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT `ProizvodID`, `NazivProizvoda`, `Opis`, `Slika`, `CenaPoPorciji`, proizvodi.`KategorijaID`, `NazivKategorije`, `Program` "
                + "FROM `proizvodi` INNER JOIN `kategorije` ON proizvodi.KategorijaID = kategorije.KategorijaID "
                + "WHERE `ProizvodID` = ?";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, trazeni.getProizvodID());
            ResultSet rs = stmt.executeQuery();
            Proizvod nadjeni = null;
            if (rs.next()) {
                nadjeni = new Proizvod(rs.getInt("ProizvodID"),
                        rs.getInt("CenaPoPorciji"),
                        new Kategorija(rs.getString("Program"), rs.getString("NazivKategorije"), rs.getInt("KategorijaID")),
                        rs.getString("NazivProizvoda"),
                        rs.getString("Opis"),
                        rs.getString("Slika"));
            }
            rs.close();
            return nadjeni;
        } catch (Exception sqle) {
            throw sqle;
        } finally {
            con.close();
        }
    }

}
