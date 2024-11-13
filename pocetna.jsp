<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
         <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ketering Služba</title>

        <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
    </head>
<style>
    /* Resetiranje stilova za body */
body {
    font-family: Arial, sans-serif; /* Font za celu stranicu */
    background-color: #f8f9fa; /* Boja pozadine tela stranice */
    margin: 0; /* Nema margina */
    padding: 0; /* Nema padding-a */
}

/* Stil za glavni kontejner */
.proizvodiPrikaz {
    max-width: 1200px; /* Maksimalna širina kontejnera */
    margin: auto; /* Centriranje kontejnera na sredinu ekrana */
    padding: 20px; /* Unutrašnji padding kontejnera */
    display: grid; /* Grid prikaz */
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); /* Automatski raspored kolona */
    gap: 20px; /* Rastojanje između elemenata u gridu */
}

/* Stil za pojedinačan proizvod */
.proizvod {
    background-color: #ffffff; /* Boja pozadine proizvoda */
    border: 1px solid #dddddd; /* Ivica proizvoda */
    border-radius: 5px; /* Zaobljeni ivičnjaci */
    padding: 15px; /* Unutrašnji padding proizvoda */
    box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* Senka ispod proizvoda */
}

.proizvod img {
    max-width: 100%; /* Maksimalna širina slike unutar proizvoda */
    height: auto; /* Automatska visina slike */
}

.proizvod h3 {
    margin-top: 10px; /* Margina na vrhu naslova */
    font-size: 1.2rem; /* Veličina fonta za naslov */
    font-weight: bold; /* Debljina fonta za naslov */
}

.proizvod p {
    margin: 5px 0; /* Margine za paragraf */
    font-size: 1rem; /* Veličina fonta za paragraf */
}

/* Stil za footer */
footer {
    background-color: #343a40; /* Boja pozadine footera */
    color: #ffffff; /* Boja teksta u footeru */
    text-align: center; /* Centriranje teksta */
    padding: 20px 0; /* Unutrašnji padding footera */
    position: fixed; /* Fiksni footer na dnu ekrana */
    width: 100%; /* Širina footera */
    bottom: 0; /* Prikazivanje footera na dnu ekrana */
}

footer a {
    color: #ffffff; /* Boja linkova u footeru */
    text-decoration: none; /* Bez podvlačenja linkova */
}

footer a:hover {
    text-decoration: underline; /* Podvlačenje linkova na hover-u */
}

    </style>
    <body>
        <%@include file="/includes/navbar.jsp"%>
      
        <h1>Nasa ponuda: </h1><br>
            <div class="proizvodiPrikaz">
                ${msg}
                <c:forEach var="proizvod" items="${proizvodi}">     
                    <%@include file="/includes/proizvod.jsp" %>                    
                </c:forEach>
            </div> 
        </div>
          
     
    </body>
</html>
