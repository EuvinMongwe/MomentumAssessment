<%-- 
    Document   : Withdrawal
    Created on : 01 Nov 2022, 2:53:37 PM
    Author     : euvinmongwe
--%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="Header.jsp" flush="true" />

<body>

    <div class="row">

        <div class="col-75">
            <div class="container">
                <form class="form-signin" method="POST" action="${contextPath}/processWithdrawal" >

                    <div class="row">
                        <div class="col-50">
                            <h3>Investors Details</h3>
                            <c:forEach items="${investor}" var="investorArray">

                                <div class="text-success">
                                    <strong>username : <c:out value="${investorArray.investorID}"/></strong>
                                    <label for="firstname"><i class="fa fa-user"></i>    </label>
                                </div>
                                <label for="firstname"><i class="fa fa-user"></i> First Name</label>
                                <input type="text" id="fname" name="firstname" placeholder="your first name..." value="${investorArray.firstname}" disabled="">

                                <label for="lastname"><i class="fa fa-user"></i> Last Name</label>
                                <input type="text" id="fname" name="lastname" placeholder="your last name..." value="${investorArray.lastname}" disabled="">

                                <label for="phone"><i class="fa fa-envelope"></i> Date of birth</label>
                                <input type="date" id="dob" name="dob" placeholder="your cell Number.." value="${investorArray.dob}" disabled="">

                                <label for="email"><i class="fa fa-envelope"></i> Email</label>
                                <input type="text" id="email" name="email" placeholder="your email address.." value="${investorArray.email}" disabled="">

                                <label for="phone"><i class="fa fa-envelope"></i> Cell Number</label>
                                <input type="text" id="phone" name="phone" placeholder="your cell Number.." value="${investorArray.phone}" disabled="">

                                <label for="gender">Gender</label>
                                <input type="text" id="phone" name="phone" placeholder="your cell Number.." value="${investorArray.gender}" disabled="">

                                <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                                <input type="text" id="adr" name="address" placeholder="your street name and house number..." value="${investorArray.address}" disabled="">
                                <label for="city"><i class="fa fa-institution"></i> City</label>
                                <input type="text" id="city" name="city" placeholder="your city..." value="${investorArray.city}" disabled="">

                                <div class="row">
                                    <div class="col-50">
                                        <label for="state">State</label>
                                        <input type="text" id="state" name="state" placeholder="your state..." value="${investorArray.state}" disabled="">
                                    </div>
                                    <div class="col-50">
                                        <label for="zip">Zip</label>
                                        <input type="text" id="zip" name="zip" placeholder="your zip code..." value="${investorArray.zip}" disabled="">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="col-50">
                            <h3>Investors Product</h3>
                            <fieldset class="fa fa-user">
                                <legend>Previous balances</legend>
                                <c:forEach items="${product}" var="productArray">
                                    <label for="retirementBalance">Retirement balance:</label>
                                    <input type="text" id="retirementBalance" name="retirementBalance" disabled="" value="R ${productArray.retirementPreviousBalance} | Last modified : ${productArray.lastRetire}">
                                    <label for="zip">Savings balance:</label>
                                    <input type="text" id="savingBalance" name="savingBalance" disabled="" value="R${productArray.savingPreviousBalance}   | Last modified : ${productArray.lastSaving}">
                                </c:forEach>
                            </fieldset>

                            <fieldset class="fa fa-user">
                                <legend>Available balances</legend>
                                <c:forEach items="${product}" var="productArray">
                                    <label for="retirementBalance">Retirement balance:</label>
                                    <input type="text" id="retirementBalance" name="retirementBalance" disabled="" value="R${productArray.retirementBalance}   | Last modified : ${productArray.retireDate}">
                                    <label for="zip">Savings balance:</label>
                                    <input type="text" id="savingBalance" name="savingBalance" disabled="" value="R${productArray.savingBalance}   | Last modified : ${productArray.saveDate}">
                                </c:forEach>
                            </fieldset>
                            <label for="withdrawAmnt"><i class="fa fa-user"></i> </label>
                            <label for="withdrawAmnt"><i class="fa fa-user"></i> How much do you want to withdraw</label>
                            <input type="number" id="withdrawAmnt" name="withdrawAmnt" placeholder="withdrawal amount..." required="">

                            <label for="withdrawAmnt"><i class="fa fa-user"></i> </label>
                            <label for="investmentType">Which investment would you like to withdraw</label>
                            <input type="radio" name="investmentType" value="Retirement" checked="">Retirement
                            <input type="radio" name="investmentType"value="Savings">Savings
                            
                            <div class="text-error">
                                <strong><c:out value="${errorMsg}"/></strong>
                            </div>

                            <input type="submit" value="Withdraw Now" class="btn">
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>

</body>

<jsp:include page="Footer.jsp" flush="true" />  
</html>
