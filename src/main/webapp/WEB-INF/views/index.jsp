<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="BigCloneEVal web framework">
<meta name="author" content="C M Khaled Saifullah">
<!-- Static content -->
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" >
<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/app.js"></script>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/scripts.js"></script>


<title>BigCloneEval Framework</title>
</head>
<body>
	<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3>
				BigCloneEval Web Framework
			</h3>
			<p>
				Its a web framework of BigCLoneEval Framework. Please fill up the following field to register your clone detection tool and detect the clones of IJA Dataset. Thanks
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<form action="summary" method="post" onsubmit="return fieldValidate()" enctype="multipart/form-data">
				<div class="form-group">					 
					<label for="name">
						Name of the Tool
					</label>
					<input type="Text" class="form-control" name="toolname" id="toolname">
				</div>
				<div class="form-group">					 
					<label for="description">
						Short Description of the tool
					</label>
					<input type="text" class="form-control" name="description" id="description">
				</div>
				<div class="form-group">					 
					<label for="toolFile">
						Tool File submit
					</label>
					<input type="file" class="form-control-file"name="toolFile" id="toolFile" multiple>
					<p class="help-block">
						Please upload the main clone detection tool file here. The file will be used to detect clones.
					</p>
				</div>
				<div class="form-group">					 
					<label for="toolRunnerFile">
						Tool Runner File
					</label>
					<input type="file" class="form-control-file" name="toolRunnerFile" id="toolRunnerFile">
					<p class="help-block">
						Please upload the tool runner file here. The file will run the tool and will format the output in BigCLoneBench format.
					</p>
				</div> 
				<button type="submit" value="Submit" class="btn btn-primary">
					Submit
				</button>
			</form>
		</div>
	</div>
</div>
	
	

</body>
</html>