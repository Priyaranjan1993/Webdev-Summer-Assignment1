var template;
var tbody;
var userIdentity;
var userService = new UserServiceClient();

$(main);

function main(){

	template = $('.template');
	tbody = $('tbody');
	tbody.empty();

	$('#createUserBtn').click(createUser);
	$('#searchUserBtn').click(searchUser);
	$('#updateUserBtn').click(updateUser);

	$logoutBtn = $("#logoutBtn")
	.click(logout);

	$profileBtn = $("#profileBtn")
	.click(profileScreen);

	findAllUsers();

}

function logout(){
	userService.logout()
	.then(postLogout);
}

function postLogout(data)
{
	$.toast({
		heading: 'Success',
		text: 'Logged out successfully',
		position: 'top-right',
		hideAfter: 3000,
		icon: 'success'
	})
	window.setTimeout(function(){
		window.location.pathname = "/jquery/components/login/login.template.client.html";
	}, 3500);
}

function profileScreen()
{

	window.location.pathname = "/jquery/components/Profile/profile.template.client.html";
}


function renderUsers(users) {
	tbody.empty();
	for(i=0;i < users.length; i++)
	{
		var user = users[i];
		var clone = template.clone();
		clone.attr('id',user.id);

		clone.find('.delete').click(deleteUser);
		clone.find('.edit').click(findUserById);
		clone.find('.username').html(user.username);
		clone.find('.password').html(user.password);
		clone.find('.firstName').html(user.firstName);
		clone.find('.lastName').html(user.lastName);
		clone.find('.role').html(user.role);
		tbody.append(clone);
	}

	$.toast({
		heading: 'Information',
		text: 'All users loaded',
		position: 'top-right',
		hideAfter: 4000,
		icon: 'info'
	})
}

function findAllUsers()
{
	userService.findAllUsers()
	.then(renderUsers);
}

function createUser(){
	var username = $('#usernameFld').val();
	var password = $('#passwordFld').val();
	var firstName = $('#firstNameFld').val();
	var lastName = $('#lastNameFld').val();
	var role = $('#roleFld').val();

	if(username != "" && password!= "")
	{
		var user = {
				username : username,
				password : password,
				firstName : firstName,
				lastName : lastName,
				role : role
		};

		userService.createUser(user)
		.then(createSuccess)
		.then(findAllUsers);
	}
	else{
		$.toast({
			heading: 'Error',
			text: 'Enter both username and password to add an user.',
			position: 'top-right',
			hideAfter: 3000,
			icon: 'error'
		})
	}


}

function deleteUser(event) {
	var deleteBtn = $(event.currentTarget);
	var userId = deleteBtn.parent().parent().attr('id');
	userService.deleteUser(userId)
	.then(deleteSuccess)
	.then(findAllUsers);
}

function findUserById(event)
{
	var updateBtn = $(event.currentTarget);
	var userId = updateBtn.parent().parent().attr('id');
	userService.findUserById(userId)
	.then(renderUser);
}

function renderUser(user) {
	console.log(user);
	$('#usernameFld').val(user.username);
	$('#passwordFld').val(user.password); 
	$('#firstNameFld').val(user.firstName);
	$('#lastNameFld').val(user.lastName);
	$('#roleFld').val(user.role);
	userIdentity = user.id;
	$.toast({
		heading: 'Information',
		text: 'User loaded for editing',
		position: 'top-right',
		hideAfter: 4000,
		icon: 'info'
	})
}

function updateUser() {
	var username = $('#usernameFld').val();
	var password = $('#passwordFld').val();
	var firstName = $('#firstNameFld').val();
	var lastName = $('#lastNameFld').val();
	var role = $('#roleFld').val();

	var user = {
			username : username,
			password : password,
			firstName : firstName,
			lastName : lastName,
			role : role
	};

	userService.updateUser(user,userIdentity)
	.then(updateSuccess)
	.then(findAllUsers)
}

function updateSuccess(){
	$.toast({
		heading: 'Success',
		text: 'User information updated successfully',
		position: 'top-right',
		hideAfter: 4000,
		icon: 'success'
	})
}

function deleteSuccess(){
	$.toast({
		heading: 'Success',
		text: 'User information deleted successfully',
		position: 'top-right',
		hideAfter: 4000,
		icon: 'success'
	})
}

function createSuccess(data){
	if(data == false)
	{
		$.toast({
			heading: 'Error',
			text: 'A similar username already exists. Please try some different username',
			position: 'top-right',
			hideAfter: 4000,
			icon: 'error'
		})
	}
	else{
		$.toast({
			heading: 'Success',
			text: 'User created successfully',
			position: 'top-right',
			hideAfter: 4000,
			icon: 'success'
		})
	}

}

function searchSuccess(){
	$.toast({
		heading: 'Success',
		text: 'Search data loaded succeessfully',
		position: 'top-right',
		hideAfter: 4000,
		icon: 'success'
	})
}

function searchUser() {
	var username = $('#usernameFld').val();
	var password = $('#passwordFld').val();
	var firstName = $('#firstNameFld').val();
	var lastName = $('#lastNameFld').val();
	var role = $('#roleFld').val();

	var user = {
			username : username,
			password : password,
			firstName : firstName,
			lastName : lastName,
			role : role
	};

	userService.searchUsers(user)
	.then(renderUsers)
	.then(searchSuccess);
}
