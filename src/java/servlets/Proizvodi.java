package servlets;

import beans.Kategorija;
import beans.Proizvod;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//Hendluje pozive za brisanje i dodavanje proizvoda, uploaduj-e sliku proizvoda
@MultipartConfig
public class Proizvodi extends HttpServlet {

    //Poziv za brisanje proizvoda;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Proizvod zaBrisanje = new Proizvod(Integer.valueOf(request.getParameter("Proizvod")));
        try {
            zaBrisanje.izbrisiProizvod();
            request.getRequestDispatcher("Administracija?Zahtev=Proizvodi").forward(request, response);
        } catch (SQLException sqle) {
            request.setAttribute("msg", sqle.getMessage());
            request.getRequestDispatcher("Administracija").forward(request, response);
        }

    }

    //Hendluje AJAX poziv iz scripts.js za izmenu proizvoda i normalan poziv za dodavanje proizvoda
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        switch (request.getParameter("zahtev")) { // Zahtev za dodavanje proizvoda    
            case "dodaj":
                Part filePart = request.getPart("slika");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                Proizvod zaDodavanje = new Proizvod();
                zaDodavanje.setCenaPoPorciji(Integer.valueOf(request.getParameter("cena")));
                zaDodavanje.setKategorija(new Kategorija(Integer.valueOf(request.getParameter("kategorijaID"))));
                zaDodavanje.setNazivProizvoda(request.getParameter("naziv"));
                zaDodavanje.setOpis(request.getParameter("opis"));
                zaDodavanje.setSlika(fileName);

                //TODO naci drugi nacin za filepath
                File filePath = new File("C:\\Users\\dgudo\\OneDrive\\Desktop\\Faks\\Sesti semestar\\Internet Programerski alati\\Projekat\\CateringService\\web\\img");
                // Upload slike se vrsi u servletu 
                try ( InputStream fileContent = filePart.getInputStream()) {
                    zaDodavanje.noviProizvod();
                    File file = new File(filePath, fileName);
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException | SQLException ie) {
                    request.setAttribute("msg", ie.getMessage());
                }
                response.sendRedirect("Administracija?Zahtev=Proizvodi");
                return;
            case "izmeni": // Ajax poziv za izmenu proizvod   
                Proizvod zaIzmenu = new Proizvod(Integer.valueOf(request.getParameter("proizvod")));
                Proizvod izmene = new Proizvod();
                izmene.setNazivProizvoda(request.getParameter("naziv"));
                izmene.setOpis(request.getParameter("opis"));
                izmene.setCenaPoPorciji(Integer.valueOf(request.getParameter("cena")));
                izmene.setKategorija(new Kategorija(Integer.valueOf(request.getParameter("kategorijaID"))));
                try {
                    zaIzmenu.izmeniProizvod(izmene);
                } catch (SQLException sqle) {
                    response.sendError(500);
                }
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Hendluje pozive za brisanje i dodavanje proizvoda, uploaduj-e sliku proizvoda";
    }
}
