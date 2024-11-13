<!-- Upit za brisanje -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>

<style>
.form-brisanje {
    text-align: center;
    margin: 50px auto;
    padding: 20px;
    border: 2px solid #ccc;
    border-radius: 8px;
    max-width: 400px;
    background-color: #f9f9f9;
}

   .form-brisanje input[type="text"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
    }

    .form-brisanje input[type="submit"] {
        width: 100%;
        padding: 10px;
        border: none;
        border-radius: 5px;
        background-color: #dc3545; 
        color: white;
        cursor: pointer;
    }
</style>

<div class="brisanje">
    <form class="form-brisanje" action="Profil" method="post">
        <p id="hvala" style="font-family: 'Montserrat Black', sans-serif;">Hvala sto ste bili nas korisnik!</p>
        <input type="text" placeholder="Unesite sifru" name="password">
        <input type="submit" class="btn btn-danger" value="Izbrisi" name="zahtev">
        
        <c:if test="${param.Status == 'greska'}">
            <p class="Del-greska" style="font-family: 'Montserrat Black', sans-serif;">Pogresna lozinka!</p> 
        </c:if>
    </form>
</div>
