function fieldValidate()
{
	
	var name = document.getElementById("toolname").value;
	var description = document.getElementById("description").value;
	if (!name.match(/\S/)) 
	{
		alert('Please enter a valid Tool Name.');
		return false;
	} 
	else if(!description.match(/\S/)) 
	{
		alert('Please enter a short Decription of the tool');
		return false;
	}
	else if(document.getElementById("toolFile").files.length == 0)
	{
		alert('Please upload the tool file');
		return false;
	}
	else if(document.getElementById("toolRunnerFile").files.length == 0)
	{
		alert('Please upload the tool runner file');
		return false;
	}
	else
	{
		return true;
	}

}

function detectInfoValidate()
{
	var output = document.getElementById("output").value;
	if (!output.match(/\S/)) 
	{
		alert('Please enter a valid Output file name.');
		return false;
	} 
	else
	{
		return true;
	}
}