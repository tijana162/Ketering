package beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.ProizvodRepository;

public class Proizvod implements Serializable {
    
    private int proizvodID;
    private int cenaPoPorciji;
    private Kategorija kategorija;
    private String nazivProizvoda;
    private String opis;
    private String slika;  // relative path do slike.

    public Proizvod() {
    }
    
    public Proizvod(int proizvodID) {  //Konstruktor za pretrage
        this.proizvodID = proizvodID;
    }
    
    public Proizvod(int proizvodID, int cenaPoPorciji, Kategorija kategorija, String nazivProizvoda, String opis, String slika) {
        this.proizvodID = proizvodID;
        this.cenaPoPorciji = cenaPoPorciji;
        this.kategorija = kategorija;
        this.nazivProizvoda = nazivProizvoda;
        this.opis = opis;
        this.slika = slika;
    }
    
    public int getProizvodID() {
        return proizvodID;
    }
    
    public void setProizvodID(int proizvodID) {
        this.proizvodID = proizvodID;
    }
    
    public int getCenaPoPorciji() {
        return cenaPoPorciji;
    }
    
    public void setCenaPoPorciji(int cenaPoPorciji) {
        this.cenaPoPorciji = cenaPoPorciji;
    }
    
    public Kategorija getKategorija() {
        return kategorija;
    }
    
    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
    
    public String getNazivProizvoda() {
        return nazivProizvoda;
    }
    
    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }
    
    public String getOpis() {
        return opis;
    }
    
    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public String getSlika() {
        return slika;
    }

    public String getSlikaPath() {
        return "./img/" + slika;
    }
    
    public void setSlika(String slika) {
        this.slika = slika;
    }

    // Sistemske operacije
    //  Static metode
    /* 
     Vraca listu proizvoda filtriranih po kategoriji ili po programu (in 'slani' 'slatki')
     Ako se pretrazuje po programu( != null ) kategorija ce biti ignorisana, u pozivima sam stavljao vrednost -1 simbolicno
     */
    public static List<Proizvod> filtrirajPonudu(List<Proizvod> proizvodi, String trazeniProgram, int trazenaKategorija) {
        List<Proizvod> filtriraniProizvodi = new ArrayList<>();
        if (trazeniProgram == null) {   // filtriranje je po kategoriji
            for (Proizvod p : proizvodi) {
                if (p.getKategorija().getKategorijaID() == trazenaKategorija) {
                    filtriraniProizvodi.add(p);
                }
            }
        } else // filtriranje je po programu
        {
            for (Proizvod p : proizvodi) {
                if (p.getKategorija().getProgram().equals(trazeniProgram)) {
                    filtriraniProizvodi.add(p);
                }
            }
        }
        return filtriraniProizvodi;
    }

    /*
    Vraca listu svih proizvoda iz baze
     */
    public static List<Proizvod> celaPonuda() throws SQLException {
        ProizvodRepository repository = new ProizvodRepository();
        try {
            return repository.getSve();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // NonStatic metode
    
    /*
     Predaje izmene repositoriju za promenu u bazi
     proizvoda sa this.proizvodID
     */
    public void izmeniProizvod(Proizvod izmene) throws SQLException {
        ProizvodRepository repository = new ProizvodRepository();
        try {
            Proizvod izBaze = repository.getJedan(this);
            izBaze.cenaPoPorciji = izmene.cenaPoPorciji;
            izBaze.opis = izmene.opis;
            izBaze.nazivProizvoda = izmene.nazivProizvoda;
            izBaze.kategorija = izmene.kategorija;
            repository.izmeni(this, izBaze);            
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // predaje this repositoriju na brisanje
    public void izbrisiProizvod() throws SQLException {
        ProizvodRepository repository = new ProizvodRepository();
        try {
            repository.izbrisi(this);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
    
    //Predaje this repositoriju za dodavanje u bazu
    public void noviProizvod() throws SQLException {
        ProizvodRepository repository = new ProizvodRepository();
        try {
            repository.dodaj(this);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
