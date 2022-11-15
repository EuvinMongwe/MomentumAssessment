<%-- 
    Document   : HomePage
    Created on : 01 Nov 2022, 2:53:37 PM
    Author     : euvinmongwe
--%>
<jsp:include page="Header.jsp" flush="true" />

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="row">
    <div class="col-75">
        <div class="container">

            <div class="row">
                <div class="col-50">
                    <h3>Investors Details</h3>
                    <form class="form-signin" method="POST" action="${contextPath}/Login" >
                        <label for="text"><i class="fa fa-user"></i> If you once withdrew from this online platform please enter your credentials else sign up and withdraw</label>

                        <div class="form-group">
                            <label for="username"><i class="fa fa-user"></i> Username</label>
                            <input type="text" id="username" name="username" placeholder="your username..." required="Please enter your username" value="<c:out value="${username}"/>">
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="fa fa-user"></i> Password</label>
                            <input type="password" id="fname" name="password" placeholder="your password..." required="" >
                        </div>
                        
                        <div class="text-error">
                            <a href="${contextPath}/resetPassword" ><strong><c:out value="${failLogin}"/></strong> </a>
                         
                        </div>
                        <div class="text-success">
                            <strong><c:out value="${resetPasssword}"/></strong>
                        </div>
                        <div class="text-error">
                            <strong><c:out value="${failCred}"/></strong>
                        </div>

                        <input type="submit" value="Login and Withdraw" class="btn">

                    </form>
                </div>
                <div class="col-50">
                        <h3>Investments for individuals</h3>
                        <p>You?re ready to keep your money safe and grow your wealth. </p>
                        <p>The question is, what are the most suitable investment options in South Africa for your unique needs? 
                        <p>Whether you want to invest a lump sum amount or build savings monthly,</p> 
                        <p>with our wide range of savings plans and investment solutions, we can help you achieve your financial goals.</p>

                    <a href=https://www.momentum.co.za/momentum/personal/products/callmeback#/getadvice/investment-and-savings"><input type="submit" value="Request a call back" class="btn"></a>

                </div>
            </div>
        </div>

    </div>

    <jsp:include page="Footer.jsp" flush="true" />  
