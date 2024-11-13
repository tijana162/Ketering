package servlets;

import beans.Kategorija;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//Hendluje pozive za brisanje, dodavanje i edit kategorija
public class Kategorije extends HttpServlet {

    @Override // hendluje zahtev za brisanje i dodavanje kategorije
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getParameter("Zahtev")) {
            case "Dodaj":
                Kategorija zaDodavanje = new Kategorija();
                zaDodavanje.setNazivKategorije(request.getParameter("naziv"));
                zaDodavanje.setProgram(request.getParameter("program"));
                try {
                    zaDodavanje.dodajKategoriju();
                    request.getRequestDispatcher("Administracija?Zahtev=Proizvodi").forward(request, response);
                } catch (SQLException sqle) {
                    request.setAttribute("msg", sqle.getMessage());
                    request.getRequestDispatcher("Administracija").forward(request, response);
                }
                break;
            case "Izbrisi":
                try {
                Kategorija zaBrisanje = new Kategorija(Integer.valueOf(request.getParameter("Kategorija")));
                zaBrisanje.izbrisiKategoriju();
                request.getRequestDispatcher("Administracija?Zahtev=Proizvodi").forward(request, response);
            } catch (SQLException sqle) {
                request.setAttribute("msg", sqle.getMessage());
                request.getRequestDispatcher("Administracija").forward(request, response);
            }
            break;
        }

    }

    @Override  //hendluje AJAX zahtev za menjanje postojece
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Kategorija zaIzmenu = new Kategorija(Integer.valueOf(request.getParameter("kategorija")));
        Kategorija izmene = new Kategorija();
        izmene.setKategorijaID(Integer.valueOf(request.getParameter("kategorija")));
        izmene.setNazivKategorije(request.getParameter("naziv"));
        izmene.setProgram(request.getParameter("program"));
        try {
            zaIzmenu.izmeniKategoriju(izmene);
        } catch (SQLException sqle) {
            response.sendError(500);
        }
    }

    @Override
    public String getServletInfo() {
        return "Hendluje pozive za brisanje, dodavanje i edit kategorija";
    }

}
