function UserServiceClient() {
	this.register = register;
	this.login = login;
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


}
