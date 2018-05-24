(function(){

	$(init);
	var staticEmail;
	var firstName;
	var lastName;
	var updateBtn;
	var userId;
	var userService = new UserServiceClient();

	function init()
	{
		console.log(userId);
		$userName = $("#userName");
		$firstName = $("#firstName");
		$lastName = $("#lastName");
		$phoneNumber = $("#phoneNumber");
		$email = $("#email");
		$role = $("#role");
		$dob = $("#dob");

		$logoutBtn = $("#logoutBtn")
		.click(logout);

		$manageBtn = $("#manageBtn")
		.click(adminScreen);

		updatedBtn = $("#updateBtn")
		.click(updateProfile);
		findProfileById();
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

	function adminScreen()
	{

		userService.checkIfAdmin()
		.then(postAdminScreen);
	}

	function postAdminScreen(data)
	{
		if(data == true)
		{
			window.location.pathname = "/jquery/components/admin/user-admin.template.client.html";
		}
		else{
			$.toast({
				heading: 'Error',
				text: 'You do not have access to the Admin module.',
				position: 'top-right',
				hideAfter: 3000,
				icon: 'error'
			})
		}

	}

	function updateProfile()
	{
		console.log(userId);
		var user = {
				firstName : $firstName.val(),
				lastName : $lastName.val(),
				phone : $phoneNumber.val(),
				email : $email.val(),
				role : $role.val(),
				dateOfBirth : $dob.val(),
		};
		userService.updateProfile(userId,user)
		.then(success);
	}

	function success(response)
	{
		if(response == null)
		{
			//alert("unable to update");
			$.toast({
				heading: 'Success',
				text: 'Error in updating data',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'error'
			})
		}
		else{
			//alert("success");
			renderUser(response);
			$.toast({
				heading: 'Success',
				text: 'Data updated successfully',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'success'
			})
		}
	}

	function findProfileById(){
		userService.findProfileById()
		.then(renderUser);
	}

	function renderUser(user)
	{
		console.log(user);
		$userName.val(user.username);
		$firstName.val(user.firstName);
		$lastName.val(user.lastName);
		$phoneNumber.val(user.phone);
		$email.val(user.email);
		$("#role").val(user.role);
		$dob.val(user.dateOfBirth);
	}
})();