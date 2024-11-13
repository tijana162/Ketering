package servlets;

import beans.Narudzbina;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Korpa extends HttpServlet {

    @Override    // Hendluje zahteve vezane za stavke narudzbine 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Narudzbina narudzbina = (Narudzbina)session.getAttribute("Narudzbina");
        switch (request.getParameter("Zahtev")) {
            case "Izmeni" :  // Menja kolicinu
               narudzbina.izmeniKolicinu(Integer.valueOf(request.getParameter("Proizvod")), Integer.valueOf(request.getParameter("Kolicina")));
               break;
            case "Izbrisi":  // Brise stavku
                narudzbina.ukloniProizvod(Integer.valueOf(request.getParameter("Proizvod")));
                break;       
            default: // Prikazuje korpu
                response.sendRedirect("Profil?User=" + session.getAttribute("User").toString() + "&View=Korpa");
                return;               
        }
        
        if(!narudzbina.getStavkeNarudzbine().isEmpty()){
            session.setAttribute("Narudzbina", narudzbina);
        } else{
            session.removeAttribute("Narudzbina");
        } 
        
        response.sendRedirect("Profil?User=" + session.getAttribute("User").toString() + "&View=Korpa");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {         
        response.setContentType("text/html;charset=UTF-8");
        //do nothing
    }

    @Override
    public String getServletInfo() {
        return "Hendluje zahteve vezane za stavke narudzbine";
    }

}
