<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="container">
        <div class="navbar-header">
            <a href="${indexURL}" class="navbar-brand">
              SportScore<b class=" disabled">.com</b>
            </a>
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
        </div>
        
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <li class="${generalModel.sportSection=='footbal'?'active':''}">
                    <a href="javascript:;" class="navbar-btn">Football</a>
                </li>
                <li class="${generalModel.sportSection=='tennis'?'active':''}">
                    <a href="${tennisHomeURL}" class="navbar-btn ">Tennis</a>
                </li>
                <li class="${generalModel.sportSection=='rugby'?'active':''}">
                    <a href="javascript:;" class="navbar-btn">RugbyUnion</a>
                </li>
                <li>
                    <a href="javascript:;" class="navbar-btn ">Formula 1</a>
                </li>
                <li>
                    <a href="javascript:;" class="navbar-btn ">Cycling</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a class="navbar-btn " href='${loginURL}' >Login</a></li>
                <li>
                    <a class="navbar-btn" id="navbar-reg-btn" href='${registerURL}' >
                        Register
                    </a>
                </li>
            </ul>

        </div>
    </div>
