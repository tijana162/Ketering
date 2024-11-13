package servlets;

import beans.Korisnik;
import beans.Rola;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Autentikacija extends HttpServlet {
    
    @Override //  Hendluje logout request
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("Pocetna").forward(request, response);
    }
    
    @Override  // Hendluje register i login request
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        if (request.getParameter("loginKorisnicko") == null) {  //register request;  kreira korisnika i poziva registruj() nad njim 
            try {
                Korisnik noviKorisnik = new Korisnik(request.getParameter("adresa"),
                        request.getParameter("ime"),
                        request.getParameter("prezime"),
                        request.getParameter("korisnickoIme"),
                        request.getParameter("password"),
                        0, new Rola(3, "Klijent"));
                noviKorisnik.registruj();
            } catch (SQLIntegrityConstraintViolationException sql) {
                request.setAttribute("msg", "Korisničko ime već postoji!");
                request.getRequestDispatcher("registracija.jsp").forward(request, response);
                return;
            } catch(SQLException sqle){
                request.setAttribute("msg", "Greška pri konekciji, pokušajte ponovo");
                request.getRequestDispatcher("registracija.jsp").forward(request, response);
                return;
            }
            request.setAttribute("msgTip", "uspeh");
            request.setAttribute("msg", "Uspesna registracija, ulogujte se!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
            
        } else {    //login request; kreira korisnika sa samo ID-em i passwordom, poziva login, ako uspije zapocinje sesiju; 
            Korisnik loginKorisnik = new Korisnik(request.getParameter("loginKorisnicko"), request.getParameter("password"));
            HttpSession session = request.getSession();
            try {                
                if (loginKorisnik.login()) {
                    session.setAttribute("User", loginKorisnik.getKorisnickoIme());
                    session.setAttribute("UserRola", loginKorisnik.getRola().getRolaID());
                    session.setAttribute("Poeni", loginKorisnik.getPoeni());
                    response.sendRedirect("Pocetna");
                } else {
                    request.setAttribute("msgTip", "greska");
                    request.setAttribute("msg", "Pogrešna šifra ili username");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }                  
            } catch (SQLException sqle) {
                request.setAttribute("msgTip", "greska");
                request.setAttribute("msg", "Interna greška, pokušajte ponovo!");
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet upravlja HTTP pozivima za registraciju, login i logout";
    }
    
}
