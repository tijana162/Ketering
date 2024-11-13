<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="card proizvod mb-4 shadow-sm mt-3">
    <img class="card-img-top proizvod-slika" src="${proizvod.getSlikaPath()}" onerror="this.src='./img/svg/minilogo.svg';" alt="...">
    <div class="card-body proizvod-body">
        <h5 class="card-title">${proizvod.getNazivProizvoda()}</h5>
        <p class="card-text proizvod-opis">${proizvod.getOpis()}</p>
        <h6 class="card-subtitle mb-2 text-muted">Cena: ${proizvod.getCenaPoPorciji()} RSD</h6>
    </div>
    <div class="card-footer proizvod-footer bg-white">
        <form action="Narucivanje" method="post">
            <div class="row">
                <div class="col-6">
                    <input class="form-control kolicina-input" placeholder="Količina" type="number" name="kolicina" min="1" required>
                    <input type="hidden" name="proizvodID" value="${proizvod.getProizvodID()}">
                </div>
                <div class="col-6 d-flex justify-content-end">
                    <input type="submit" class="btn btn-primary" name="zahtev" value="Dodaj">
                </div>
            </div>
        </form>
    </div>
</div>

<style>
    .proizvod-slika {
        object-fit: cover;
        height: 200px;
    }
    .proizvod-body {
        padding: 15px;
    }
    .proizvod-footer {
        padding: 10px 15px;
    }
    .kolicina-input {
        max-width: 100px;
    }
</style>
