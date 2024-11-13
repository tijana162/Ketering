package beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.KategorijaRepository;

public class Kategorija implements Serializable {

    private String program;
    private String nazivKategorije;
    private int kategorijaID;

    public Kategorija() {
    }

    public Kategorija(int kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public Kategorija(String program, String nazivKategorije, int kategorijaID) {
        this.program = program;
        this.nazivKategorije = nazivKategorije;
        this.kategorijaID = kategorijaID;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public int getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(int kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    //Static metode
    // Vraca listu kategorija po zadatom kriterijumu ( in 'slatki', 'slani' )
    public static List<Kategorija> filterKategorije(List<Kategorija> sveKat, String kriterijum) {
        List<Kategorija> filtriraneKategorije = new ArrayList<>();
        for (Kategorija k : sveKat) {
            if (k.getProgram().equals(kriterijum)) {
                filtriraneKategorije.add(k);
            }
        }
        return filtriraneKategorije;
    }

    // Vraca listu svih kategorija iz baze
    public static List<Kategorija> sveKategorije() throws SQLException {
        KategorijaRepository repository = new KategorijaRepository();
        try {
            return repository.getSve();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
    
    //NonStatic metode
    
    //predaje kategoriju repositoriju za promenu u bazi
    public void izmeniKategoriju(Kategorija izmene) throws SQLException {
        KategorijaRepository repository = new KategorijaRepository();
        try {
            repository.izmeni(this, izmene);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    //predaje kategoriju repositoriju za dodavanje u bazu
    public void dodajKategoriju() throws SQLException{
        KategorijaRepository repository = new KategorijaRepository();
        try{
            repository.dodaj(this);
        }catch(SQLException sqle){
            throw sqle;
        }
    }

    //predaje kategoriju repositoriju za brisanje iz baze
    public void izbrisiKategoriju() throws SQLException{
        KategorijaRepository repository = new KategorijaRepository();
        try{
            repository.izbrisi(this);
        }catch(SQLException sqle){
            throw sqle;
        }
    }

}
