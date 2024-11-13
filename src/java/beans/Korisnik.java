package beans;

import database.MD5;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import repository.KorisnikRepository;

public class Korisnik implements Serializable {

    private String adresa;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String password;  //MD5 hash
    private int poeni;
    private Rola rola;

    public Korisnik() {
    }

    public Korisnik(String korisnickoIme) {  // Konstruktor za pretrage po ID-u
        this.korisnickoIme = korisnickoIme;
    }

    public Korisnik(String korisnickoIme, String password) {  // Konstruktor za login
        this.korisnickoIme = korisnickoIme;
        this.password = MD5.getHash(password);
    }

    public Korisnik(String korisnickoIme, String password, String Ime, String Prezime, String Adresa) {  // Konstruktor za izmenu podataka
        this.adresa = Adresa;
        this.ime = Ime;
        this.prezime = Prezime;
        this.korisnickoIme = korisnickoIme;
        this.password = MD5.getHash(password);
    }

    public Korisnik(String adresa, String ime, String prezime, String korisnickoIme, String password, int poeni, Rola rola) {  //potpuni konstruktor
        this.adresa = adresa;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.password = MD5.getHash(password);
        this.poeni = poeni;
        this.rola = rola;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5.getHash(password);
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }

    public Rola getRola() {
        return rola;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }

    //Sistemske operacije
    //Static metode
    // Poziva getSve metodu repozitorija i vraca listu svih korisnika
    public static List<Korisnik> sviKorisnici() throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();
        try {
            return repository.getSve();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    //NonStatic metode
    /*
     Proverava da li se predata sifra podudara sa korisnikom u bazi koji dodaje repozitory
     ako se podudara, puni objekat podacima iz baze i vraca true(uspeh); \
     */
    public boolean login() throws SQLException {
        KorisnikRepository repositorij = new KorisnikRepository();
        try {
            Korisnik celi = repositorij.getJedan(this);
            if (celi != null) {
                if (this.password.equals(celi.getPassword())) {
                    napuniPodatke(celi);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // Predaje sebe repozitoriju za dodavanje u bazu, u slucaju greske forwarduje exception kontroleru
    public void registruj() throws SQLException {
        KorisnikRepository repositorij = new KorisnikRepository();
        try {
            repositorij.dodaj(this);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // Controller poziva nad Korisnikom kreiranim samo sa username-om u svrhe prikaza profila
    public void getPodatke() throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();
        try {
            Korisnik celi = repository.getJedan(this);
            napuniPodatke(celi);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // puni instance kreirane samo sa username-om i sifrom.
    private void napuniPodatke(Korisnik puni) {
        this.ime = puni.ime;
        this.prezime = puni.prezime;
        this.adresa = puni.adresa;
        this.rola = puni.rola;
        this.poeni = puni.poeni;
    }

    /* 
    *Administrator menja korisnikove podatke*
    Proverava da li je administrator menjao sifru
    Ako nije puni izmene sa trenutnim hashom sifre iz baze
     */
    public void izmeniKorisnika(Korisnik izmene) throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();

        if (izmene.getPassword().equals("Neizmenjen")) {
            izmene.setPasswordHash(repository.getJedan(this).getPassword());
        } else {
            izmene.setPassword(izmene.getPassword()); // izmene.getPassword je neheshirann, setPassword heshira
        }

        try {
            repository.izmeni(this, izmene);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /*
    *Korisnik menja svoje podatke*
    Proverava da li je korisnik uneo tacnu sifru,
    ako jeste salje izmenjene podatke repositoriju za promenu u bazi
     */
    public boolean izmeniInformacije(Korisnik izmene) throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();
        Korisnik originalniKorisnik;
        try {
            originalniKorisnik = repository.getJedan(this);

            if (originalniKorisnik.getPassword().equals(this.password)) {
                izmene.setRola(originalniKorisnik.getRola());
                izmene.setPoeni(originalniKorisnik.getPoeni());
                originalniKorisnik.setPasswordHash(izmene.getPassword());
                originalniKorisnik.napuniPodatke(izmene);
                repository.izmeni(this, originalniKorisnik);
                return true;
            } else {
                return false;  // pogresan password;
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
    /*
    *Administrator brise korisnika*
    Predaje this repositoriju na brisanje*
    */
    public void izbrisiKorisnika() throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();
        try {
            repository.izbrisi(this);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /*
    *Korisnik brise svoj profil*
     Poredi uneti password hash (this.password) sa hashom iz baze
     Ako je tacan predaje korisnika za brisanje iz baze (repository.izbrisi) i vraca true
     Ako nije tacan ili je bacen SQLexception vraca false
     */
    public boolean izbrisiProfil() {
        KorisnikRepository repository = new KorisnikRepository();
        try {
            Korisnik korisnik = repository.getJedan(this);
            if (korisnik.getPassword().equals(this.password)) {
                repository.izbrisi(korisnik);
                return true;
            }
            return false;
        } catch (SQLException sqle) {
            return false;
        }
    }

    /*
    Uzima korisnika iz baze od repositorija,
    dodaje poene iz parametra i predaje nazad repositoriju na izmenu (repository.izmeni)    
     */
    void dodajPoene(int poeni) throws SQLException {
        KorisnikRepository repository = new KorisnikRepository();
        try {
            Korisnik zaBazu = repository.getJedan(this);
            zaBazu.setPoeni(zaBazu.getPoeni() + poeni);
            repository.izmeni(this, zaBazu);
        } catch (SQLException sqle) {
            throw sqle;
        }

    }
}
