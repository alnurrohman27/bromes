function login($scope,$http,$window,$rootScope){
	$scope.message = null;

	if($rootScope.session_name != null)
	{
		$window.location.reload();
	}

	$scope.login_validation = function(){
		var data = $.param({
			no:$scope.no,
			command:"login"
		});

		var config = {
	        headers : {
	            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
	        }
	    }

	    $http.post("php/login.php", data, config).
	        success(function(data, status, headers, config) {
	            if(data !=0){
	            	console.log(data); 
		        	sessionStorage.setItem('name', data.name);
		        	sessionStorage.setItem('no', data.no_hp);
		        	$window.location.reload();
		        }
		        else{
		        	$scope.message = "Login gagal";
		        }
	        });
	}

}

angular
	.module('bromes')
	.controller('login', login);