<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ketering Služba</title>
        <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
        <script src="js/html2pdf.js"></script>
        <script src="js/jquery.js"></script>
        <script src="js/scripts.js"></script>

    </head>
<style>
.administracija{
    display: grid;
    grid-template-columns: 1fr 4fr;
    gap:2px;
    
}
.administracija-linkovi{
    background-color: black;
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 10px 2px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.administracija-link{
    margin: 1rem;
    color: black;
    font-weight: bold;
    font-size: 30px;
    text-decoration: none;
}

.administracija-link:hover{
    color: black;
}
.administracija-rola{
    color: black;
    padding-top: 2rem;
    padding-left: 1rem;
    padding-bottom: 0;

}
.panel{
    background-color: white;
}


.naslov{
    margin: 2rem;
    padding-bottom: 2rem;
    text-align: center;
    border-bottom: solid 2px white;
}


.prikaz-neostvarenih{
    background-color: white;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 32%));
    grid-template-rows: repeat(auto-fit, minmax(24.375rem, 24.375rem));
    gap: 1rem 1rem;
    justify-content: space-evenly;
    overflow: auto;
    padding: 1rem;
}

.btn-narudzbina{
    display: flex;
    flex-direction: column;
}

.btn-narudzbina > a{
    margin: 2px;
}

.edit-ponude{
    background-color: white;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1.1fr 0.9fr;
    gap: 2rem;
    padding: 1rem;
}

.lista-naslov{
    font-weight: bold;
    display: grid;
    grid-template-columns: 1fr 1.5fr 3fr 0.7fr 0.8fr 1.2fr;
    gap:1rem;
}
.lista{
    display: grid;
    height: 27rem;
    width: 100%;
    grid-template-rows: repeat(auto-fit, minmax(4rem,4rem));
    grid-template-columns: 1fr;
    overflow: auto;
    gap: 0.2rem;
}
.lista-stavka{
    display: grid;
    grid-template-columns: 1fr 1.5fr 3fr 0.7fr 0.8fr 1.2fr;
    gap:1rem;
    height: 4rem;
    padding-left: 0.5rem;
    background-color:white;
}
.lista-stavka > p{
    margin: auto;
    margin-left: 0;
}
.lista-stavka > select{
   height: 2rem;
   margin-top: 1rem;
}

.lista-btn{
    margin: auto;
}
/* Donji dio ponude */
.ponuda-dole{
    display: grid;
    grid-template-columns: 7fr 3fr;
}
.nov-proizvod{
    -webkit-box-shadow: -6px 10px 19px 0px rgba(0,0,0,0.35);
    box-shadow: -6px 11px 19px 0px rgba(0,0,0,0.35);
    background-color: white;
    border-radius: 0.5rem;
    display: grid;
    padding: 1rem;


}
.nov-proizvod > form{
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: repeat(auto-fit, minmax(2rem, 4rem));
    gap:1rem;
    margin-top: 0;
}
.nov-proizvod > p{
    text-align: center;
}

.nov-proizvod > form > div{
    display: grid;
}
.nov-proizvod > form > div:nth-child(6){
    grid-template-columns: 4fr 1fr;
    gap: 1rem;
    padding: 1rem 0;
}
.prikaz-kategorija{
    display: grid;
    grid-template-rows: 1fr 8fr;
    border: solid 2px #fff;
    margin-right: 1rem;
}

.prikaz-kategorija > p{
    display: grid;
    font-weight: bold;
    font-size: 20px;
    border-bottom: solid 2px #fff;
    place-content: center;
    height: 100%;
}

.prikaz-kategorija > div{
    display: grid;
    grid-template-columns: 1fr 1fr;
}

.slatke-kategorije{
    display: grid;
    grid-template-rows: 1fr 1fr 6fr;
    border-left: solid 2px #fff;
    padding: 1rem;
    padding-left: 2rem;
}

.slane-kategorije{
    display: grid;
    grid-template-rows: 1fr 1fr 6fr;
    padding: 1rem;
    padding-left: 2rem;
    
}
.slane-kategorije > p{
    text-align: center;
    font-weight: bold;
}
.slatke-kategorije > p{
    text-align: center;
    font-weight: bold;
}
.kategorije-lista{
    display: grid;
    grid-template-rows: repeat(auto-fit, minmax(2rem,3rem));
    width: 100%;
    max-height: 17rem;
    gap: 1rem;
    overflow: auto;
}
.kat-stavka{
    display: grid;
    grid-template-columns: 0.5fr 1fr 1.5fr;
    margin: auto 0;
}
.kat-stavka > select{
    margin: 0 1rem;
}

