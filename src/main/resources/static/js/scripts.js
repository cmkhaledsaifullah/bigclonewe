function validateForm(scope) {
	var name = document.getElementById("toolname").value;
	var description = document.getElementById("description").value;
	var outputfilename = document.getElementById("output").value;
	if (!name.match(/\S/)) 
	{
		alert('Please enter a valid Tool Name.');
		return false;
	}
	else if (!description.match(/\S/)) 
	{
		alert('Please enter a short Decription of the tool');
		return false;
	}
	else if (document.getElementById("toolFile").files.length == 0) 
	{
		alert('Please upload the tool result file');
		return false;
	}
	else if (!outputfilename.match(/\S/)) 
	{
		alert('Please enter Output File Name');
		return false;
	}
	else 
	{
		return true;
	}

}