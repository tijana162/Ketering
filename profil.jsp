<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ketering Služba</title>
        <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">

        <script src="js/bootstrap.min.js"></script>
        <script src="js/scripts.js"></script>
         <style>
            .fullwidth-content {
                width: 100%;
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                
            }
        </style>
    </head>
    <body>
         <div class="container-fluid fullwidth-content">
            <%@include file="includes/navbar.jsp"%>
            <section>
                
<!--                    <div class="profil-linkovi">
                        <img src="./img/svg/posluzaonik-cropped.svg" alt="Posluzaonik"/>
                        <h2><a class="profil-link" href="Profil?User=${User}&View=Profil">Informacije</a></h2>
                        <h2><a class="profil-link" href="Profil?User=${User}&View=Korpa">Korpa</a></h2>
                        <h2><a class="profil-link" href="Istorija?Zahtev=Pregled">Istorija</a></h2>
                        <h2><a class="profil-link" href="Profil?User=${User}&View=Izbrisi">Izbriši profil</a></h2>
                        <img src="./img/svg/tacna-cropped.svg" alt="ruka"/>
                    </div>-->
                    <c:if test = "${param.View == 'Profil'}">
                        <%@include file="includes/profilInfo.jsp"%>
                    </c:if>
                    <c:if test = "${param.View == 'Korpa'}">
                        <%@include file="includes/korpa.jsp"%>
                    </c:if>
                    <c:if test = "${param.View == 'Izbrisi'}">
                        <%@include file="includes/izbrisi.jsp"%>
                    </c:if>
                    <c:if test = "${param.View == 'Poruka'}">
                        <%@include file="includes/poruka.jsp"%>
                    </c:if>
                      
            </section>
        
        </div>
             
    </body>
</html>