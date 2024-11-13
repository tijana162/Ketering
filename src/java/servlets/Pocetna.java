package servlets;

import beans.Kategorija;
import beans.Proizvod;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 
 Hendluje prikaz i filtriranje prikaza ponude i kategorija na naslovnoj strani
 konfigurisan je kao welcome page u web.xml-u
*/
public class Pocetna extends HttpServlet {  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        
        List<Kategorija> kategorije = null;
        List<Proizvod> proizvodi = null;
        try {
            kategorije = Kategorija.sveKategorije();
            proizvodi = Proizvod.celaPonuda();
        } catch (SQLException sqle) {
            request.setAttribute("msg", sqle + " GRESKA");
            request.getRequestDispatcher("/pocetna.jsp").forward(request, response);
        }   // puni listu svih proizvoda i svih kategorija iz baze 

        if (request.getParameter("kategorija") == null) {  //Obican poziv pocetne - predaje nefiltrirane liste
            request.setAttribute("proizvodi", proizvodi);
        } else{  // filtriran poziv po kategoriji - filtira liste po zahtevu iz get-a
            List<Proizvod> filtriraniProizvodi = Proizvod.filtrirajPonudu(proizvodi, null, Integer.parseInt(request.getParameter("kategorija")));
            request.setAttribute("proizvodi", filtriraniProizvodi);
        }
        
        if (request.getParameter("program") != null){ // filtriran poziv po programu;
            List<Proizvod> filtriraniProizvodi = Proizvod.filtrirajPonudu(proizvodi, request.getParameter("program"), -1);
            request.setAttribute("proizvodi", filtriraniProizvodi);
        }

        List<Kategorija> slaneKategorije = Kategorija.filterKategorije(kategorije,"slani");
        List<Kategorija> slatkeKategorije = Kategorija.filterKategorije(kategorije,"slatki");
        request.setAttribute("slaneKategorije", slaneKategorije);
        request.setAttribute("slatkeKategorije", slatkeKategorije);
        
        request.getRequestDispatcher("/pocetna.jsp").forward(request, response);
    }  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //Do nothing
    }

    @Override
    public String getServletInfo() {
        return "Puni listu proizvoda i predaje index.jsp-u za prikaz";
    }
}
