<!-- PRIKAZUJE FORMU ZA IZMENU KORISNICKIH INFORMACIJA -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
 <style>
               .input-prikaz {
            border-radius: 10px;
            border: 2px solid #ccc;
            padding: 8px 12px; /* Prilagodite ovo prema vašim preferencijama */
            font-size: 16px; /* Prilagodite veličinu fonta prema vašim preferencijama */
            width: 70% !important; /* Širina input polja */
            box-sizing: border-box; /* Dodajte padding i border u ukupnu širinu elementa */
            margin-bottom: 10px; /* Dodajte razmak ispod input polja */
        }
        </style>

<div class="profil-prikaz">
    
   
      
    <form class="forma-prikaz" action="Profil" method="post">  
        <h2><strong>PROFIL</strong></h2><br>
        <label for="korisnickoIme"><strong>Korisničko ime</strong></label> 

        <input class="input-prikaz" type="text" name="korisnickoIme" id="korisnickoIme" value="${User}" disabled/></br>

        <label for="ime"><strong>Ime</strong></label> 

        <input class="input-prikaz" type="text" name="ime" id="ime" value="${Ime}" required/></br>

        <label for="prezime"><strong>Prezime</strong></label> 

        <input class="input-prikaz" type="text" name="prezime" id="prezime" value="${Prezime}" required/></br>

        <label for="password"><strong>Password</strong></label> 

        <input class="input-prikaz" type="password" name="password" id="password" placeholder="Ovde upišite Vaš trenutni password " required/></br>

        <label for="noviPassword"><strong>Novi Password</strong></label> 

        <input class="input-prikaz" type="password" name="noviPassword" id="noviPassword" placeholder="Ovde upišite novi password!" required/></br>

        <label for="adresa"><strong>Adresa</strong></label> 

        <input class="input-prikaz" type="text" name="adresa" id="adresa" value="${Adresa}" required/></br>
        <div class="prikaz-button">
            <input class="btn btn-primary " type="submit" value="Izmeni" name="zahtev">       <br> <br> 
         
                 <h2><a class="btn btn-danger" href="Profil?User=${User}&View=Izbrisi">Izbriši profil</a></h2>
        </div>
        
        
        
        <p class="Info-${param.Status}"> 
        <c:if test="${param.Status == 'uspeh'}">
            Uspešno ste izmenili informacije!
        </c:if>
             
        <c:if test="${param.Status == 'greska'}">
            Uneli ste pogrešnu šifru!
        </c:if>
             
        <c:if test="${param.Status == 'con'}">
            Došlo je do greške pokušajte ponovo!
        </c:if>
        </p> <!-- Ispisuje poruku o greski ili uspehu -->
    </form>
</div>
