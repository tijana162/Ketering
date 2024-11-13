package servlets;

import beans.Korisnik;
//import beans.Rola;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Profil extends HttpServlet {
//Hendluje pozive za prikaz i izmenu informacija i brisanja profila
//Refresuje stranice koje se prikazuju na profil.jsp, a ne hendluju u ovom kontroleru, npr. "korpa.jsp"(View=Korpa; Korpa Controller)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("User") == null) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        if (session.getAttribute("User").equals(request.getParameter("User"))) {  // Ima pristup ?
            switch (request.getParameter("View")) {
                case "Profil":  // Prikaz forme za izmenu profila
                    Korisnik trazeni = new Korisnik(request.getParameter("User"));
                    try {
                        trazeni.getPodatke();
                        session.setAttribute("Ime", trazeni.getIme());
                        session.setAttribute("Prezime", trazeni.getPrezime());
                        session.setAttribute("Adresa", trazeni.getAdresa());
                        session.setAttribute("Poeni", trazeni.getPoeni());
                        session.setAttribute("Rola", trazeni.getRola().getRolaID());
                        request.getRequestDispatcher("profil.jsp").forward(request, response);
                    } catch (SQLException sqle) {
                        request.setAttribute("msg", "Došlo je do greške");
                        request.getRequestDispatcher("profil.jsp").forward(request, response);
                    }
                    break;
                case "Korpa":  //Prikaz korpe
                case "Izbrisi":  // Prikaz strane za brisanje
                case "Poruka":  // Prikaz poruke o ostvarenoj narudzbi i poenima
                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                    break;
                default:   // 403 Forbidden
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    break;
            }
        } else { // 403 Forbidden
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) // Poziva se za izmenu profila ili brisanje 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String status;  //Koristi se ispis poruke o uspesnosti radnje
        
        if (request.getParameter("zahtev").equals("Izmeni")) {  // Izmena
            Korisnik trenutni = new Korisnik(session.getAttribute("User").toString(),
                    request.getParameter("password"));
            Korisnik izmenjenKorisnik = new Korisnik(request.getParameter("korisnickoIme"),
                    request.getParameter("noviPassword"),
                    request.getParameter("ime"),
                    request.getParameter("prezime"),
                    request.getParameter("adresa"));
            try {
                if (trenutni.izmeniInformacije(izmenjenKorisnik)) {
                    status = "uspeh";
                } else {
                    status = "greska";
                }
            } catch (SQLException sqle) {
                status = sqle.getMessage();
            }
            // Poziva svoju doGet metodu za prikaz izmenjenih podataka ili greske
            response.sendRedirect("Profil?User=" + session.getAttribute("User").toString() + "&View=Profil&Status=" + status);
        } else {  // Brisanje
            Korisnik zaBrisanje = new Korisnik(session.getAttribute("User").toString(), request.getParameter("password"));
            if (zaBrisanje.izbrisiProfil()) {
                session.invalidate();
                response.sendRedirect("Pocetna");
            } else { // pogresna sifra
                response.sendRedirect("Profil?User=" + session.getAttribute("User").toString() + "&View=Izbrisi&Status=greska");
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Hendluje pozive za prikaz informacija, istoriju porudzbina i brisanja profila";
    }

}
