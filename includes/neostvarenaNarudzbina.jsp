<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="narudzbina status0">
    <div class='istorija-heading'>
        
     <p class="bold"> 
    <span style="color: black;">Korisnik:</span> 
    <span style="color: red;">${narudzbina.getKorisnik().getKorisnickoIme()}</span>
</p>
        <h4> NARUČENO: </h4>
    </div>

    <div class='narudzbina-stavke'>
        <c:forEach var="stavka" items="${narudzbina.getStavkeNarudzbine().keySet()}">
            <div class="narudzbina-stavka">
                <p>${stavka.getNazivProizvoda()}: </p> <p>${stavka.getCenaPoPorciji()} RSD x ${narudzbina.getStavkeNarudzbine().get(stavka)}</p>
            </div>                 
        </c:forEach>
    </div>
   <br>
            <div class="naruceno">
                <h4> Datum: ${narudzbina.getDatumKreiranja()} </h4>
            </div>
    <div class='narudzbina-footer'>
        <div>               
            <p> Popust: ${narudzbina.getPopust()} %</p>
            <p> Ukupna cena: ${narudzbina.getUkupnaCena()} RSD </p>
        </div>
        <div class="btn-narudzbina">
            <a href="Narudzbe?Zahtev=Ostvari&Narudzba=${narudzbina.getNarudzbinaID()}" class="btn btn-warning" >Ostvari</a>
            <a href="Narudzbe?Zahtev=Otkazi&Narudzba=${narudzbina.getNarudzbinaID()}" class="btn btn-dark" >Otkazi</a>
        </div>
    </div>
</div> 

