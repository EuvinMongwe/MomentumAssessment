<%-- 
    Document   : Header
    Created on : 01 Nov 2022, 2:53:14 PM
    Author     : euvinmongwe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Momentum Investments</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/styles.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
    <div class="header">
        <div class="logo_img">
            <a href="#"><img src="${contextPath}/resources/img/logo.png" alt="" /></img></a>
        </div>

        <a href="https://www.momentum.co.za/momentum/get-help/contact-us" class="logo">Contact US</a>
    </div>
