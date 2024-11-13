<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ketering Služba</title>
    <link rel="icon" href="./img/svg/minilogo.svg" type="image/icon type">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">

    <script src="js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>
   
  
</head>

<style>


.info-message a {
    text-decoration: none;
    color: white;
}

.info-message a:hover {
    text-decoration: underline;
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

</style>


<body>
    <!-- Postavljanje div-a koji će zauzimati celu širinu stranice -->
    
        <%@include file="includes/navbar.jsp"%>
        
    <section style="font-family: 'Montserrat Black', sans-serif;">
<c:if test="${requestScope.Narudzbine.isEmpty()}">
        <div class="prazna-korpa">
            <p class="info-message">Nemate narudzbine! </p> 
            <p> <a href="Pocetna" class="btn btn-dark">Početna strana</a></p>
        </div>
    </c:if>


            <c:if test="${!requestScope.Narudzbine.isEmpty()}">
                <!-- PRIKAZ NARUDZBINA -->
                <div class="istorija-prikaz">
                    <c:forEach var="narudzbina" items="${requestScope.Narudzbine}">
                        <%@include file="includes/narudzbina.jsp"%>
                    </c:forEach>
                </div>
                <!-- KRAJ NARUDZBINA -->
            </c:if>
        </section>
        
        
</body>
</html>
