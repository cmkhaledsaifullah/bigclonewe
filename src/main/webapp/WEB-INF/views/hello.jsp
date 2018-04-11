<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Summary of the Tool</title>

<!-- Static content -->
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" >
<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/app.js"></script>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/scripts.js"></script>

</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1>Summary of <c:out value="${tool.name}"/></h1>
			<hr>
			<h2>Name of the Tool is <c:out value="${tool.name}"/></h2>
			<h2>Description of the tool is <c:out value="${tool.description}"/></h2>
			<h2>Path of the tool file is <c:forEach var="toolfilename" items="${tool.toolFile}">
			<c:out value="${toolfilename}" />,
			</c:forEach></h2>
			<h2>Path of the tool runner file is ${tool.toolRunnerFile}</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
		<p>Upon Clicking the Go To Next Step Button the present jar file will terminate and you will configure your tool and tool runner using interactive terminal and then run the part 2 jar. Please dont change the location of the tool runner file</p>
		</div>
	</div>
	
	
	<div class="row">
			<div class="col-md-12">
				<form action="detect" method="post" onsubmit="return detectInfoValidate()"> 
					<button type="submit" value="Submit" class="btn btn-primary">
						Goto To Next Step
					</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>