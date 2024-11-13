package servlets;

import beans.Korisnik;
import beans.Rola;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Hendluje pozive za brisanje, edit i dodavanje novih korisnika
public class Korisnici extends HttpServlet {

    @Override // Hendluje poziv za brisanje korisnika
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Korisnik zaBrisanje = new Korisnik(request.getParameter("Korisnik"));
        try {
            zaBrisanje.izbrisiKorisnika();
        } catch (SQLException sqle) {
            request.setAttribute("msg", "Doslo je do greske");
        }
        response.sendRedirect("Administracija?Zahtev=Korisnici");
    }

    @Override //Hendluje poziv za edit i dodavanje korisnika
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        switch (request.getParameter("zahtev")) {
            case "izmeni":  // AJAX poziv za edit korisnika
                Korisnik zaIzmenu = new Korisnik(request.getParameter("korisnik"));

                Korisnik izmene = new Korisnik(request.getParameter("korisnicko"));
                izmene.setIme(request.getParameter("ime"));
                izmene.setPrezime(request.getParameter("prezime"));
                izmene.setAdresa(request.getParameter("adresa"));
                izmene.setPasswordHash(request.getParameter("password"));
                izmene.setPoeni(Integer.valueOf(request.getParameter("poeni")));
                izmene.setRola(new Rola(Integer.valueOf(request.getParameter("rola")), ""));
                try {
                    zaIzmenu.izmeniKorisnika(izmene);
                } catch (SQLException sqle) {
                    response.sendError(500);
                }

                break;
            case "Dodaj":  // poziv za dodavanje korisnika
                Korisnik nov = new Korisnik(request.getParameter("adresa"),
                        request.getParameter("ime"),
                        request.getParameter("prezime"),
                        request.getParameter("korisnicko"),
                        request.getParameter("password"),
                        Integer.valueOf(request.getParameter("poeni")),
                        new Rola(Integer.valueOf(request.getParameter("rola")), ""));
                try{
                    nov.registruj();
                }catch(SQLException sqle){
                    request.setAttribute("msg", "Doslo je do greske");
                }
                response.sendRedirect("Administracija?Zahtev=Korisnici");
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Hendluje pozive za brisanje, edit i dodavanje novih korisnika";
    }
}
