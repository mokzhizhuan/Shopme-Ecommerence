/**
 * 
 */
function showDeleteConfirmModal(link, entityName)
{
	entityID = link.attr("entityId");
	
	$('#yesButton').attr("href", link.attr("href"));
	$('confirmText').text("Are you sure you want to delete this " + entityName + " ID : " + entityID + " ? ");
	
	$('#confirmModal').modal();
}