<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%request.setCharacterEncoding("utf-8");
    JSONArray arr = (JSONArray)request.getAttribute("arr");%>
    <%=arr%>