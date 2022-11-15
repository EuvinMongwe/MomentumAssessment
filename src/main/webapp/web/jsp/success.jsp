<%-- 
    Document   : success
    Created on : 01 Nov 2022, 2:53:37 PM
    Author     : euvinmongwe
--%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="Header.jsp" flush="true" />

<body>

    <div class="text-success">
        <strong>Thank you, your withdrawal was successful a confirmation email will be sent shortly...</strong>
    </div>

</body>

</html>
