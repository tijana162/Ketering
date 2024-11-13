<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ketering Služba</title>
        <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./css/customStyles.css"/>
        

        <script src="js/bootstrap.min.js"></script>
    </head>
    
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
        
    <body>
        
            <%@include file="includes/navbar.jsp"%>
            <section>
                <div class="container login-cent">     
                    <form class="login-form" action="Autentikacija" method="post">     
                        <strong class="bold"> LOGIN </strong>
                        <input class="input-prikaz" type="text" placeholder="Korisnicko ime" name="loginKorisnicko" required></br>
                        <input class="input-prikaz" type="password" placeholder="Password" name="password" required></br>
                        <input class="btn btn-primary" type="submit" value="Uloguj se!">
                        <p class="${msgTip}">${msg}</p>
                       </form>  
                </div>
            </section>
    </body>
</html>