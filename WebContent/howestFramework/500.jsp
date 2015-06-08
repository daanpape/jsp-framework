<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="nl">
<head>
<title>500 Internal Server Error</title>
</head>
<body>
<h1>Oeps, something went wrong!</h1>
<p>
The server experienced an unrecoverable error. Please examine the error information
below if you are an application developer. If you are an end user and you keep having
this problem, please contact the site administrator.
</p>
<p>
${error}
</p>
</body>
</html>