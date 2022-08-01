/**
 * 
 */
$(document).ready(function()
{
	$("#fileImage").change(function()
	{
		if(!checkFileSize(this))
		{
			return;
		}
		showImageThumbnail(this);
	});	
}

function showImageThumbnail(fileInput)
{
	var file = fileInput.file[0];
	var reader = new FileReader();
	reader.onload = function(e)
	{
		$("#thumbnail").attr("src", e.target.result);
	}
	
	reader.readAsDataURL(file);
}