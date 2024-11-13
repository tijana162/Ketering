package servlets;

import beans.Korisnik;
import beans.Narudzbina;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Istorija extends HttpServlet {

    // Hendluje pozive za prikaz istorije, otkazivanje narucene narudzbine i ponavljanje stare narudzbine
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("User") == null || request.getParameter("Zahtev") == null) {
            //Invalidan poziv
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        switch (request.getParameter("Zahtev")) {
            case "Otkazi":
                // Otkazivanje narudzbine sa statusom U Pripremi
                Narudzbina zaOtkazivanje = new Narudzbina(Integer.valueOf(request.getParameter("Narudzba")));
                try{
                    zaOtkazivanje.otkaziNarudzbinu();
                    response.sendRedirect("Istorija?Zahtev=Pregled");
                } catch(SQLException sqle){
                    request.setAttribute("msg", sqle.getMessage());
                    request.getRequestDispatcher("istorija.jsp").forward(request, response);
                }
                
                break;
            case "Ponovi":
                // Kopiranje stare narudzbe u korpu
                Narudzbina zaPonavljanje = new Narudzbina(Integer.valueOf(request.getParameter("Narudzba")));
                try {
                    zaPonavljanje.ponoviNarudzbu();
                    session.setAttribute("Narudzbina", zaPonavljanje);
                    response.sendRedirect("Profil?User=" + session.getAttribute("User").toString() + "&View=Korpa");
                } catch (SQLException sqle) {
                    request.setAttribute("msg", sqle.getMessage());
                    request.getRequestDispatcher("istorija.jsp").forward(request, response);
                }

                break;
            case "Pregled":
                 // Prikaz istorije
            try {
                List<Narudzbina> narudzbine = Narudzbina.prikazNarudzbiKorisnika(new Korisnik(session.getAttribute("User").toString()));
                Collections.reverse(narudzbine);
                request.setAttribute("Narudzbine", narudzbine);
                request.getRequestDispatcher("istorija.jsp").forward(request, response);
            } catch (SQLException sqle) {
                request.setAttribute("msg", sqle.getMessage());
                request.getRequestDispatcher("istorija.jsp").forward(request, response);
            }
            break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hendluje pozive za prikaz istorije, otkazivanje narucene narudzbine i ponavljanje stare narudzbine";
    }

}
