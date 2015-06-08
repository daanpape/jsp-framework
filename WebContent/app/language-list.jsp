<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="nl">
<head>
<title>List of languages</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Language</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="lang" items="${languages}">
				<tr>
					<td>${lang.id}</td>
					<td>${lang.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>