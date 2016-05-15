<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/WEB-INF/layouts/commonURL.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
		<c:url var="loginURL" value="/login" />
		<c:url var="registerURL" value="/register" />
        <h1>Register</h1>
		register for your account. Or <a href="${loginURL}">login</a>
		
        <form:form method="POST" action="${registerURL}" modelAttribute="accountRegistrationForm">
            <fieldset>
                <legend>Login Details</legend>
                <div>
                    <label for="email">Email</label>
                    <form:input type="email" path="email" />
                    <form:errors path="email" cssClass="field_error" element="span" />
                </div>
                <div>
                    <label for="password">Password</label>
                    <form:password path="password" />
                    <form:errors path="password" cssClass="field_error" element="span" />
                </div>
                <div>
                    <label for="repeatedPassword">Repeat Password</label>
                    <form:password path="repeatedPassword" />
                    <form:errors path="repeatedPassword" cssClass="field_error" element="span" />
                </div>
            </fieldset>
            <fieldset>
                <legend>Person Details</legend>
                <div>
                    <label for="title">Title</label>
                    <select name="title" >
                        <option >Select...</option>
                        <option value="1">Miss</option>
                        <option value="2">Mr</option>
                        <option value="3">Mrs</option>
                        <option value="4">Ms</option>
                    </select>
                </div>
                <div>
                    <label for="firstname">Firstname</label>
                    <form:input path="firstname" />
                    <form:errors path="firstname" cssClass="field_error" element="span" />
                </div>
                <div>
                    <label for="lastname">Lastname</label>
                    <form:input path="lastname" />
                    <form:errors path="lastname" cssClass="field_error" element="span" />
                </div>
            </fieldset>
            <fieldset>
                <legend>Contact Details</legend>
                <div>
                    <label for="mobilePhone">Mobile Telephone</label>
                    <form:input type="number" path="mobilePhone" />
                    <form:errors path="mobilePhone" cssClass="field_error" element="span" />
                </div>
                <div>
                    <label for="homePhone">Home Telephone</label>
                    <form:input type="number" path="homePhone" />
                    <form:errors path="homePhone" cssClass="field_error" element="span" />
                </div>
            </fieldset>
            <fieldset>
                <legend>Vehicle Details</legend>
                <div>
                    <label for="registrationNumber">Registration No</label>
                    <form:input path="registrationNumber" />
                    <form:errors path="registrationNumber" cssClass="field_error" element="span" />
                </div>
            </fieldset>
            <div>
                <input type="submit" value="submit" />
            </div>
        </form:form>
    </body>
</html>