/* KRAJ PROIZ/KAT */

/* CSS ZA KORISNIKE */
.edit-korisnika{
    padding: 2rem;
    background-color: white;
}

.lista-korisnika{
    display: grid;
    height: 30rem;
    width: 100%;
    grid-template-rows: repeat(auto-fit, minmax(4rem,4rem));
    grid-template-columns: 1fr;
    overflow: auto;
    gap: 0.2rem;
}
.header-korisnika{
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1.3fr;
    padding-left: 0.5rem;
}
.header-korisnika > p{
    font-weight: bold;
}
.korisnik-stavka{
    background-color: white;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 0.7fr 1fr 1fr 1.3fr;
    gap:1rem;
    height: 4rem;
    padding-left: 0.5rem;
}
.korisnik-stavka > p{
    margin: auto;
    margin-left: 0;
}
.korisnik-stavka > div{
    margin: auto;
    margin-left: 0;
}
.korisnik-stavka > select{
   height: 2rem;
   margin-top: 1rem;
}
.korisnik-stavka > input{
    width: 100%;
    height: 2rem;
    margin: auto;
    text-align: center;
}

.btn-dodaj{
   width: 50% !important;
  
}
/*KRAJ CCS-A ZA KORISNIKE */

/* CSS ZA IZVESTAJE */
.izvestaj{
    display: grid;
    grid-template-rows: 0.5fr 3fr 1fr;
    grid-template-columns: 1fr;
    padding: 2rem;
    gap: 1rem;
}

.naslov-izvestaja{
    display: grid;
    place-content: center;
}

.naslov-izvestaja > span{
    font-size: 30px;
}

.telo-izvestaja{
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: 1fr 1fr;
    gap: 1rem;
    padding: 0 4rem;
}

.telo-izvestaja > div{
    border: solid 2px #fff;
    padding: 2rem;
}
.footer-izvestaja{
    display: grid;
    grid-template-rows: 1fr 1fr;
}
.izvestaj-totali{
    display: grid;
    grid-template-columns: 1fr 1fr;
    padding: 1rem 4rem;
    text-align: center;
}

.izvestaji-btn{
    display: grid;
    place-items: center;
}
.btn-pdf{
    width: 5rem;
    height: 2rem;
}

.vertical-center{
    text-align: center;
}

</style>
    <body>
        
            <%@include file="includes/navbar.jsp"%>
          
                <div class="vertical-center">
                    <c:if test="${UserRola == 1}">  
                        <h2 class="administracija-rola" style="text-align: center; font-size: 16px"><strong>Admin panel</strong></h2>
                        
                 
                    </c:if>
                    <c:if test="${UserRola == 2}">  
                        <h2 class="administracija-rola" >Menadžer</h2>
                        <br>
                      
                    </c:if>
                        <button class="administracija-link" style="height: 40px; font-size: 16px;" onclick="location.href='Administracija?Zahtev=Korisnici'">Korisnici</button>
<button class="administracija-link" style="height: 40px; font-size: 16px;" onclick="location.href='Administracija?Zahtev=Narudzbine'">Narudžbine</button>
<button class="administracija-link" style="height: 40px; font-size: 16px;" onclick="location.href='Administracija?Zahtev=Proizvodi'">Proizvodi</button>

                 
</div>      
                <c:if test = "${param.Zahtev == 'Narudzbine'}">
                    <%@include file="includes/upravljanjeNarudzbinama.jsp"%>
                </c:if>
                <c:if test = "${param.Zahtev == 'Proizvodi'}">
                    <%@include file="includes/upravljanjeProizvodima.jsp"%>
                </c:if>
                <c:if test = "${param.Zahtev == 'Kategorije'}">
                    <%@include file="includes/kategorije.jsp"%>
                </c:if>
            
                <c:if test = "${param.Zahtev == 'Korisnici'}">
                    <%@include file="includes/korisnici.jsp"%>
                </c:if>
                
    </body>
</html>