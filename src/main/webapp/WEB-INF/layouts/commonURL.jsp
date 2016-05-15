<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="ctxURL" value="${pageContext.request.contextPath}" scope="application" />

<spring:url var="indexURL" value="/" />
<spring:url var="homeURL" value="/home" />
<spring:url var="loginURL" value="/login"  />
<spring:url var="logoutURL" value="/logout"  />
<spring:url var="registerURL" value="/register" />
<spring:url var="tennisHomeURL" value="/tennis" scope="application" />
