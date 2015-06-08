<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="nl">
<head>
<title>400 Bad Request</title>
</head>
<body>
<h1>Hey there, check your input</h1>
<p>
The request you made to this server is not valid. Please check your input and
try again. If you believe you get this message without deserving it, contact
the site administrator. 
</p>
<p>
${info}
</p>
<p>
Detailed information:<br/><br/>
${error}
</body>
</html>