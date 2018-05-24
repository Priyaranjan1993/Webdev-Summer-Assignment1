function UserServiceClient() {
	this.register = register;
	this.login = login;
	this.forgotPassword = forgotPassword;
	this.resetPassword = resetPassword;
	this.logout = logout;
	this.registerURL = '/api/register';
	this.loginURL = '/api/login';
	var self = this;

	function register(user) {

		return fetch(this.registerURL,{
			method :'post',
			body : JSON.stringify(user),
			credentials: 'same-origin',
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}

	function login(user) {
		return fetch(this.loginURL,{
			method: 'post',
			credentials: 'same-origin',
			body: JSON.stringify({username:user.username, password: user.password}),
			headers: {
				'content-type': 'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function forgotPassword(userEmail)
	{
		return fetch(this.loginURL+'/forgot',{
			method :'post',
			credentials: 'same-origin',
			body : JSON.stringify(userEmail),
			headers : {
				'content-type':'application/json',
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}

	function resetPassword(userPwd)
	{
		return fetch(this.loginURL+'/reset',{
			method :'post',
			credentials: 'same-origin',
			body : JSON.stringify(userPwd),
			headers : {
				'content-type':'application/json',
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function logout(){
		return fetch(this.url+'/logout',{
			method :'post',
			credentials: 'same-origin',
			headers : {
				'Content-Type': 'application/json',
			}
		})
		.then(function(response)
				{
			return response;
				});
	}


}
