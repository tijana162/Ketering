<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>

<c:if test="${param.Status == 'uspeh'}">
    <div class="poruka">
        <p id="hvala" style="font-family: 'Montserrat Black', sans-serif;">Hvala!<br>Osvojićete ${param.Poeni} poena!</p>
    </div>
</c:if>
<c:if test="${param.Status == 'greska'}">
    <div class="poruka">
        <p id="hvala" style="font-family: 'Montserrat Black', sans-serif;">Došlo je do greške, pokušajte ponovo</p>
    </div>
</c:if>




