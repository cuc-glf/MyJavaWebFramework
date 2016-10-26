$(document).ready(function() {
	$("#login").on("click", function() {
		var token = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		$.post("/login", token, function(data, status) {
			// success callback
			console.log("login succeed, data: " + data);

		});
	});
});