// Skripta menja link buttona za menjanje kolicine u korpi
function updateUrl(element) {
    var link = document.getElementById("a" + element.id);
    link.setAttribute("href", "Korpa?Zahtev=Izmeni&Proizvod=" + element.id + "&Kolicina=" + element.value);
}
// Skripta dinamicki racuna Total sa popustom u korpi
function popust(element) {
    let subtotal = document.getElementById("subtotal").textContent;
    let total = document.getElementById("total");

    if (element.value > 2) {
        element.value = 0;
    }
    if (element.value < 0) {
        element.value = 0;
    }
    let popust = element.value * 10;
    total.textContent = subtotal - ((subtotal / 100) * popust);
}

// Jquery

$(document).ready(function () {

    //Async poziv Proizvod controllera za izmenu proizvoda.
    $(".btn-izmeni").on("click", function () {
        $proizvod = event.target.id;
        $naziv = $("#naziv" + $proizvod).text();
        $slika = $("#slika" + $proizvod).text();
        $opis = $("#opis" + $proizvod).text();
        $cena = $("#cena" + $proizvod).text();
        $kategorija = $("#kategorija" + $proizvod + " :selected").text();
        $kategorijaID = $("#kategorija" + $proizvod + " :selected").attr("id");

        if ($.isNumeric($cena)) {
            $.ajax({
                type: "POST",
                data: {proizvod: $proizvod,
                    naziv: $naziv,
                    slika: $slika,
                    opis: $opis,
                    cena: $cena,
                    kategorija: $kategorija,
                    kategorijaID: $kategorijaID,
                    zahtev: "izmeni"},
                url: "/CateringService/Proizvodi",
                success: function () {
                    alert("Proizvod je izmenjen!");
                },
                error: function () {
                    alert("Doslo je do greške");
                }
            });
        } else {
            alert("Cena mora biti broj");
        }
    });

    // Async pozivi za Kategorije kontroler za izmenu kategorija
    $(".kat-izmeni").on("click", function () {
        $kategorija = event.target.id;
        $naziv = $("#katNaziv" + $kategorija).text();
        $program = $("#program" + $kategorija + " :selected").text();

            $.ajax({
                type: "POST",
                data: {kategorija: $kategorija,
                    naziv: $naziv,
                    program: $program,
                    zahtev: "izmeni"},
                url: "/CateringService/Kategorije",
                success: function () {
                    alert("Kategorija je izmenjena!");
                },
                error: function () {
                    alert("Doslo je do greške");
                }
            });

    });
    
    //Async poziv za Korisnici kontroler za izmenu informacija korisnika
   $(".btn-izmeni-kor").on("click", function(){
        // get data
        $korisnik = event.target.id;
        $korisnickoIme = $("#korisnicko" + $korisnik).text();
        $ime = $("#ime" + $korisnik).text();
        $prezime = $("#prezime" + $korisnik).text();
        $adresa = $("#adresa" + $korisnik).text();
        $poeni = $("#poeni" + $korisnik).text();
        $rola = $("#rola" + $korisnik + " :selected").attr("id");
        $password = $("#password" + $korisnik).text();

        if($.isNumeric($poeni)){
             $.ajax({
            type: "POST",
            data:{
                zahtev : "izmeni",
                korisnik : $korisnik,
                korisnicko : $korisnickoIme,
                ime : $ime,
                prezime : $prezime,
                adresa : $adresa,
                poeni : $poeni,
                rola : $rola, 
                password : $password 
            },
            url: "/CateringService/Korisnici",
            success: function(){
                $("#password" + $korisnik).text("Neizmenjen");
                alert("Korisnik je izmenjen");
            },
            error: function() {
                alert("Doslo je do greske!");
            }
        });
        } else {
            alert("Poeni moraju biti broj!");
        }

    });

   // Menja vrednost kategorije za dodavanje novog proizvoda 
    $(".katSelect").on("change", function(){
       $kategorija = $(".katSelect :selected").attr("id");
       $(".katID").attr("value", $kategorija);
    });

    //Menja vrednost role za dodavanje novog korisnika
    $(".nov-rola").on("change", function(){
        $(".nov-rolaID").attr("value", $(".nov-rola :selected").attr("id"));
    });
    
    //Generise pdf od izvestaja
    
});