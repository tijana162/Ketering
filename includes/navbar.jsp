<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-k6RqeWeci5ZR/Lv4MR0sA0FfDOM5Hg+aW6RV9b07+TTK1MZqC3c1J8u8IoTkbq" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</head>
<style>
    .navbar {
    background-color: #ffffff; /* Boja pozadine navbar-a */
    border-bottom: 1px solid #dddddd; /* Donja ivica sa sivom bojom */
    box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* Senka ispod navbar-a */
}

.navbar-brand {
    font-size: 1.5rem; /* Veličina fonta za brendiranu stavku */
    font-weight: bold; /* Debljina fonta */
    color: #000000; /* Boja teksta */
    margin-right: 15px; /* Margina sa desne strane */
}

.navbar-toggler-icon {
    background-color:antiquewhite; /* Boja ikonice toggle button-a */
}

.nav-link {
    font-size: 1.1rem; /* Veličina fonta linkova */
    color: #000000; /* Boja teksta */
    margin-right: 10px; /* Margina sa desne strane linkova */
    text-decoration: none; /* Bez podvlačenja linkova */
    transition: all 0.3s ease; /* Tranzicija za glatko prelazak hover-a */
}

.nav-link:hover {
    color: #007bff; /* Boja teksta na hover-u */
    text-decoration: underline; /* Podvlačenje teksta na hover-u */
}

.navbar-nav {
    margin-left: auto; /* Automatski poravnanje leve navigacione grupe na desno */
}

.navbar-nav .dropdown-menu {
    background-color: antiquewhite; /* Boja pozadine dropdown menija */
    border: 1px solid black; /* Ivica dropdown menija */
    box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* Senka ispod dropdown menija */
}

.dropdown-item {
    color: #000000; /* Boja teksta stavki dropdown menija */
    font-size: 1rem; /* Veličina fonta stavki dropdown menija */
    transition: all 0.3s ease; /* Tranzicija za glatko prelazak hover-a */
}

.dropdown-item:hover {
    background-color: #f8f9fa; /* Boja pozadine stavki dropdown menija na hover-u */
    color: #007bff; /* Boja teksta stavki dropdown menija na hover-u */
}

/* Ostali stilovi */
body {
    font-family: Arial, sans-serif; /* Font za celu stranicu */
    background-color: white; /* Boja pozadine tela stranice */
    margin: 0; /* Nema margina */
    padding: 0; /* Nema padding-a */
}

.container-fluid {
    padding-left: 15px; /* Leva padding za fluidni kontejner */
    padding-right: 15px; /* Desna padding za fluidni kontejner */
}

.logo {
    display: flex; /* Fleksibilni prikaz */
    align-items: center; /* Poravnanje elemenata po visini */
    text-decoration: none; /* Bez podvlačenja teksta za link */
}

.logo img {
    height: 30px; /* Visina slike u logu */
    margin-right: 10px; /* Margina sa desne strane slike u logu */
}

.dodaj-font {
    font-weight: bold; /* Debljina fonta */
}

.btn {
    cursor: pointer; /* Pokazivač miša u obliku pokazivača */
    transition: all 0.3s ease; /* Tranzicija za glatko prelazak hover-a */
}

.btn:hover {
    opacity: 0.8; /* Smanjivanje prozirnosti dugmeta na hover-u */
}

.dropdown-toggle::after {
   
    display: none; /* Skrivanje ikonice strelice za dropdown meni */
}

    </style>
<nav class="navbar navbar-expand-lg navbar-light nav-boja sticky-top">
    <div class="container-fluid">

        <a class="navbar-brand logo" href="Pocetna">
            <%@include file="../img/svg/catering.svg"%>             
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <!-- LEVI NAV -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active dodaj-font" aria-current="page" href="Pocetna">Proizvodi</a>
                </li>
              
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle dodaj-font" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Katalog
                    </a>                                                         
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="Pocetna?program=slatki">Slatko</a></li>
                        <li><a class="dropdown-item" href="Pocetna?program=slani">Slano</a></li>
                    </ul>
                </li>
                 
                <c:if test="${UserRola < 3}">
                    <li class="nav-item">
                        <a class="nav-link btn btn-lg dodaj-font mx-4" href="adminPanel.jsp">Admin panel</a>
                    </li>
                </c:if>

            </ul>
            
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">                                                             
                <c:if test = "${User == null}">
                    
                    <li class="seperator-red"></li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registracija.jsp">Registracija</a>
                    </li>
                </c:if>

                <c:if test = "${User != null}">
                    <li class="nav-item">
                        <a class="nav-link dodaj-font" href="#" tabindex="-1" aria-disabled="true"><strong>Poeni: ${sessionScope.Poeni}</strong></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Profil?User=${User}&View=Korpa"><i class="fa-solid fa-cart-shopping fa-2x mx-2"></i></a>                     
                    </li>
                    <c:if test="${Narudzbina != null}">
                        <li class="nav-item">
                            <p class="brojac">${Narudzbina.getStavkeNarudzbine().size()}</p>
                        </li>
                    </c:if>  
                        
                    <li class="nav-item dropdown">
                        <a class="mx-4 btn  btn-lg nav-link dropdown-toggle dodaj-font" href="#" id="navbarDropdownProfil" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ${User}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownProfil">
                            <li><a class="dropdown-item" href="Profil?User=${User}&View=Profil">Profil</a></li>
                            <li><a class="dropdown-item" href="Istorija?Zahtev=Pregled">Narudžbine</a></li>
                            <li><a class="dropdown-item" href="Autentikacija">Logout</a></li>
                        </ul>
                    </li>               
                </c:if>

            </ul>
        </div>
    </div>
</nav>
            
