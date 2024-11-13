<!-- PRIKAZ KORPE -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    .korpa-prikaz {
        max-width: 100%;
        margin: 0 auto;
        padding: 20px;
    }

    .stavke-prikaz {
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    .korpa-stavka {
        display: flex;
        align-items: center;
        padding: 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background-color: #f9f9f9;
    }

    .korpa-stavka img {
        width: 100px;
        height: 100px;
        object-fit: cover;
        border-radius: 8px;
        margin-right: 15px;
    }

    .stavka-info {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
    }

    .stavka-info h5 {
        margin: 0;
        font-size: 18px;
        color: #333;
    }

    .stavka-info p {
        margin: 5px 0;
        font-size: 16px;
        color: #666;
    }

    .stavka-buttons {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        gap: 10px;
    }

    .stavka-buttons .btn {
        padding: 5px 10px;
        font-size: 14px;
    }

    .korpa-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px;
        border-top: 1px solid #ddd;
        margin-top: 20px;
    }

    .korpa-footer .btn {
        padding: 10px 20px;
        font-size: 16px;
    }

    .total {
        font-size: 18px;
        font-weight: bold;
    }
.prazna-korpa {
    text-align: center;
    margin: 50px auto;
    padding: 20px;
    border: 2px solid #ccc;
    border-radius: 8px;
    max-width: 400px;
    background-color: #f9f9f9;
}

.poruka-korpa p {
    margin: 10px 0;
}

.poruka-korpa a {
    text-decoration: none;
    color: #fff;
}
    .quantity-input {
    width: 100px;
    padding: 5px;
    font-size: 16px;
    text-align: center;
    border: 1px solid #ccc; /* Dodavanje border-a */
    border-radius: 4px; /* Dodavanje zaobljenih ivica */
}
</style>

<c:if test="${Narudzbina == null}">
    <div class="prazna-korpa">
        <div class="poruka-korpa">
            <p style="font-family: 'Montserrat Black', sans-serif;">Korpa je prazna!</p>
            <p><a href="Pocetna" class="btn btn-dark" style="font-family: 'Montserrat Black', sans-serif;">Početna strana</a></p>
        </div>
    </div>
</c:if>

<c:if test="${Narudzbina != null}">
    <form class="korpa-prikaz" action="Narucivanje" method="post">
        <h3><strong>Vaša korpa</strong></h3>
        <div class='stavke-prikaz'>
            <c:forEach var="stavka" items="${Narudzbina.getStavkeNarudzbine().keySet()}">
                <div class='korpa-stavka'>
                    <img src="${stavka.getSlikaPath()}" alt="${stavka.getNazivProizvoda()}">
                    <div class="stavka-info">
                        <h5>${stavka.getNazivProizvoda()}</h5>
                        <p>Količina: 
    <input class="btn btn-light poeni-korpa quantity-input" type="number" min="1" id="${stavka.getProizvodID()}" onchange="updateUrl(this)" value="${Narudzbina.getStavkeNarudzbine().get(stavka)}">
</p>

                        <p class='stavka-total'>Cena: ${stavka.getCenaPoPorciji() * Narudzbina.getStavkeNarudzbine().get(stavka)} RSD</p>
                    </div>
                    <div class="stavka-buttons">
                        <a href="" id="a${stavka.getProizvodID()}" class="btn btn-warning btn-stavka">Izmeni</a>
                        <a href="Korpa?Zahtev=Izbrisi&Proizvod=${stavka.getProizvodID()}" class="btn btn-danger btn-stavka">Izbriši</a>
                    </div>
                </div>
            </c:forEach>
            
            
        </div>
        <div class="korpa-footer">
            <input class="btn btn-light poeni-korpa" type="number" min="0" id="poeni" onchange='popust(this)' max="<c:if test="${sessionScope.Poeni>2}">2</c:if><c:if test="${sessionScope.Poeni<=2}">${sessionScope.Poeni}</c:if>" name="poeni" placeholder="Poeni">
            <div>
               <input class="btn btn-danger btn-korpa" type="submit" name="zahtev" value="Otkaži">
<input class="btn btn-success btn-korpa" type="submit" name="zahtev" value="Naruči">

            </div>
            <div class="total">
                   <p>Cena bez popusta: <span id="subtotal">${Narudzbina.getTotalBezPopusta()}</span> RSD</p> 
                <p>Cena sa popustom: <span id="total">${Narudzbina.getTotalBezPopusta()}</span> RSD</p>
            </div>
        </div>
            
            
    </form>  
                 
</c:if>


  
<script src="scripts/popust.js"></script>
<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br>


