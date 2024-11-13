package servlets;

import beans.Narudzbina;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// hendluje pozive za otkazivanje i ostvarivanje narudzbi
public class Narudzbe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        switch (request.getParameter("Zahtev")) {
            case "Otkazi":
                Narudzbina zaOtkazivanje = new Narudzbina(Integer.valueOf(request.getParameter("Narudzba")));
                try {
                    zaOtkazivanje.otkaziNarudzbinu();
                    response.sendRedirect("Administracija?Zahtev=Narudzbine");
                } catch (SQLException sqle) {        
                    response.sendRedirect("Administracija?Zahtev=Narudzbine");
                }
                return;
            case "Ostvari":
                 Narudzbina zaOstvarivanje = new Narudzbina(Integer.valueOf(request.getParameter("Narudzba")));
                try {
                    zaOstvarivanje.ostvariNarudzbinu();
                    response.sendRedirect("Administracija?Zahtev=Narudzbine");
                } catch (SQLException sqle) {        
                    response.sendRedirect("Administracija?Zahtev=Narudzbine");
                }
                return;
            default:
                response.sendRedirect("error.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //do nothing
    }

    @Override
    public String getServletInfo() {
        return "hendluje pozive za otkazivanje i ostvarivanje narudzbi";
    }

}
