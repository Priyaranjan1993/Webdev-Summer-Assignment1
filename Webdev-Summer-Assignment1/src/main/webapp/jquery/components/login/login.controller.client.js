(function () {
	var $usernameFld, $passwordFld;
	var $loginBtn;
	var $forgotPwdBtn;
	var userService = new UserServiceClient();
	$(main);

	function main() {
		$usernameFld = $("#username");
		$passwordFld = $("#inputPassword");
		$loginBtn = $("#loginBtn")
		.click(login);
		$forgotPwdBtn = $("#forgotPwdBtn")
		.click(forgotPassword);
		$resetPwdBtn = $("#resetPwdBtn")
		.click(resetPassword);
		$resetPwd = $("#resetPasswordFld");

		$resetPasswordFld = $("#resetPasswordFld");
		$confirmPasswordFld = $("#confirmPasswordFld");
	}

	$(document).ready(function () {
		$("#confirmPasswordFld").keyup(checkPassword);
	});


	function checkPassword(){
		if($resetPasswordFld.val() != $confirmPasswordFld.val())
		{
			$('#confirmPasswordFld').removeClass('is-valid');
			$('#confirmPasswordFld').addClass('is-invalid');
		}
		else{
			$('#confirmPasswordFld').removeClass('is-invalid');
			$('#confirmPasswordFld').addClass('is-valid');
		}
	}

	function forgotPassword()
	{
		
	}


	function resetPassword()
	{

	}

	function postforgotPassword(val)
	{

	}

	function resetforgotPassword(val)
	{

	}


	function login() {
		var user = {
				username : $usernameFld.val(),
				password : $passwordFld.val()
		};
		userService.login(user)
		.then(success);
	}

	function success(str)
	{
		if(str[1] === "true")
		{
			//alert("Logged In");

			$.toast({
				heading: 'Success',
				text: 'Logged In.',
				position: 'top-right',
				hideAfter: 3000,
				icon: 'success'
			})
			window.setTimeout(function(){
				window.location.pathname = "/jquery/components/Profile/profile.template.client.html";
			}, 3500);
		}
		else{
			$.toast({
				heading: 'Error',
				text: 'Invalid User Credentials',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'error'
			})

		}
	}

})();
