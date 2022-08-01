		max_file_size = 502400; // 500KB
		
		moduleURL = "[[@{/products}]]";
		
		$(document).ready(function()
		{
			$("#buttonCancel").on("click", function() 
			{
				window.location = "[[@{/products}]]";
			});
		});
		
		function showImageThumbnail(fileInput)
		{
			var file = fileInput.file[0];
			var reader = new FileReader();
			reader.onload = function(e)
			{
				$("#thumbnail").attr("src", e.target.result);
			};
	
			reader.readAsDataURL(file);
		}
		
		$(document).ready(function()
		{
			$("#LogoutLink").on("click", function(e) 
			{
				e.preventDefault();
				document.LogoutForm.submit();
			});
		});
		
		function checkUnique(form)
		{
			productID = $("#ID").val();
			productName = $("#name").val();
			
			csrfValue = $("input[name='_csrf']").val();
			
			url = "[[@{/products/check_unique}]]";
			
			params = {ID: productID, name: productName, _csrf: csrfValue};
			
			$.post(url, params, function(response) 
			{
				if (response == "OK")
				{
					form.submit();
				}
				else if(response == "Duplicate")
				{
					alert("There is another Product named : " + productName + " .  Please type again");
				}
			});
			
			return false;
		}
		
		function checkFileSize(fileInput)
		{
			fileSize = fileInput.files[0].size;
		
			if(fileSize > max_file_size)
			{
				fileInput.setCustomValidity("You must choose an image less than 100KB");
				fileInput.reportValidity();
				return false;
			}
			else
			{
				fileInput.setCustomValidity("You must choose an image less than 100KB");
				return true;
			}
		}