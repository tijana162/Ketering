<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ketering</title>
        <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">

        <script src="js/bootstrap.min.js"></script>
    </head>
    
    <style>
               .input-prikaz {
                    
            border-radius: 10px;
            border: 2px solid #ccc;
            padding: 8px 12px;
            font-size: 16px; 
            width: 70% !important; 
            box-sizing: border-box; 
            margin-bottom: 10px;
           
            }
                .reg-center {
                width: 100%;
                max-width: 500px;
                padding: 20px;
                background-color: rgba(255, 255, 255, 0.9); /* Bež pozadina sa transparentnošću */
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                margin-left: 30%;
                margin-top: 10%
            }
               
        </style>

        
    <body>
       
            <%@include file="includes/navbar.jsp"%>
            <div class="reg-center">
                        <form class="reg-form" action="Autentikacija" method="post">  
                            <strong class="bold">REGISTRACIJA</strong><br>
                            <label for="korisnickoIme">Korisničko ime</label> 

                            <input class="input-prikaz"type="text" name="korisnickoIme" id="korisnickoIme" required/></br>

                            <label for="ime">Ime</label> 

                            <input class="input-prikaz" type="text" name="ime" id="ime" required/></br>

                            <label for="prezime">Prezime</label> 

                            <input class="input-prikaz" type="text" name="prezime" id="prezime" required/></br>

                            <label for="password">Password</label> 

                            <input class="input-prikaz" type="password" name="password" id="password" required/></br>

                            <label for="adresa">Adresa</label> 

                            <input class="input-prikaz" type="text" name="adresa" id="adresa" required/></br>
                            <div class="reg-buttons">
                                    <input class="btn btn-primary " type="submit" value="Registruj se"> 
                                    <input class="btn btn-secondary" type="reset" value="Resetuj">
                            </div>      
                            <p class="greska">${msg}</p>
                        </form>                                 
            </div>

    </body>
</html>
