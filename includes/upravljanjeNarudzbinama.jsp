<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>

<div class='panel'>
    <h2 class="naslov"> Sve narudžbine </h2>
    <div class="prikaz-neostvarenih">
        <c:if test="${requestScope.Narudzbine.isEmpty()}">
            <div class="prazna-korpa">
                <p>Nema neostvarenih narudzbina!</p>
            </div>
        </c:if>

        <c:if test="${!requestScope.Narudzbine.isEmpty()}">
          

                <c:forEach var="narudzbina" items="${requestScope.Narudzbine}">
                    <%@include file="./neostvarenaNarudzbina.jsp"%>
                </c:forEach>

        </c:if>
    </div>
</div>