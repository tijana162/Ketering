package beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import repository.NarudzbinaRepository;
import repository.ProizvodRepository;

public class Narudzbina implements Serializable {

    private String datumKreiranja;
    private String datumOstvarivanja;
    private int status; // 0- Priprema, 1- Ostvarena 2- Otkazana
    private Korisnik korisnik;
    private int narudzbinaID;
    private int popust;
    private int ukupnaCena;
    private HashMap<Proizvod, Integer> stavkeNarudzbine; // cuva kolicinu proizvoda 

    //constructors
    public Narudzbina() {
        stavkeNarudzbine = new HashMap<>();
    }

    public Narudzbina(int NarudzbinaID) {
        this.narudzbinaID = NarudzbinaID;
        stavkeNarudzbine = new HashMap<>();
    }

    public Narudzbina(String datumKreiranja, String datumOstvarivanja, int status, Korisnik korisnik, int narudzbinaID, int popust, int UkupnaCena, HashMap<Proizvod, Integer> stavkeNarudzbine) {
        if (datumKreiranja.equals("sada")) {
            Date d = new Date();
            this.datumKreiranja = d.toString();
        } else {
            this.datumKreiranja = datumKreiranja;
        }
        this.datumOstvarivanja = datumOstvarivanja;
        this.status = status;
        this.korisnik = korisnik;
        this.narudzbinaID = narudzbinaID;
        this.popust = popust;
        this.ukupnaCena = UkupnaCena;
        this.stavkeNarudzbine = stavkeNarudzbine;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public String getDatumOstvarivanja() {
        return datumOstvarivanja;
    }

    public void setDatumOstvarivanja(String datumOstvarivanja) {
        this.datumOstvarivanja = datumOstvarivanja;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public int getNarudzbinaID() {
        return narudzbinaID;
    }

    public void setNarudzbinaID(int narudzbinaID) {
        this.narudzbinaID = narudzbinaID;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public int getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(int UkupnaCena) {
        this.ukupnaCena = UkupnaCena;
    }

    public HashMap<Proizvod, Integer> getStavkeNarudzbine() {
        return stavkeNarudzbine;
    }

    public void setStavkeNarudzbine(HashMap<Proizvod, Integer> stavkeNarudzbine) {
        this.stavkeNarudzbine = stavkeNarudzbine;
    }

    // Sistemske operacije
    //STATIC metode
    // Vraca listu punih objekata Narudzbina predatog korisnika
    public static List<Narudzbina> prikazNarudzbiKorisnika(Korisnik korisnik) throws SQLException {
        NarudzbinaRepository narudzbinaRepository = new NarudzbinaRepository();
        List<Narudzbina> rezultat = new ArrayList<>();
        try {
            rezultat = narudzbinaRepository.getSveOdKorisnika(korisnik);
        } catch (SQLException sqle) {
            throw sqle;
        }
        return rezultat;
    }

    //Vraca listu neostvarenih narudzbina svih korisnika    
    public static List<Narudzbina> prikazNeostvarenih() throws SQLException {
        NarudzbinaRepository narudzbinaRepository = new NarudzbinaRepository();
        List<Narudzbina> rezultat = new ArrayList<>();
        try {
            for (Narudzbina nar : narudzbinaRepository.getSve()) {
                if (nar.getStatus() == 0) {
                    rezultat.add(nar);
                }
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return rezultat;
    }

    // NON-STATIC metode
    /* 
    Predaje this repositoriju za dodavanje narudzbe u bazu
    poziva dodavanje ostvarenih poena nad korisnikom koji je narucio 
    Oduzima potrosene poene
     */
    public void naruci() throws SQLException {
        NarudzbinaRepository repository = new NarudzbinaRepository();
        try {
            repository.dodaj(this);
            this.getKorisnik().dodajPoene(this.popust / 10 * -1);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    //Dobija i setuje podatke iz baze potrebne da se kopija narudzbe stavi u trenutnu korpu korisnika
    public void ponoviNarudzbu() throws SQLException {
        NarudzbinaRepository repository = new NarudzbinaRepository();
        try {
            Narudzbina zaPonavljanje = repository.getJedan(this);
            this.stavkeNarudzbine = zaPonavljanje.getStavkeNarudzbine();
            this.korisnik = zaPonavljanje.getKorisnik();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /*
    Menja status na 2(Otkazana),
    Izmenjenu narudzbu predaje repository.izmeni metodi 
    vraca korisniku iskoristene poene
     */
    public void otkaziNarudzbinu() throws SQLException {
        NarudzbinaRepository repository = new NarudzbinaRepository();
        try {
            Narudzbina zaIzmenu = repository.getJedan(this);
            zaIzmenu.setStatus(2);
            repository.izmeni(this, zaIzmenu);
            zaIzmenu.getKorisnik().dodajPoene(zaIzmenu.getPopust() / 10);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /*
    Menja status na 1(Ostvarena),
    Izmenjenu narudzbu predaje repository.izmeni metodi
    Dodaje poene korisniku
     */
    public void ostvariNarudzbinu() throws SQLException {
        NarudzbinaRepository repository = new NarudzbinaRepository();
        
        try {
            Narudzbina zaIzmenu = repository.getJedan(this);
            zaIzmenu.setStatus(1);
            zaIzmenu.setDatumOstvarivanja(new java.sql.Date(System.currentTimeMillis()).toString());
            repository.izmeni(this, zaIzmenu);
            zaIzmenu.getKorisnik().dodajPoene(Math.round(zaIzmenu.ukupnaCena) / 1000);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // Dodaje proizvod u hashmapu
    public void dodajProizvod(Proizvod p, int kolicina) throws SQLException {
        for (Proizvod prod : stavkeNarudzbine.keySet()) {
            if (prod.getProizvodID() == p.getProizvodID()) {
                stavkeNarudzbine.put(prod, stavkeNarudzbine.get(prod) + kolicina);
                return;
            }
        }
        // Ako je novi proizvod puni ga relevantnim podacima iz baze
        ProizvodRepository repository = new ProizvodRepository();
        try {
            stavkeNarudzbine.put(repository.getJedan(p), kolicina);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    // Menja kolicinu (value u hash mapi) zadatog proizvoda(key u hashmapi) po ID-u 
    public void izmeniKolicinu(int proizvodID, int novaKolicina) {
        for (Proizvod prod : stavkeNarudzbine.keySet()) {
            if (prod.getProizvodID() == proizvodID) {
                stavkeNarudzbine.put(prod, novaKolicina);
                break;
            }
        }
    }

    //ukljanja zadati proizvod iz hashmape po ID-u
    public void ukloniProizvod(int proizvodID) {
        for (Proizvod prod : stavkeNarudzbine.keySet()) {
            if (prod.getProizvodID() == proizvodID) {
                stavkeNarudzbine.remove(prod);
                break;
            }
        }
    }

    //vraca total pre racunanja popusta
    public int getTotalBezPopusta() {
        int ukupno = 0;
        for (Proizvod stavka : stavkeNarudzbine.keySet()) {
            ukupno += (stavka.getCenaPoPorciji() * stavkeNarudzbine.get(stavka));
        }
        return ukupno;
    }

    //Setuje popust, racuna i setuje ukupnu cenu sa popustom objekta nad kojim je pozvana.
    public void izracunajPopust(int poeni) {
        if (poeni > 0) {
            this.setPopust(poeni * 10);
            this.setUkupnaCena(this.getTotalBezPopusta() - ((this.getTotalBezPopusta() / 100) * this.popust));
        } else {
            this.setPopust(0);
            this.setUkupnaCena(this.getTotalBezPopusta());
        }
    }

}
