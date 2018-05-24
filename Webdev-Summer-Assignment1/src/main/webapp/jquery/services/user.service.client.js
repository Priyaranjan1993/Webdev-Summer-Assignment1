function UserServiceClient() {
	this.register = register;
	this.registerURL = '/api/register';
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
	

}
